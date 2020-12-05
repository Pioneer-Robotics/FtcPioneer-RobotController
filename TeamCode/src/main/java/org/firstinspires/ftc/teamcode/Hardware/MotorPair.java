package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DcMotor;

 class MotorPair {
    public DcMotor motor1;
    public DcMotor motor2;

    public MotorPair(DcMotor motorA, DcMotor motorB, DcMotor.RunMode mode){
        motor1 = motorA;
        motor1.setMode(mode);
        motor2 = motorB;
        motor2.setMode(mode);
    }
    public void setPower(double power){
        motor1.setPower(power);
        motor2.setPower(power);
    }

    public void setDriveMode(DcMotor.RunMode mode) {
        motor1.setMode(mode);
        motor2.setMode(mode);
    }
}
