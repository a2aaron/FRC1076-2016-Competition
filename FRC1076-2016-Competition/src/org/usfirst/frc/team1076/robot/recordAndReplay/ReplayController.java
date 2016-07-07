package org.usfirst.frc.team1076.robot.recordAndReplay;

import org.usfirst.frc.team1076.robot.IRobot;
import org.usfirst.frc.team1076.robot.gamepad.IDriverInput.MotorOutput;
import org.usfirst.frc.team1076.robot.physical.GearShifter;

public class ReplayController {
    ReplayInput replayInput;
    GearShifter gearShifter;

    // TODO: Putting constants into the controllers is a bad idea.
    double driverTurboSpeed = 1;
    double operatorTurboSpeed = 0.75;
    double armUpSpeed = 0.4;
    double armDownSpeed = 0.23;

    public ReplayController(ReplayInput replayInput) {
        this.replayInput = replayInput;
        gearShifter = new GearShifter();
    }

    public void replayInit(IRobot robot) {

    }

    public void replayPeriodic(IRobot robot) {
        replayInput.getFrame();
        double armSpeed = replayInput.armSpeed();
        if (armSpeed > 0) {
            robot.setArmSpeed(armSpeed * armUpSpeed);
        } else {
            if (replayInput.driverTurbo()) {
                robot.setArmSpeed(armSpeed * driverTurboSpeed);
            } else if (replayInput.operatorTurbo()) {
                robot.setArmSpeed(armSpeed * operatorTurboSpeed);
            } else {
                robot.setArmSpeed(armSpeed * armDownSpeed);
            }
        }
        robot.setArmExtendSpeed(replayInput.armExtendSpeed());
        robot.setIntakeSpeed(replayInput.intakeSpeed());
        robot.setIntakeElevation(replayInput.intakeRaiseState());
        MotorOutput drive = replayInput.driveTrainSpeed();
        robot.setLeftSpeed(drive.left);
        robot.setRightSpeed(drive.right);
        robot.setBrakes(replayInput.brakesApplied());

        if (replayInput.shiftHigh()) {
            gearShifter.shiftHigh(robot);
        } else if (replayInput.shiftLow()) {
            gearShifter.shiftLow(robot);
        } else {
            // gearShifter.shiftAuto(robot);
        }

    }

    public boolean replaying() {
        return replayInput.isReplaying();
    }
}
