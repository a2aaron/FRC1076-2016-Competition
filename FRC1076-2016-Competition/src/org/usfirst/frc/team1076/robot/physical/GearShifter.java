package org.usfirst.frc.team1076.robot.physical;

import org.usfirst.frc.team1076.robot.IRobot;
import org.usfirst.frc.team1076.robot.IRobot.SolenoidValue;
import org.usfirst.frc.team1076.robot.sensors.GearShiftStateManager;

public class GearShifter extends GearShiftStateManager {
	GearStates currentGear;
	
	@Override
	protected void shiftGear(GearStates newState, IRobot robot) {
		currentGear = newState;
		if (newState == GearStates.High) {
			robot.setGear(SolenoidValue.Reverse);
		} else if (newState == GearStates.Low) {
			robot.setGear(SolenoidValue.Forward);
		} 
	}
	
	public GearStates getGearState() {
		return currentGear;
	}
}
