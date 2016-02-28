package org.usfirst.frc.team1076.robot.statemachine;

import org.usfirst.frc.team1076.robot.gamepad.IInput.MotorOutput;
import org.usfirst.frc.team1076.udp.SensorData;

public class DistanceAutonomous extends AutoState {

	
	// GOAL: Drive the robot a specified distance in a specified time. This uses external data from the accelerometer,
	// encoder, or calibrated factors based on the motors.
	
	// Assuming calibrated factors for now.
	// ASSUMPTIONS: The motors move at a constant speed given a particular MotorOutput()
	// 	            That MotorOutputs are periodic
	
	private double MOTOR_FACTOR = 1; // TODO: Find a reasonable value for this.
	private double PERIOD = 0.1; // TODO: Find a reasonable value for this.
	private double speed;
	private double distanceTraveled = 0;
	private double distance;
	
	public DistanceAutonomous(double distance, double time) {
		this.distance = distance;
		this.speed = distance / time;
	}

	
	@Override
	public void init() {  }

	@Override
	public boolean shouldChange() {
		// TODO Auto-generated method stub
		return distanceTraveled > distance;
	}

	@Override
	public MotorOutput driveTrainSpeed() {
		
		distanceTraveled += speed * MOTOR_FACTOR * PERIOD;
		
		return new MotorOutput(speed, speed);
	}

	@Override
	public double armSpeed() {
		return 0;
	}

	@Override
	public double intakeSpeed() {
		return 0;
	}

}
