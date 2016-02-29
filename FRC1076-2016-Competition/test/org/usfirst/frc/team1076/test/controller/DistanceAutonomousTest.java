package org.usfirst.frc.team1076.test.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.junit.Test;
import org.usfirst.frc.team1076.robot.gamepad.IInput.MotorOutput;
import org.usfirst.frc.team1076.robot.statemachine.AutoState;
import org.usfirst.frc.team1076.robot.statemachine.DistanceAutonomous;

public class DistanceAutonomousTest {

	private static final double EPSILON = 1e-12;

	public DistanceAutonomousTest() {
		// TODO Auto-generated constructor stub
	}
	
	
	@Test
	public void testNext() {
		AutoState auto = new DistanceAutonomous(1000, 10);
		assertSame(null, auto.next());
		auto.setNext(auto);
		assertSame(auto, auto.next());
	}
	
	@Test
	public void testShouldNotChange() {
		AutoState auto = new DistanceAutonomous(1000, 10);
		assertEquals(false, auto.shouldChange());
	}
	
	@Test
	public void testDistanceTraveled() {
		AutoState auto = new DistanceAutonomous(10, 1); // go 10 units in 1 second
		MotorOutput motorOutput = auto.driveTrainSpeed();
		
		
		assertEquals(false, auto.shouldChange());
		assertEquals(10, motorOutput.left, EPSILON);
		assertEquals(10, motorOutput.right, EPSILON);
		
		try {
		    Thread.sleep(1000);                 //Sleep for 1 seconds
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}

		motorOutput = auto.driveTrainSpeed();
		
		assertEquals(true, auto.shouldChange());
		assertEquals(0, motorOutput.left, EPSILON);
		assertEquals(0, motorOutput.right, EPSILON);
	}
	
	@Test
	public void testNoArmMotion() {
		AutoState auto = new DistanceAutonomous(100, 10);
		assertEquals(0, auto.armSpeed(), EPSILON);
	}
	
	@Test
	public void testNoIntakeMotion() {
		AutoState auto = new DistanceAutonomous(100, 10);
		assertEquals(0, auto.intakeSpeed(), EPSILON);
	}

}
