package org.usfirst.frc.team3506.robot.subsystems;

import org.usfirst.frc.team3506.robot.RobotMap;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class RobotCompressor extends Subsystem {
	private Compressor compressor;

	public RobotCompressor() {
		compressor = new Compressor(RobotMap.PCM_ID);
	}

	public void turnOffCompressor() {
		compressor.stop();
	}
	
	public void turnOnCompressor() {
		compressor.start();
	}
    public void initDefaultCommand() {
    }
}

