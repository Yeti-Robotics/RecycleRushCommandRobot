package org.usfirst.frc.team3506.robot;

import org.usfirst.frc.team3506.robot.autonomi.AutonomousCommandGroup;
import org.usfirst.frc.team3506.robot.commands.LoadRecordingCommand;
import org.usfirst.frc.team3506.robot.commands.RecordCommand;
import org.usfirst.frc.team3506.robot.commands.SaveRecordingCommand;
import org.usfirst.frc.team3506.robot.commands.arm.MoveArmDownCommand;
import org.usfirst.frc.team3506.robot.commands.arm.MoveArmHalfUpCommand;
import org.usfirst.frc.team3506.robot.commands.arm.MoveArmUpCommand;
import org.usfirst.frc.team3506.robot.commands.arm.ResetArmEncoderCommand;
import org.usfirst.frc.team3506.robot.commands.canPole.ExtendCanPoleCommand;
import org.usfirst.frc.team3506.robot.commands.canPole.RetractCanPoleCommand;
import org.usfirst.frc.team3506.robot.commands.claw.CloseClawCommand;
import org.usfirst.frc.team3506.robot.commands.claw.OpenClawCommand;
import org.usfirst.frc.team3506.robot.commands.claw.TurnForwardBeltCommand;
import org.usfirst.frc.team3506.robot.commands.claw.TurnOffBeltCommand;
import org.usfirst.frc.team3506.robot.commands.claw.TurnReverseBeltCommand;
import org.usfirst.frc.team3506.robot.commands.compressor.TurnOffCompressorCommand;
import org.usfirst.frc.team3506.robot.commands.compressor.TurnOnCompressorCommand;
import org.usfirst.frc.team3506.robot.commands.drive.UniversalDriveCommand;
import org.usfirst.frc.team3506.robot.commands.elevator.LiftElevatorCommand;
import org.usfirst.frc.team3506.robot.commands.elevator.LowerElevatorCommand;
import org.usfirst.frc.team3506.robot.commands.scheduler.RebootSchedulerCommand;
import org.usfirst.frc.team3506.robot.domain.RobotInput;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	// Joysticks
	private static Joystick leftDriveJoy;
	private static Joystick rightDriveJoy;
	private static Joystick armJoy;
	private static boolean arcadeMode;

	public OI(){
		leftDriveJoy = new Joystick(RobotMap.JOYSTICK_LEFT_PORT);
		rightDriveJoy = new Joystick(RobotMap.JOYSTICK_RIGHT_PORT);
		armJoy = new Joystick(RobotMap.JOYSTICK_ARM_PORT);
		arcadeMode = true;
		// Commands and buttons
		
		// Left Drive Joystick
		setJoystickButtonCommand(leftDriveJoy, 1, new RebootSchedulerCommand());
		setJoystickButtonCommand(leftDriveJoy, 8, new TurnOnCompressorCommand());
		setJoystickButtonCommand(leftDriveJoy, 9, new TurnOffCompressorCommand());
		setJoystickButtonCommand(leftDriveJoy, 2, new RecordCommand());
		setJoystickButtonCommand(leftDriveJoy, 3, new LoadRecordingCommand());
		setJoystickButtonCommand(leftDriveJoy, 11, new SaveRecordingCommand());
		setJoystickButtonCommand(leftDriveJoy, 10, new AutonomousCommandGroup());

		// Right Drive Joystick
		setJoystickButtonCommand(rightDriveJoy, 3, new ExtendCanPoleCommand());
		setJoystickButtonCommand(rightDriveJoy, 2, new RetractCanPoleCommand());
		setJoystickButtonCommand(rightDriveJoy, 4, new LiftElevatorCommand());
		setJoystickButtonCommand(rightDriveJoy, 1, new LowerElevatorCommand());
//		setJoystickButtonCommand(rightDriveJoy, 9, new UniversalDriveCommand(90, 0, 0.1));
//		setJoystickButtonCommand(rightDriveJoy, 10, new UniversalDriveCommand(0, 3, 0.2));
//		setJoystickButtonCommand(rightDriveJoy, 11, new MoveArmHalfUpCommand());
		
		// Arm Joystick
		setJoystickButtonCommand(armJoy, 2, new CloseClawCommand());
		setJoystickButtonCommand(armJoy, 3, new OpenClawCommand());
//		setJoystickButtonCommand(armJoy, 1, new ResetArmEncoderCommand());
//		setJoystickButtonCommand(armJoy, 11, new MoveArmUpCommand());
//		setJoystickButtonCommand(armJoy, 10, new MoveArmDownCommand());
		setJoystickButtonCommand(armJoy, 6, new TurnForwardBeltCommand());
		setJoystickButtonCommand(armJoy, 7, new TurnReverseBeltCommand());
		setJoystickButtonCommand(armJoy, 8, new TurnOffBeltCommand());
	}
	public double getLeftX() {
		return deadZoneMod(leftDriveJoy.getX());
	}
	public double getLeftY(){
		return deadZoneMod(leftDriveJoy.getY());
	}
	public double getLeftZ() {
		return throttleMod(leftDriveJoy.getZ());
	}
	public double getRightX(){
		return deadZoneMod(rightDriveJoy.getX());
	}
	public double getRightY(){
		return deadZoneMod(rightDriveJoy.getY());
	}
	public double getArmY(){
		return deadZoneMod(armJoy.getY());
	}
	public double getArmZ(){
		return deadZoneMod(armJoy.getZ());
	}
	private double deadZoneMod(double joyVal){
		if (Math.abs(joyVal) > RobotMap.JOYSTICK_DEADZONE) {
			return joyVal * RobotMap.JOYSTICK_MODIFIER;
		} else {
			return 0.0;
		}
	}
	private double throttleMod(double joyVal) {
		return (joyVal + 1.0) / 2.0;
	}
	public Joystick getLeftDriveJoy(){
		return leftDriveJoy;
	}
	public Joystick getRightDriveJoy(){
		return rightDriveJoy;
	}
	public void toggleDriveMode() {
		arcadeMode = !arcadeMode;
	}
	public boolean isArcadeMode() {
		return arcadeMode;
	}
	private void setJoystickButtonCommand(Joystick joystick, int button, Command command) {
		new JoystickButton(joystick, button).whenPressed(command);
		if (leftDriveJoy == joystick) {
			RobotInput.leftCommands[button - 1] = command;
		} else if (rightDriveJoy == joystick) {
			RobotInput.rightCommands[button - 1] = command;	
		}
	}
}

