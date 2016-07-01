package org.usfirst.frc.team1076.robot.gamepad;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.LinkedList;

public class ReplayInput implements IOperatorInput, IDriverInput{
    /**
     * ReplayInput takes a saved replay file and outputs those to the robot.
     * A replay file consists of the various possible outputs (driveTrain, brakes
     * arm extension motor, etc). 
     * */
    boolean replaying = false;
    File file;
    ObjectInputStream ois;
    long time;
    LinkedList<Object[]> frames; 
    Object[] driverFrame;
    Object[] operatorFrame;

    public ReplayInput(File file) {
        this.file = file;
        try {
            this.ois = new ObjectInputStream(new FileInputStream(file));
            frames = (LinkedList<Object[]>) ois.readObject();
            ois.close();
        } catch (FileNotFoundException e) {
            System.out.println("Can't find the file " + file.getName());
            e.printStackTrace();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void getFrame() throws Exception {
        if (frames.isEmpty()) {
            replaying = false;
            driverFrame = null;
            operatorFrame = null;
            throw new Exception("Reached EOF");
        } else {
            replaying = true;
            Object[] frame = frames.getFirst();
            time = (long) frame[0];
            driverFrame = (Object[]) frame[1];
            operatorFrame = (Object[]) frame[2];
            frames.removeFirst();
        }
    }

    public boolean isReplaying() {
        return replaying;
    }
    @Override
    public MotorOutput driveTrainSpeed() {
        double left = (double) driverFrame[0];
        double right = (double) driverFrame[1];
        return new MotorOutput(left, right);
    }

    @Override
    public boolean brakesApplied() {
        return (boolean) driverFrame[2];
    }

    @Override
    public boolean shiftHigh() {
        return (boolean) driverFrame[3];
    }

    @Override
    public boolean shiftLow() {
        return (boolean) driverFrame[4];
    }

    @Override
    public ControlSide controlSide() {
        // What would go here? You can't switch while
        // in this input.
        return null;
    }

    public boolean driverTurbo() {
        return (boolean) driverFrame[5];
    }

    @Override
    public double intakeSpeed() {
        return (double) operatorFrame[0];
    }

    @Override
    public IntakeRaiseState intakeRaiseState() {
        return (IntakeRaiseState) operatorFrame[1];
    }

    @Override
    public double armSpeed() {
        return (double) operatorFrame[2];
    }

    @Override
    public double armExtendSpeed() {
        return (double) operatorFrame[3];
    }

    public boolean operatorTurbo() {
        return (boolean) operatorFrame[4];
    }

    // Use driverTurbo and operatorTurbo instead.
    @Override @Deprecated
    public boolean turboArm() {
        return driverTurbo() || operatorTurbo();
    }

    // Never use this
    @Override @Deprecated
    public boolean replayButtonHeld() {
        return false;
    }
}
