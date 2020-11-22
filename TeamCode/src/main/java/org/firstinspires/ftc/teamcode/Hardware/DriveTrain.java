package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DcMotor;


public class DriveTrain {
    MotorPair leftMotors;
    MotorPair rightMotors;

    public DriveTrain(){
        DcMotor leftTop = Robot.get().hwMap.get(DcMotor.class,Config.motorLT);
        DcMotor leftBottom = Robot.get().hwMap.get(DcMotor.class,Config.motorLB);
        leftMotors = new MotorPair(leftTop, leftBottom, DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        DcMotor rightTop = Robot.get().hwMap.get(DcMotor.class,Config.motorRT);
        DcMotor rightBottom = Robot.get().hwMap.get(DcMotor.class,Config.motorRB);
        rightMotors = new MotorPair(rightTop, rightBottom,DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    public void setMotorPowers(double leftPower, double rightPower){
        leftMotors.setPower(leftPower);
        rightMotors.setPower(rightPower);
    }

    public void setDriveMode(DcMotor.RunMode mode) {
        leftMotors.setDriveMode(mode);
        rightMotors.setDriveMode(mode);
    }
}
