package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DcMotorEx;

class MotorPair {
    public DcMotorEx motor1;
    public DcMotorEx motor2;

    public MotorPair(DcMotorEx motorA, DcMotorEx motorB){
         motor1 = motorA;
         motor2 = motorB;
    }
    public MotorPair setDriveMode(DcMotorEx.RunMode mode) {
         motor1.setMode(mode);
         motor2.setMode(mode);
         return this;
    }
    public MotorPair setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior behavior){
        motor1.setZeroPowerBehavior(behavior);
        motor2.setZeroPowerBehavior(behavior);
        return this;
    }
    public MotorPair setDirection(DcMotorEx.Direction direction){
        motor1.setDirection(direction);
        motor2.setDirection(direction);
        return this;
    }
    public MotorPair setPower(double power){
        motor1.setPower(power);
        motor2.setPower(power);
        return this;
    }
}
