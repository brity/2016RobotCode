package org.usfirst.frc.team5631.robot;

public class Methods {

	public static boolean getParameters(double input, double para) {

		if (input > para || input < -para) {
			return true;
		} else {
			return false;
		}

	}

}
