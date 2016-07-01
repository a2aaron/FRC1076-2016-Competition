package org.usfirst.frc.team1076.robot.controllers;

import java.awt.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;

import org.usfirst.frc.team1076.robot.gamepad.IDriverInput;
import org.usfirst.frc.team1076.robot.gamepad.IOperatorInput;

public class RecordController {
    /**
     * This class records inputs and outputs
     * them to a file. File is an ObjectOutputStream.
     *
     * Do not switch input methods during the recording. It will not record them.
     * Does not send motorOutputs, you must do this with teleopContoller.
     *
     */
    ObjectOutputStream ois;
    IDriverInput driverInput;
    IOperatorInput operatorInput;
    long startTime;
    File file;
    // Contain all of the data about each controller.
    // driveTrain, brakes, shiftLow, shiftHigh, turboArm
    Object[] driverFrame = new Object[6];
    // intakeSpeed, intakeRaise, armSpeed, armExtendSpeed, operatorTurbo
    Object[] operatorFrame = new Object[5];

    LinkedList<RecordFrame> frames = new LinkedList<RecordFrame>();

    boolean isRecording = false;
    public RecordController(File file, IDriverInput driverInput, IOperatorInput operatorInput) {
        this.driverInput = driverInput;
        this.operatorInput = operatorInput;
        this.file = file;
        // Appends to the file.
        try {
            this.ois = new ObjectOutputStream(new FileOutputStream(file));
        } catch (FileNotFoundException e) {
        } catch (IOException e) { }
    }

    public void startRecording() {
        startTime = System.currentTimeMillis();
        isRecording = true;
    }

    public void recordFrame() {
        if(isRecording) {
            long deltaTime = System.currentTimeMillis() - startTime;
            getDriverFrame();
            getOperatorFrame();
            RecordFrame frame = new RecordFrame(driverFrame, operatorFrame);
            frames.add(frame);
        } else {
            throw new RuntimeException("Not recording! (call startRecording())");
        }
    }

    public void getDriverFrame() {
        driverFrame[0] = driverInput.driveTrainSpeed().left;
        driverFrame[1] = driverInput.driveTrainSpeed().right;
        driverFrame[2] = driverInput.brakesApplied();
        driverFrame[3] = driverInput.shiftLow();
        driverFrame[4] = driverInput.shiftHigh();
        driverFrame[5] = driverInput.turboArm();
    }

    public void getOperatorFrame() {
        operatorFrame[0] = operatorInput.intakeSpeed();
        operatorFrame[1] = operatorInput.intakeRaiseState();
        operatorFrame[2] = operatorInput.armSpeed();
        operatorFrame[3] = operatorInput.armExtendSpeed();
        operatorFrame[4] = operatorInput.turboArm();
    }

    public void stopRecording() {
        try {
            ois.writeObject(frames);
            ois.close();
            isRecording = false;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public boolean isRecording() {
        return isRecording;
    }
}

class RecordFrame implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 6006282172422543098L;
    Object[] driverFrame = new Object[5];
    Object[] operatorFrame = new Object[5];
    
    public RecordFrame(Object[] driverFrame, Object[] operatorFrame) {
        this.driverFrame = driverFrame;
        this.operatorFrame = operatorFrame;
    }
}