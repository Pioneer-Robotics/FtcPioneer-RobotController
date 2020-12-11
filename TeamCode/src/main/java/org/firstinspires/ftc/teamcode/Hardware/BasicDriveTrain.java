package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DcMotor;

public class BasicDriveTrain {
    DcMotor motorRT;
    DcMotor motorRB;
    DcMotor motorLT;
    DcMotor motorLB;
    BasicDriveTrain(){
        motorLT = BasicRobot.get().hwMap.get(DcMotor.class, Config.motorLT);
        motorLB = BasicRobot.get().hwMap.get(DcMotor.class, Config.motorLB);
        motorRT = BasicRobot.get().hwMap.get(DcMotor.class, Config.motorRT);
        motorRB = BasicRobot.get().hwMap.get(DcMotor.class, Config.motorRB);
        motorLT.setDirection(DcMotor.Direction.FORWARD);
        motorLB.setDirection(DcMotor.Direction.FORWARD);
        motorRT.setDirection(DcMotor.Direction.REVERSE);
        motorRB.setDirection(DcMotor.Direction.REVERSE);
        motorLT.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorLB.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorRT.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorRB.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorLT.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorLB.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorRT.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorRB.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    void setDrivePowers(double left, double right){
        motorLT.setPower(left);
        motorLB.setPower(left);
        motorRT.setPower(right);
        motorRB.setPower(right);
    }
}
