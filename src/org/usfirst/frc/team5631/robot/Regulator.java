package org.usfirst.frc.team5631.robot;

public class Regulator {

	double speedZ, speedY, throttle;

	public Regulator() {
		speedZ = speedY = throttle = 0;

	}

	/*
	 * 
	 */
	public double regulateLeftSpeed() {
		double speedLeft = 0;

		speedLeft = -speedY;
		// If speedZ is faster than speedY, the left side speeds up
		if (speedZ > 0 && speedY <= 0) {
			speedLeft += speedZ;
		}
		// If speedZ is less than speedY or 0, the left side speeds up. You get
		// the gist of it.
		if (speedZ < 0 && speedY <= 0) {
			speedLeft += speedZ;
		}
		if (speedZ > 0 && speedY > 0) {
			speedLeft += speedZ;
		}
		if (speedZ < 0 && speedY > 0) {
			speedLeft += speedZ;
		}

		return speedLeft * throttle;
		// Now if only I knew what speedZ was
	}

	public double regulateRightSpeed() {
		double speedRight = 0;

		speedRight = -speedY;

		if (speedZ < 0 && speedY <= 0) {
			speedRight -= speedZ;
		}
		if (speedZ > 0 && speedY <= 0) {
			speedRight -= speedZ;
		}

		if (speedZ < 0 && speedY > 0) {
			speedRight -= speedZ;
		}
		if (speedZ > 0 && speedY > 0) {
			speedRight -= speedZ;
		}
		

		return speedRight * throttle;
		// Now if only I knew what speedY was
	}

	public void setThrottle(double throttle) {
		// We make it negative because the value we get I believe is -1 to 0
		System.out.println("Throttle: " + throttle);
		this.throttle = -throttle;
	}

	/**
	 * 
	 * @param speed
	 *            is the speed at which the 
	 * @param i
	 */
	public void setSpeed(double speed, int i) {
		if (i == 1)
			speedY = speed;
		if (i == 2)
			speedZ = speed / 2;
	}

}
