/*
 * This is the main robot code for FRC team 5631.
 * This class uses the other objects to control the robot
 * while it's on.
 */
package org.usfirst.frc.team5631.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.CameraServer;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	DriveTrain driveTrain;
	// This is the webcam.
	CameraServer server;
	double[][] commands;
	static int num_i = 0;

	public static boolean calibrating = true;

	public void robotInit() {

		server = CameraServer.getInstance();
		server.setQuality(50);
		server.startAutomaticCapture("cam1");

		driveTrain = new DriveTrain();

	}

	public void autonomousInit() {
		/*
		 * Determines whether it is calibrating -- always true if autonomous is
		 * turned off then on also resets the distance that the elevator and 2
		 * motor sides have gone
		 */
		num_i = 0;
		calibrating = true;
		driveTrain.resetDistance();

		// turning distance, found through experimentation with the physical robot
		double turningDistance = 23.1; 
		/*
		 * Sets the size of the commands array, needs to be [n =
		 * numberOfCommands][3]
		 */
		commands = new double[3][3];
		/*
		 * Last number tells you whether its the elevator or motors running 1 =
		 * motors, 2 = elevator First number tells the motors on the left side
		 * distance forwards or the elevators level that it needs to go second
		 * number tells right side motor
		 */
		raiseTote(0);
		driveForward(84 + 28, 1);
		dropTote(2);
	}

	public void driveForward(int distance, int i) {
		// 1 Command
		commands[i] = convert(distance, distance, 1);
	}

	public void raiseTote(int i) {
		// 1 command
		commands[i] = convert(2, 0, 2);
	}

	public void dropTote(int i) {
		// 1 command
		commands[i] = convert(0, 0, 2);
	}

	public double[] convert(double num1, double num2, double n) {
		double[] number = new double[3];
		number[0] = num1;
		number[1] = num2;
		number[2] = n;
		return number;
	}

	public void autonomousPeriodic() {

		// if the robot is not calibrating then it will run the set of commands
		if (!calibrating) {
			//
			if (num_i < commands.length) {
				if (commands[num_i][2] == 1) {
					driveTrain.drive(commands[num_i][0], commands[num_i][1]);
				} else if (commands[num_i][2] == 2) {
					driveTrain.raiseElevator(commands[num_i][0]);
				}
				driveTrain.runSystems();
			} else {
				driveTrain.setWheelSystemPowers(0, 0);
				driveTrain.elevator.setSpeed(0);
				driveTrain.runSystems();
			}
		} else {
			// calibrates the robot
			driveTrain.calibrateElevator();
		}
	}

	public void teleopPeriodicInit() {
		// refer to autonomousInit
		driveTrain.resetDistance();
		calibrating = true;
	}

	public void teleopPeriodic() {
		// refer to autonomousPeriodic
		if (!calibrating) {
			// Runs the robot (refer to DriveTrain class)
			driveTrain.runRobot();
		} else {
			driveTrain.calibrateElevator();
		}
	}

	public void testPeriodic() {
	}

}
