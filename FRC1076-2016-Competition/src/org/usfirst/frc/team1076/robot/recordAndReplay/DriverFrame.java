package org.usfirst.frc.team1076.robot.recordAndReplay;

import java.io.Serializable;

import org.usfirst.frc.team1076.robot.gamepad.IDriverInput;
import org.usfirst.frc.team1076.robot.gamepad.IDriverInput.ControlSide;

public class DriverFrame implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5887518044847225176L;
    public double driveTrainSpeedLeft;
    public double driveTrainSpeedRight;
    public boolean brakesApplied;
    public boolean shiftLow;
    public boolean shiftHigh;
    public boolean turboArm;
    public ControlSide controlSide;
    public DriverFrame(IDriverInput driverInput) {
        driveTrainSpeedLeft = driverInput.driveTrainSpeed().left;
        driveTrainSpeedRight = driverInput.driveTrainSpeed().right;
        brakesApplied = driverInput.brakesApplied();
        shiftLow = driverInput.shiftLow();
        shiftHigh = driverInput.shiftHigh();
        turboArm = driverInput.turboArm();
        controlSide = driverInput.controlSide();
    }

}
