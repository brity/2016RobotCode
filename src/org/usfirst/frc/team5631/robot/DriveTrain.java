package org.usfirst.frc.team5631.robot;

import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;

public class DriveTrain {

	MotorEncoderSystem leftSide, rightSide, elevator;
	Controller[] controller;
	Regulator regulator;
	double maxSpeed, calibratingTimer;

	public DriveTrain() {
		/*
		 * Sets the left side to store the left side motors and encoder vice
		 * versa for the right side also sets the elevator motor and encoder pin
		 * locations
		 */
		leftSide = new MotorEncoderSystem(new Talon(0), new Talon(1),
				new Encoder(2, 3, true, CounterBase.EncodingType.k1X));
		rightSide = new MotorEncoderSystem(new Talon(2), new Talon(3),
				new Encoder(0, 1, true, CounterBase.EncodingType.k1X));
		elevator = new MotorEncoderSystem(new Talon(4), new Encoder(5, 6, false, CounterBase.EncodingType.k1X));
		controller = new Controller[2];
		/*
		 * Creates a new controller -- controller 0 needs to be joystick and
		 * controller 1 needs to be xbox controller, you can drag & drop this in
		 * the drive station software.
		 */
		controller[0] = new Controller(new Joystick(0));//Joystick controller
		controller[1] = new Controller(new Joystick(1));//Xbox controller

		// creates the speed regulator
		regulator = new Regulator();

		// sets max speed determined by person
		maxSpeed = 50;
		calibratingTimer = 0;

	}

	public void runRobot() {

		// checks inputs from two controllers
		checkInputs();

		// checks for emergency brake
		if (!checkEmergBrake()) {
			// pulls needed speeds from regulator and passes them to wheel
			// system
			setWheelSystemSpeeds(regulator.regulateLeftSpeed(), regulator.regulateRightSpeed());
		} else {
			// sets wheel systems power and speed to 0
			setWheelSystemPowers(0, 0);
			setWheelSystemSpeeds(0, 0);
		}

		// runs the robots wheel systems
		runSystems();

		// not necessary
		System.out.println(elevator.distance);

	}

	public void resetDistance() {
		// resets both sides distances
		leftSide.resetDist();
		rightSide.resetDist();
	}

	public void raiseElevator(double level) {

		// Set speed, distance from level before slowing down and
		double t = 2;
		double speed = 10;

		// not sure what this does lols
		boolean added = false;

		// Checks Whether the elevators needs to go up or down and applies the
		// correct speed
		if (level < elevator.getLevel())
			speed *= -1;
		// If the elevator is too low, it moves up
		if (elevator.getLevel() < level) {
			elevator.setSpeedElev(speed);
		}
		// If the elevator is too high, it moves down
		if (elevator.getLevel() > level) {
			elevator.setSpeedElev(speed);
		}
		// If it's at the right level, it stops moving.
		if (level == elevator.getLevel()) {
			Robot.num_i++;
			elevator.setSpeedElev(0);
		}

	}

	public void drive(double distance1, double distance2) {

		int t = 10;
		double speed1 = maxSpeed / 2;
		double speed2 = -maxSpeed / 2;

		// checks whether the The distance is negative if so the speed is
		// inverted
		if (distance1 < 0)
			speed1 *= -1;
		if (distance2 < 0)
			speed2 *= -1;

		// Grabs the absolute value of the distance for easier manipulation
		distance1 = Math.abs(distance1);
		distance2 = Math.abs(distance2);

		// Grabs the current distance
		double leftDist = Math.abs(leftSide.distance);
		double rightDist = Math.abs(rightSide.distance);

		// If the current distance is less then the distance needed then it
		// applies the speed to the motors
		if ((leftDist) < distance1 - t) {
			leftSide.setSpeed(speed1);
		} else if (leftDist > distance1 - t && leftDist < distance1) {
			leftSide.setSpeed(speed1 / 3);
		} else {
			leftSide.setPower(0);
		}

		if ((rightDist) < distance2 - t) {
			rightSide.setSpeed(speed2);
		} else if (rightDist > distance2 - t && rightDist < distance2) {
			rightSide.setSpeed(speed2 / 4);
		} else {
			rightSide.setPower(0);
		}

		// once it reaches its destination the robot stops and resets the
		// encoders distance
		// Then tells the robot to move on to the next command
		if (Math.abs(leftSide.distance) >= Math.abs(distance1) && Math.abs(rightSide.distance) >= Math.abs(distance2)) {
			Robot.num_i++;
			resetDistance();
			setWheelSystemSpeeds(0, 0);
		}

	}

	public void setWheelSystemPowers(double leftSidePower, double rightSidePower) {
		// Sets the power of the left and right side to the inputed power
		// ONLY REALLY USED TO OVERIDE THE PID
		leftSide.setPower(leftSidePower);
		rightSide.setPower(-rightSidePower);

	}

	public void setWheelSystemSpeeds(double leftSideSpeed, double rightSideSpeed) {
		// Sets the speed of the motor systems to the inputed amount
		// the PID then tries to regulate the read speed to get it to the needed
		// speed
		leftSide.setSpeed(leftSideSpeed);
		rightSide.setSpeed(-rightSideSpeed);

	}

	public void runSystems() {
		// Just runs all of the motor systems
		leftSide.runMotors();
		rightSide.runMotors();
		elevator.runMotor();

	}

	public void calibrateElevator() {
		// Sets the max speed of the elevator to 10 inches per second
		elevator.setSpeedElev(-10);
		// runs the motor systems
		runSystems();
		// Checks if the timer is greater than 0, if so then checks if the read
		// encoder speed
		// is 0 then the elevator has stopped moving meaning it had reached the
		// bottom
		// therefore the distance on the encoders can be reset in order to have
		// the distance start at the bottom
		if (calibratingTimer > 5) {
			if (elevator.encoder.getRate() > -0.5 && elevator.encoder.getRate() <= 0) {
				// if(elevator.encoder.getRate() == 0){
				Robot.calibrating = false;
				elevator.setPower(0);
				calibratingTimer = 0;
				elevator.encoder.reset();
			}
		}
		calibratingTimer++;
	}

	public void checkInputs() {

		// Runs a for loop through the axises, x and y for the joystick
		for (int i = 1; i < 3; i++) {

			double inputDevice1 = controller[0].getAxisValue(i);
			double inputDevice2 = -controller[1].getAxisValue(1);
			double para = 0.2;

			if (Methods.getParameters(inputDevice1, para)) {
				regulator.setSpeed(inputDevice1, i);
			} else {
				regulator.setSpeed(0, i);
			}

			if (controller[1] != null) {
				
				if (Methods.getParameters(inputDevice2, 0.2)) {
					elevator.setSpeedElev(inputDevice2 * 20);
				} else {
					elevator.setSpeedElev(0);
				}
			}

		}

		regulator.setThrottle(((controller[0].getAxisValue(3) - 1) / 2) * maxSpeed);

	}

	public boolean checkEmergBrake() {

		if (controller[0].getButtonState(1)) {
			return true;
		} else {
			return false;
		}

	}

}
