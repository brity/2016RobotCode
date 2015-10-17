
package org.usfirst.frc.team5631.robot;

import edu.wpi.first.wpilibj.Solenoid;

public class Hydraulic {
	public Solenoid sol;
	boolean pull, freeze;
	private boolean pushBtn, pullBtn;
	private int pinChannel;

	public Hydraulic(int channel) {
		sol = new Solenoid(channel);
		freeze = true;
		this.pinChannel = channel;

	}

	/**
	 * pushes the solenoid, once I know the pins
	 */
	public void push() {
		if (!freeze)
			sol.set(true);

	}

	/**
	 * 
	 */
	public void pull() {
		if (!freeze)
			sol.set(false);
	}
}