package org.usfirst.frc.team1076.test.controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.usfirst.frc.team1076.robot.controllers.TeleopController;
import org.usfirst.frc.team1076.test.mock.MockDriverInput;
import org.usfirst.frc.team1076.test.mock.MockOperatorInput;
import org.usfirst.frc.team1076.test.mock.MockRobot;

public class TeleopControllerTest {
	private static final double EPSILON = 1e-10;
	MockDriverInput driverInput = new MockDriverInput();
	MockOperatorInput operatorInput = new MockOperatorInput();
	TeleopController controller = new TeleopController(driverInput, operatorInput, driverInput, driverInput);
	MockRobot robot = new MockRobot();
	
	@Before
	public void reset() {
		driverInput.reset();
	}
	
	@Test
	public void testMotion() {
		for (int i = -100; i <= 100; i++) {
			for (int j = -100; j <= 100; j++) {
				double left = i / 100.0;
				double right = i / 100.0;
				driverInput.left = left;
				driverInput.right = right;
				controller.teleopPeriodic(robot);
				assertEquals("The left motor should match the left input",
						left, robot.left, EPSILON);
				assertEquals("The right motor should match the right input",
						right, robot.right, EPSILON);
			}
		}
	}
	
	@Test
	public void testUpArmMotion() {
	    for (int i = 0; i <= 100; i++) {
	        robot.armUpSpeed = 0.5;
	        double value = i / 100.0;
	        operatorInput.arm = value;
	        controller.teleopPeriodic(robot);
	        double expected =  value * robot.armUpSpeed;
	        assertEquals("The arm motion should be the arm input " +
	                     "reduced by a factor of " + robot.armUpSpeed,
	                     expected, robot.arm, EPSILON);
	    }
	}
	
	@Test
	public void testDownArmMotion() {
	    for (int i = -100; i <= 0; i++) {
	        robot.armDownSpeed = 0.5;
	        double value = i / 100.0;
	        operatorInput.arm = value;
	        controller.teleopPeriodic(robot);
	        double expected =  value * robot.armDownSpeed;

	        assertEquals("The arm motion should be the arm input "
	                + "reduced by a factor of " + robot.armDownSpeed,
	                expected, robot.arm, EPSILON);
	    }
	}

	@Test
	public void testDriverTurboArmMotion() {
	    for (int i = -100; i <= 0; i++) {
	        robot.driverTurboSpeed = 0.9;
	        double value = i / 100.0;
	        operatorInput.arm = value;
	        driverInput.turboArm = true;
	        controller.teleopPeriodic(robot);
	        double expected =  value * robot.driverTurboSpeed;

	        assertEquals("The arm motion should be the arm input "
	                + "multiplied by a factor of " + robot.driverTurboSpeed,
	                expected, robot.arm, EPSILON);
	    }
	}

    @Test
    public void testOperatorTurboArmMotion() {
        for (int i = -100; i <= 0; i++) {
            robot.operatorTurboSpeed = 0.75;
            double value = i / 100.0;
            operatorInput.arm = value;
            operatorInput.operatorTurbo = true;
            controller.teleopPeriodic(robot);
            double expected =  value * robot.operatorTurboSpeed;

            assertEquals("The arm motion should be the arm input "
                    + "multiplied by a factor of " + robot.operatorTurboSpeed,
                    expected, robot.arm, EPSILON);
        }
    }

	@Test
	public void testArmExtension() {
		for (int i = -100; i <= 100; i++) {
			double value = i / 100.0;
			operatorInput.extend = value;
			controller.teleopPeriodic(robot);
			assertEquals("The arm extension should match the extension input",
					value, robot.extend, EPSILON);
		}
	}
	
	@Test
	public void testIntakeMotion() {
		for (int i = -100; i <= 100; i++) {
			double value = i / 100.0;
			operatorInput.intake = value;
			controller.teleopPeriodic(robot);
			assertEquals("The arm motion should match the arm input",
					value, robot.intake, EPSILON);
		}
	}
	
	@Test
	public void testBrakes() {
		driverInput.brakes = true;
		controller.teleopPeriodic(robot);
		assertEquals(true, robot.brakes);
		
		driverInput.brakes = false;
		controller.teleopPeriodic(robot);
		assertEquals(false, robot.brakes);
	}
}
