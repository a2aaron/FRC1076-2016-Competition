package org.usfirst.frc.team1076.robot.statemachine;

import java.util.concurrent.TimeUnit;

public class ArmExtendAutonomous extends AutoState {
	public enum ExtendDirection {
		Forwards, Backwards
	};

	ExtendDirection extendDirection;
	long timeStart;
	long timeLimit;
	boolean started = false;
	double speed;

	public ArmExtendAutonomous(int millis, double speed, ExtendDirection extendDirection) {
		this.speed = speed;
		this.timeLimit = millis;
		this.extendDirection = extendDirection;
	}

	public ArmExtendAutonomous(int millis, ExtendDirection extendDirection) {
		this(millis, 1, extendDirection);
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
	public double armExtendSpeed() {
		if (shouldChange()) {
			return 0;
		} else {
			switch (extendDirection) {
			case Forwards:
				return speed;
			case Backwards:
				return -speed;
			default:
				return 0;
			}
		}
	}
}
