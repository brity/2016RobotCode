<<<<<<< HEAD
=======

>>>>>>> nanobranch
package org.usfirst.frc.team5631.robot;

import edu.wpi.first.wpilibj.Solenoid;

public class Hydraulic {
<<<<<<< HEAD

	private String name;
	public Solenoid sol;
	public Solenoid sol2;
	boolean pull, freeze;
	int channel = 9;
	private boolean pushBtn, pullBtn;

	/*
	 * Makes a single solenoid at a specific channel
	 * 
	 * @param challen is the channel on the PCM to control (0..7).
	 */
=======
	public Solenoid sol;
	boolean pull, freeze;
	private boolean pushBtn, pullBtn;
	private int pinChannel;

>>>>>>> nanobranch
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
<<<<<<< HEAD
		}
		sol = new Solenoid(channel);
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
/*
	public void pushOrPull(int btnPress) {
		// TODO Auto-generated method stub

	}
}*/
=======
	}
}
>>>>>>> nanobranch
