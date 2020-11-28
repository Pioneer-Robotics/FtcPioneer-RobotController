package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Helpers.Vector2;

public class Robot{
    //this class is meant to be a singleton
    //static robot so that it is the same everywhere (redundant)
    static Robot robot;
    public Telemetry telemetry;
    //all the other stuff in the Hardware package should be instantiated here
    //don't put modifier on them like "public" or "private". the default is "package" and is perfect
    DriveTrain chassis;
    Launcher launcher;
    PositionTracker odometer;
    HardwareMap hwMap;
    MotorData motorData;


    //private constructor because we don't want anybody instantiating Robot more than once
    private Robot(HardwareMap hwMap, Telemetry telemetry, double startX, double startY){
        this.hwMap = hwMap;
        chassis = new DriveTrain();
        launcher = new Launcher();
        odometer = new PositionTracker(startX, startY);
        motorData = new MotorData();
        this.telemetry = telemetry;
    }
    public static void init(HardwareMap hwMap, Telemetry telemetry, double startX, double startY){
        robot = new Robot(hwMap, telemetry, startX, startY);
    }
    //this is the method all other classes will use to access the robot
    public static Robot get(){
        return robot;
    }

    public void update(){
        odometer.trackPosition();
        motorData.handleFullStop(); //this needs to go before the setMotorPowers stuff
        chassis.setMotorPowers(motorData.leftPower,motorData.rightPower);
    }
    public void stopAllMotors(){
        motorData.fullStop = true;
    }
    public void setDrivePowers(double leftPower, double rightPower){
        motorData.leftPower = leftPower;
        motorData.rightPower = rightPower;
    }
    public Vector2 getLocation(){
        return odometer.getLocation();
    }
    public double getRotation(){
        return odometer.getRotation();
    }
}
