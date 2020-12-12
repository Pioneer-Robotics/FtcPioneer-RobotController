package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

class MotorPair {
    public DcMotor motor1;
    public DcMotor motor2;

    public MotorPair(DcMotor motorA, DcMotor motorB){
         motor1 = motorA;
         motor2 = motorB;
    }
    public void setDriveMode(DcMotor.RunMode mode) {
         motor1.setMode(mode);
         motor2.setMode(mode);
    }
    public void setZeroPowerBehavior(DcMotor.ZeroPowerBehavior behavior){
        motor1.setZeroPowerBehavior(behavior);
        motor2.setZeroPowerBehavior(behavior);
    }
    public void setDirection(DcMotorSimple.Direction direction){
        motor1.setDirection(direction);
        motor2.setDirection(direction);
    }
    public void setPower(double power){
        motor1.setPower(power);
        motor2.setPower(power);
    }
}
