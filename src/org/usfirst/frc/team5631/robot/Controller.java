package org.usfirst.frc.team5631.robot;

import edu.wpi.first.wpilibj.Joystick;

public class Controller {

	Joystick joyStick;

	/**
	 * 
	 * @param joyStick 
	 */
	public Controller(Joystick joyStick) {

		this.joyStick = joyStick;

	}

	/**
	 * 
	 * @param input
	 * @return
	 */
	public double getAxisValue(int input) {
		double axisValue = joyStick.getRawAxis(input);

		return axisValue;
	}

	/**
	 * 
	 * @param input
	 * @return
	 */
	public boolean getButtonState(int input) {
		boolean state = joyStick.getRawButton(input);

		return state;
	}

	/**
	 * 
	 * @param input
	 * @return
	 */
	public double getPovState(int input) {

		return joyStick.getPOV(input);

	}

}
