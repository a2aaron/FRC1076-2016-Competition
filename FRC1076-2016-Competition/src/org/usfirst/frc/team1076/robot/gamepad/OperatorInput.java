package org.usfirst.frc.team1076.robot.gamepad;

import org.usfirst.frc.team1076.robot.gamepad.IGamepad.GamepadAxis;
import org.usfirst.frc.team1076.robot.gamepad.IGamepad.GamepadButton;

public class OperatorInput implements IOperatorInput {
	IGamepad gamepad;
	
	public OperatorInput(IGamepad gamepad) {
		this.gamepad = gamepad;
	}
	
	@Override
	public double armSpeed() {
		return gamepad.getAxis(GamepadAxis.RightY);
	}

	@Override
	public double armExtendSpeed() {
		return -gamepad.getAxis(GamepadAxis.LeftY);
	}
	
	@Override
	public double intakeSpeed() {
		double in = gamepad.getAxis(GamepadAxis.LeftTrigger);
		double out = gamepad.getAxis(GamepadAxis.RightTrigger);
		return in - out;
	}	

	@Override
	public IntakeRaiseState intakeRaiseState() {
		boolean up = gamepad.getButton(GamepadButton.LB) ||
				gamepad.getButton(GamepadButton.Y);
		boolean down = gamepad.getButton(GamepadButton.RB) ||
				gamepad.getButton(GamepadButton.A);
		if (up && down) {
			return IntakeRaiseState.Neutral;
		} else if (up) {
			return IntakeRaiseState.Lowered;
		} else if (down) {
			return IntakeRaiseState.Raised;
		}		
		return IntakeRaiseState.Neutral;
	}

	@Override
	public boolean operatorTurbo() {
		return gamepad.getButton(GamepadButton.B);
	}
}

