package org.usfirst.frc.team1076.robot.recordAndReplay;

import java.io.Serializable;

public class RecordFrame implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 6006282172422543098L;
    public DriverFrame driverFrame;
    public OperatorFrame operatorFrame;
    
    public RecordFrame(DriverFrame driverFrame, OperatorFrame operatorFrame2) {
        this.driverFrame = driverFrame;
        this.operatorFrame = operatorFrame2;
    }
}