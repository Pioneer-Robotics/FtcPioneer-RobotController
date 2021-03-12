package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.Helpers.DataHub;
import org.firstinspires.ftc.teamcode.Helpers.Vector2;


 class DriveTrain {
    MotorPair leftMotors;
    MotorPair rightMotors;

    DriveTrain(){
        DcMotorEx leftTop = DataHub.hardwareMap.get(DcMotorEx.class,Config.motorLT);
        DcMotorEx leftBottom = DataHub.hardwareMap.get(DcMotorEx.class,Config.motorLB);
        leftMotors = new MotorPair(leftTop, leftBottom);

        DcMotorEx rightTop = DataHub.hardwareMap.get(DcMotorEx.class,Config.motorRT);
        DcMotorEx rightBottom = DataHub.hardwareMap.get(DcMotorEx.class,Config.motorRB);
        rightMotors = new MotorPair(rightTop, rightBottom);

        leftMotors.setDirection(DcMotorEx.Direction.FORWARD);
        rightMotors.setDirection(DcMotorEx.Direction.REVERSE);
        setDriveMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        setDriveMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
    }
    void setMotorPowers(double leftPower, double rightPower){
        leftMotors.setPower(leftPower);
        rightMotors.setPower(rightPower);
    }
    void setDriveMode(DcMotorEx.RunMode mode) {
        leftMotors.setDriveMode(mode);
        rightMotors.setDriveMode(mode);
    }
    void setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior behavior){
        leftMotors.setZeroPowerBehavior(behavior);
        rightMotors.setZeroPowerBehavior(behavior);
    }

}
