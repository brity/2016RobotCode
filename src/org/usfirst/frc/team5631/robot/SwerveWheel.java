/**
 * 
 */
package org.usfirst.frc.team5631.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;

/**
 * @author Nano
 *
 */
public class SwerveWheel {
	private Talon horizontal, vertical;
	private Encoder encoder;
	private double degRot;
	public SwerveWheel(int horizontalAxisPort,int verticalAxisPort,int encoderAPinPort, int encoderBPinPort) {
		horizontal=new Talon(horizontalAxisPort);
		vertical = new Talon(verticalAxisPort);
		encoder=new Encoder(encoderAPinPort,encoderBPinPort);
	}
	public void turn(){
		
	}

}
