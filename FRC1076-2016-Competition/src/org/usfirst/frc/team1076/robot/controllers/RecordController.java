package org.usfirst.frc.team1076.robot.controllers;

import java.awt.List;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

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
    Object[] driverFrame;
    Object[] operatorFrame;

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
        try {
            ois.writeChars(fileName);
            ois.writeLong(startTime);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void recordFrame() {
        long deltaTime = System.currentTimeMillis() - startTime;
        getDriverFrame();
        getOperatorFrame();

        try {
            ois.writeObject(new Object[] {deltaTime,
                                          driverFrame,
                                          operatorFrame});
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void getDriverFrame() {
        driverFrame = new Object[]{
                driverInput.driveTrainSpeed(),
                driverInput.brakesApplied(),
                driverInput.shiftLow(),
                driverInput.shiftHigh(),
                driverInput.turboArm()
        };
    }

    public void getOperatorFrame() {
        operatorFrame = new Object[]{
                operatorInput.intakeSpeed(),
                operatorInput.intakeRaiseState(),
                operatorInput.armSpeed(),
                operatorInput.armExtendSpeed(),
                operatorInput.turboArm()
        };
    }

    public void stopRecording() {
        try {
            ois.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
