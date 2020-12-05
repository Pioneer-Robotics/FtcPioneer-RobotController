package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class BasicRobot {
    static BasicRobot robot;
    static DcMotor motorRT;
    static DcMotor motorRB;
    static DcMotor motorLT;
    static DcMotor motorLB;
    static MotorData motorData;
    Telemetry telemetry;
    private BasicRobot(HardwareMap hwMap, Telemetry telemetry){
        motorData = new MotorData();
        this.telemetry = telemetry;
        motorLT = hwMap.get(DcMotor.class, "LT");
        motorLB = hwMap.get(DcMotor.class, "LB");
        motorRT = hwMap.get(DcMotor.class, "RT");
        motorRB = hwMap.get(DcMotor.class, "RB");
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
    public static void init(HardwareMap hwMap, Telemetry telemetry){
        robot = new BasicRobot(hwMap, telemetry);
    }
    public static BasicRobot get(){
        return robot;
    }
    public void setMotorPowers(double left, double right){
        motorData.leftPower = left;
        motorData.rightPower = right;
    }
    public void update(){
        motorLT.setPower(motorData.leftPower);
        motorLB.setPower(motorData.leftPower);
        motorRT.setPower(motorData.rightPower);
        motorRB.setPower(motorData.rightPower);
        telemetry.addData("left power", motorData.rightPower);
        telemetry.addData("right power", motorData.leftPower);
    }
}
