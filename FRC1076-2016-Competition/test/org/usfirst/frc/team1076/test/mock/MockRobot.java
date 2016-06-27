package org.usfirst.frc.team1076.test.mock;

import org.usfirst.frc.team1076.robot.IRobot;
import org.usfirst.frc.team1076.robot.gamepad.IDriverInput.MotorOutput;
import org.usfirst.frc.team1076.robot.gamepad.IOperatorInput.IntakeRaiseState;
import org.usfirst.frc.team1076.udp.ISensorData;

public class MockRobot implements IRobot {
	public double left, right, arm, intake, extend;
	public double lidarSpeed;
	public double armDownSpeed, armUpSpeed, driverTurboSpeed, operatorTurboSpeed;
	public boolean brakes;
	public IntakeRaiseState intakeRaiseState = IntakeRaiseState.Neutral;
	public SolenoidValue gear = SolenoidValue.Off;
	public ISensorData sensorData;
	
	@Override
	public void setLeftSpeed(double speed) {
		left = speed;
	}

	@Override
	public void setRightSpeed(double speed) {
		right = speed;
	}

	@Override
	public void setArmSpeed(double speed) {
		arm = speed;
	}

	@Override
	public void setIntakeSpeed(double speed) {
		intake = speed;
	}

	@Override
	public void setGear(SolenoidValue value) {
		gear = value;
	}

	@Override
	public MotorOutput getMotorSpeed() {
		return new MotorOutput(left, right);
	}
	
	@Override
	public void setBrakes(boolean enabled) {
		brakes = enabled;
	}

	@Override
	public void setLidarSpeed(double speed) {
		lidarSpeed = speed;
	}
	
	@Override
	public ISensorData getSensorData() {
		return sensorData;
	}
	
	@Override
	public void setIntakeElevation(IntakeRaiseState state) {
		this.intakeRaiseState = state;
	}

	@Override
	public void setArmExtendSpeed(double speed) {
		this.extend = speed;
	}
	
    @Override
    public void setArmSpeed(double speed, boolean turbo) {
        setArmSpeed(speed, turbo, false); 
    }

    @Override
    public void setArmSpeed(double speed, boolean turbo, boolean operatorTurbo) {
        if (speed > 0) {
           this.arm  = speed * armUpSpeed;
        } else {
            if (turbo) {
                this.arm = speed * driverTurboSpeed;
            } else if (operatorTurbo) {
                this.arm = speed * operatorTurboSpeed;
            } else {
                this.arm = speed * armDownSpeed;
            }
        }
    }

    @Override
    public double getArmDownSpeed() {
        return armDownSpeed;
    }

    @Override
    public double getArmUpSpeed() {
        return armUpSpeed;
    }

    @Override
    public double getArmTurboSpeed() {
        return driverTurboSpeed;
    }

    @Override
    public double getArmOperatorTurboSpeed() {
        return operatorTurboSpeed;
    }

}
