package org.usfirst.frc.team1076.test.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.usfirst.frc.team1076.robot.gamepad.IDriverInput.MotorOutput;
import org.usfirst.frc.team1076.robot.statemachine.ArmExtendAutonomous;
import org.usfirst.frc.team1076.robot.statemachine.AutoState;
import org.usfirst.frc.team1076.robot.statemachine.ArmExtendAutonomous.ExtendDirection;

public class ArmExtendAutonomousTest {
	private static final double EPSILON = 1e-12;

	@Test
	public void testArmExtending() {
		ArmExtendAutonomous auto = new ArmExtendAutonomous(50, 1.0, ExtendDirection.Forwards);
		auto.init();

		assertFalse("ArmExtendAutonomous should not change before doing work!", auto.shouldChange());
		assertEquals(1.0, auto.armExtendSpeed(), EPSILON);

		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
		}

		assertEquals(0.0, auto.armExtendSpeed(), EPSILON);
		assertTrue("ArmExtendAutonomous should change after doing work!", auto.shouldChange());
	}

	public void testArmRetracting() {
		AutoState auto = new ArmExtendAutonomous(100, 1.0, ExtendDirection.Backwards);
		auto.init();

		assertEquals(-1.0, auto.armExtendSpeed(), EPSILON);
	}

	@Test
	public void testNoDriveTrainMotion() {
		AutoState auto = new ArmExtendAutonomous(1, 1.0, ExtendDirection.Forwards);
		MotorOutput motorOutput = auto.driveTrainSpeed();
		assertEquals(0.0, motorOutput.left, EPSILON);
		assertEquals(0.0, motorOutput.right, EPSILON);
	}
}
