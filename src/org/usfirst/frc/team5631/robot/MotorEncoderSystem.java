/* 
 * So we will probably need to know the PPR of the encoder, 
 * because that seems to vary between units. The following 
 * video explains how encoders work so using that, I am 
 * hoping we can at least figure out which direction our 
 * encoders are spinning and use that to make sure they 
 * spin exactly how we want them to.
 * https://www.youtube.com/watch?v=005lyrHGeE8
 */
package org.usfirst.frc.team5631.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;

public class MotorEncoderSystem {
	// Our little power things for the motors
	public Talon front, rear, main;
	// The sensors that read how fast a motor is spinning
	public Encoder encoder;

	double power, speed, time, actualSpeed;

	// PID Variables
	double error, target_speed, net_error, sum_error, diff_error, prev_error;
	double kP, kI, kD;
	public double distance;

	/**
	 * So from what I remember, every Motor has a talon or in this case 2 talons.
	 * The motor encoder system is grouping the talons which I believe provide power to the motors to the
	 * @param front not sure what the front motor is
	 * @param rear not sure what the rear motor is
	 * @param encoder this is the encoder for the motor
	 */
	public MotorEncoderSystem(Talon front, Talon rear, Encoder encoder) {

		this.front = front;
		this.rear = rear;
		this.encoder = encoder;
		this.encoder.setDistancePerPulse((4 * Math.PI) / 1000);

		init();

		kP = 0.015;
		kI = 0.000005;
		kD = 0.001;

	}
	/**
	 * 
	 * @param main is the Talon(motor) which needs to be turned.
	 * @param encoder is the encoder which is being read from
	 */
	public MotorEncoderSystem(Talon main, Encoder encoder) {

		this.main = main;
		this.encoder = encoder;
		this.encoder.setDistancePerPulse(0.196 / 7);

		init();

		kP = 0.02;
		kI = 0.000005;
		kD = 0.001;

	}

	public void init() {

		power = speed = time = 0;

		// initialize PID Vars
		error = target_speed = net_error = sum_error = prev_error = diff_error = 0;

	}

	public void runMotors() {

		distance = encoder.getDistance() * 4;
		System.out.println("dist: " + distance);

		time++;

		if (time > 1) {
			PID();
			time = 0;
		}

		if (power > 1)
			power = 1;
		if (power < 0.15 && power > -0.15 && speed == 0)
			power = 0;

		front.set(power);
		rear.set(power);

	}

	public void runMotor() {

		distance = encoder.getDistance() / 3.159245;

		time++;

		if (speed == 0) {
			kP = 0.020;
			power = 0;
		} else {
			kP = 0.03;
		}

		if (time > 1) {
			PID();
			time = 0;
		}

		if (power > 1)
			power = 1;

		if (Robot.calibrating == false) {
			if (distance <= 1 && power < 0) {
				power = 0;
				speed = 0;
				sum_error = 0;
			}
			if (distance >= 25.5 && power > 0) {
				power = 0;
				speed = 0;
				sum_error = 0;
			}
		}
		//speed - The speed value between -1.0 and 1.0 to set
		main.set(power);
		System.out.println("Main Power: "+main.get());
		System.out.println("Front Power:"+front.get());
		System.out.println("Rear Power: "+rear.get());
	}
	/**
	 * resets the distance for the encoder.
	 */
	public void resetDist() {
		encoder.reset();
	}
	/**
	 * Physics :D
	 * https://www.youtube.com/watch?v=XfAt6hNV8XM
	 * #Gains ;D
	 * 
	 */
	public void PID() {
		actualSpeed = encoder.getRate();
		
		target_speed = speed;

		error = target_speed - actualSpeed;

		sum_error = sum_error + error;

		diff_error = error - prev_error;
		prev_error = error;
		net_error = kP * error + kI * sum_error; // + kD*diff_error;

		power = power + net_error;

	}

	public void setSpeed(double speed) {

		this.speed = speed / 4;

	}
	/**
	 * So I'm not sure what the numbers stand for, I thinkit's the current height of the elevator
	 * @return
	 */
	public double getLevel() {
		if (distance < 2.32) {
			return 0;
		} else if (distance > 2.32 && distance < 25) {
			return 1;
		} else {
			return 2;
		}
	}
	/**
	 * Sets the amount of power being sent to the 
	 * @param speed
	 */
	public void setSpeedElev(double speed) {
		this.speed = speed * 2;
	}
	/**
	 * Sets the amount of power being sent to the talons 
	 * @param power is how much power is sent to the motor
	 */
	public void setPower(double power) {

		this.power = power;

	}

}
