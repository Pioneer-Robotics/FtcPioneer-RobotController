package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Helpers.Vector2;

public class BasicRobot {
    static BasicRobot robot;
    static MotorData motorData;
    static BasicDriveTrain chassis;
    static BasicPosTracker odometer;
    static Launcher launcher; //note: this will break things once "Launcher" class does things
    Telemetry telemetry;
    HardwareMap hwMap;
    private BasicRobot(HardwareMap hwMap, Telemetry telemetry){
        this.hwMap = hwMap;
        motorData = new MotorData();
        this.telemetry = telemetry;
    }
    public static void init(HardwareMap hwMap, Telemetry telemetry, double startX, double startY){
        robot = new BasicRobot(hwMap, telemetry);
        chassis = new BasicDriveTrain();
        odometer = new BasicPosTracker(startX, startY);
        launcher = new Launcher();
    }
    public static BasicRobot get(){
        return robot;
    }
    public void setMotorPowers(double left, double right){
        motorData.leftPower = left;
        motorData.rightPower = right;
    }
    public void update(){
        chassis.setDrivePowers(motorData.leftPower, motorData.rightPower);
        telemetry.addData("left power", motorData.rightPower);
        telemetry.addData("right power", motorData.leftPower);
        odometer.trackPosition();
    }
    public Vector2 getLocation(){
        return odometer.getLocation();
    }
    public double getRotation(){
        return odometer.getRotation();
    }
}
