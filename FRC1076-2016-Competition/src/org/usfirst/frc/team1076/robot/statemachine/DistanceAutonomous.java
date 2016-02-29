package org.usfirst.frc.team1076.robot.statemachine;

import org.usfirst.frc.team1076.robot.gamepad.IInput.MotorOutput;

// TODO: Actually use SensorData to change motor output
// import org.usfirst.frc.team1076.udp.SensorData;

public class DistanceAutonomous extends AutoState {

	
	// GOAL: Drive the robot a specified distance in a specified time. This uses external data from the accelerometer,
	// encoder, or calibrated factors based on the motors.
	
	// Assuming calibrated factors for now.
	// ASSUMPTIONS: The motors move at a constant speed given a particular MotorOutput()
	// and  that MotorOutputs are periodic.
	
	double MOTOR_FACTOR = 1; // TODO: Find a reasonable value for this.
	
	double speed;
	double distanceTraveled = 0;
	double distance;
	
	public DistanceAutonomous(double distance, double time) {
		this.distance = distance;
		this.speed = distance / time;
	}

	
	@Override
	public void init() { }

	@Override
	public boolean shouldChange() {
		// TODO Auto-generated method stub

		return distanceTraveled > distance;
	}

	// time, in seconds of how often DistanceAutonomous is called.
	private long lastFrameTime = 0; // TODO: Find a reasonable value for this
	
	@Override
	public MotorOutput driveTrainSpeed() {
		if (lastFrameTime == 0) {
			lastFrameTime = System.nanoTime(); 
		}
		double deltaTime = (System.nanoTime() - lastFrameTime) / 1e9;
		lastFrameTime = System.nanoTime();
		
		distanceTraveled += speed * MOTOR_FACTOR * deltaTime;
		
		if (shouldChange()) {
			return new MotorOutput(0, 0);
		} else {
			return new MotorOutput(speed, speed);
		}
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
