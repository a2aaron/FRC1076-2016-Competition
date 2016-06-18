package org.usfirst.frc.team1076.robot.statemachine;

import java.util.concurrent.TimeUnit;

public class ArmAutonomous extends AutoState {
	// MAJOR TODO: Use Encoders or Limit Switches
	//(once they're put on the robot)
	public enum LiftDirection {
		Up, Down
	};

	LiftDirection liftDirection;
	long timeStart;
	long timeLimit;
	boolean started = false;
	double speed;

	public ArmAutonomous(int millis, double speed, LiftDirection liftDirection) {
		this.speed = speed;
		this.timeLimit = millis;
		this.liftDirection = liftDirection;
	}

	public ArmAutonomous(int millis, LiftDirection liftDirection) {
		this(millis, 1, liftDirection);
	}

	@Override
	public void init() {
		started = true;
		timeStart = System.nanoTime();
	}

	@Override
	public boolean shouldChange() {
		return started && TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - timeStart) > timeLimit;
	}

	@Override
	public double armSpeed() {
		if (shouldChange()) {
			return 0;
		} else {
			switch (liftDirection) {
			case Up:
				return speed;
			case Down:
				return -speed;
			default:
				return 0;
			}
		}
	}
}
