package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DcMotor;

public class BasicChassis {
    MotorPair leftMotors;
    MotorPair rightMotors;

    BasicChassis(){
        DcMotor leftTop = BasicRobot.get().hwMap.get(DcMotor.class,Config.motorLT);
        DcMotor leftBottom = BasicRobot.get().hwMap.get(DcMotor.class,Config.motorLB);
        leftMotors = new MotorPair(leftTop, leftBottom);

        DcMotor rightTop = BasicRobot.get().hwMap.get(DcMotor.class,Config.motorRT);
        DcMotor rightBottom = BasicRobot.get().hwMap.get(DcMotor.class,Config.motorRB);
        rightMotors = new MotorPair(rightTop, rightBottom);

        leftMotors.setDirection(DcMotor.Direction.FORWARD);
        rightMotors.setDirection(DcMotor.Direction.REVERSE);
        setDriveMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        setDriveMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    void setMotorPowers(double leftPower, double rightPower){
        leftMotors.setPower(leftPower);
        rightMotors.setPower(rightPower);
    }
    void setDriveMode(DcMotor.RunMode mode) {
        leftMotors.setDriveMode(mode);
        rightMotors.setDriveMode(mode);
    }
    void setZeroPowerBehavior(DcMotor.ZeroPowerBehavior behavior){
        leftMotors.setZeroPowerBehavior(behavior);
        rightMotors.setZeroPowerBehavior(behavior);
    }
}
