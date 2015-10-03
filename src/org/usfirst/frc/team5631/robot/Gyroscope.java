
package org.usfirst.frc.team5631.robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Gyro;

public class Gyroscope {
	/*
	 * In this case, x is the horizontal, left-right plane. Y is the vertical,
	 * up down plane. And Z is the Horizontal forward, back plane.
	 */
	public double x, y, z;

	Gyro g;

	/*
	 * @param The analog channel the gyro is connected to. Gyros can only be
	 * used on on-board channels 0-1.
	 */
	public Gyroscope(int c) {
		x = y = z = 0;
		g = new Gyro(c);
	}

	/*
	 * @param The analog channel the gyro is connected to. Gyros can only be
	 * used on on-board channels 0-1.
	 */
	public Gyroscope(AnalogInput c) {
		x = y = z = 0;
		g = new Gyro(c);
	}

}
