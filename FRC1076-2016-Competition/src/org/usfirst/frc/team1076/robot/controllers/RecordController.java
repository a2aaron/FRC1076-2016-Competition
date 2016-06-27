package org.usfirst.frc.team1076.robot.controllers;

import java.awt.List;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
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
    String fileName;
    // Contain all of the data about each controller.
    // driveTrain, brakes, shiftLow, shiftHigh, turboArm
    Object[] driverFrame = new Object[5];
    // intakeSpeed, intakeRaise, armSpeed, armExtendSpeed, operatorTurbo
    Object[] operatorFrame = new Object[5];

    LinkedList<Object[]> frames;

    boolean isRecording = false;
    public RecordController(String fileName, IDriverInput driverInput, IOperatorInput operatorInput) {
        this.driverInput = driverInput;
        this.operatorInput = operatorInput;
        this.fileName = fileName;
        // Appends to the file.
        try {
            this.ois = new ObjectOutputStream(new FileOutputStream(fileName, true));
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
            Object[] frame = new Object[]{deltaTime, driverFrame, operatorFrame};
            frames.addLast(frame);
        } else {
            throw new RuntimeException("Not recording! (call startRecording())");
        }
    }

    public void getDriverFrame() {
        driverFrame[0] = driverInput.driveTrainSpeed();
        driverFrame[1] = driverInput.brakesApplied();
        driverFrame[2] = driverInput.shiftLow();
        driverFrame[3] = driverInput.shiftHigh();
        driverFrame[4] = driverInput.turboArm();
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