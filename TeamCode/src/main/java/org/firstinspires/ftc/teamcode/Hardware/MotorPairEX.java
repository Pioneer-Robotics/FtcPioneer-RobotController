package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

class MotorPairEX{
    public DcMotorEx motor1;
    public DcMotorEx motor2;

    /**
     * WARNING! This class requires that both motors have encoders in order for the setVelocity/getVelocity to work.
     */
    public MotorPairEX(DcMotorEx motorA, DcMotorEx motorB){
         motor1 = motorA;
         motor2 = motorB;
    }
    public MotorPairEX setDriveMode(DcMotorEx.RunMode mode) {
         motor1.setMode(mode);
         motor2.setMode(mode);
         return this;
    }
    public MotorPairEX setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior behavior){
        motor1.setZeroPowerBehavior(behavior);
        motor2.setZeroPowerBehavior(behavior);
        return this;
    }
    public MotorPairEX setDirection(DcMotorEx.Direction direction){
        motor1.setDirection(direction);
        motor2.setDirection(direction);
        return this;
    }
    public MotorPairEX setPower(double power){
        motor1.setPower(power);
        motor2.setPower(power);
        return this;
    }

    public MotorPairEX setVelocity(double velocity){
        motor1.setVelocity(velocity);
        motor2.setVelocity(velocity);
        return this;
    }

    public MotorPairEX enable(){
        motor1.setMotorEnable();
        motor2.setMotorEnable();
        return this;
    }

    public MotorPairEX disable(){
        motor1.setMotorDisable();
        motor2.setMotorDisable();
        return this;
    }

    public double getAverageVelocity(){
        return (motor1.getVelocity() + motor2.getVelocity()) / 2.0;
    }

    public int getCurrentPositionM1(){
        return motor1.getCurrentPosition();
    }

    public MotorPairEX setVelocityPIDfCoefficients (DcMotor.RunMode runMode, PIDFCoefficients pidfCoefficients){
        motor1.setPIDFCoefficients(runMode, pidfCoefficients);
        motor1.setPIDFCoefficients(runMode, pidfCoefficients);
        return this;
    }

}
