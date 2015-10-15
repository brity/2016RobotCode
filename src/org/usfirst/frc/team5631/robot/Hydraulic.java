
package org.usfirst.frc.team5631.robot;

import edu.wpi.first.wpilibj.Solenoid;

public class Hydraulic {

	private String name;
	public Solenoid sol1;
	public Solenoid sol2;
	boolean pull, freeze;
	private boolean pushBtn, pullBtn;

	/*
	 * Makes a single solenoid at a specific channel
	 * 
	 * @param challen is the channel on the PCM to control (0..7).
	 */
	public Hydraulic(int channel) {
		sol = new Solenoid(channel);
		pull = false;
		freeze = true;
	}
	/**
	 * pushes the solenoid, once I know the pins
	 */
	public void push() {
		System.out.println("Pushing out solenoid: " + name);
		if (!pull && freeze) {
			sol.set(true);
		}
	}
	/**
	 * 
	 */
	public void pull() {
		System.out.println("Pulling in solenoid: " + name);
		if (pull && freeze) {
			sol.set(false);
=======
		sol1 = new Solenoid(channel);
		pull = false;
		freeze = true;
	}

	public void pushHydro() {
		System.out.println("Pushing out solenoid: " + name);
		if (!pull && freeze) {
		}
	}

	public void pullHydro() {
		System.out.println("Pulling in solenoid: " + name);
		if (pull && freeze) {
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void pushOrPull(int btn) {
		switch (btn) {
		case 1:
			break;
		case 7:
			break;
		}
		/*
		 * Will push or pull depending on what button is pressed.
		 */
		if (pushBtn) {
			push();
		} else if (pullBtn) {
			pull();
			pushHydro();
		} else if (pullBtn) {
			pullHydro();
		}
	}
}
}
