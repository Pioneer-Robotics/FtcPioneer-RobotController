package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Helpers.*;

public class Robot{
    //this class is meant to be a singleton
    //static robot so that it is the same everywhere (redundant)
    static Robot robot;
    public Telemetry telemetry;
    //all the other stuff in the Hardware package should be instantiated here
    //don't put modifier on them like "public" or "private". the default is "package" and is perfect
    static DriveTrain chassis;
    static Launcher launcher;
    static int testCases = 5;
    static double maxWaitTimeMS = 500;
    static PosTrackerType1population type1odos; //same idea, but slightly different implementations
    static PosTrackerType2population type2odos; //same idea, but slightly different implementations
    static PositionTracker mainOdometer;
    HardwareMap hwMap;
    MotorData motorData;


    //private constructor because we don't want anybody instantiating Robot more than once
    public Robot(HardwareMap hwMap, Telemetry telemetry, double startX, double startY){
        this.telemetry = telemetry;
        this.hwMap = hwMap;
        motorData = new MotorData();
        //the robot object does not exist until this method completes, so trying to create
            //new "DriveTrain" objects and similar such will not work
    }
    public static void init(HardwareMap hwMap, Telemetry telemetry, double startX, double startY){
        robot = new Robot(hwMap, telemetry, startX, startY);
        chassis = new DriveTrain();
        mainOdometer = new PositionTracker(0, 0, 0);
        type1odos = new PosTrackerType1population(testCases, maxWaitTimeMS);
        type2odos = new PosTrackerType2population(testCases, maxWaitTimeMS);
        //basically arrays of PositionTracker objects
        launcher = new Launcher();
    }
    //this is the method all other classes will use to access the robot
    public static Robot get(){
        return robot;
    }
    public void update(){
        motorData.handleFullStop(); //this needs to go before the setMotorPowers stuff
        chassis.setMotorPowers(motorData.leftPower,motorData.rightPower);
        updateOdometers();
    }
    public void stopAllMotors(){
        motorData.fullStop = true;
    }
    public void setDrivePowers(double leftPower, double rightPower){
        motorData.leftPower = leftPower;
        motorData.rightPower = rightPower;
    }
    public Vector2 getLocation(){
        return mainOdometer.getLocation();
    }
    void updateOdometers(){
        mainOdometer.update();
        type1odos.update();
        type2odos.update();
    }
    public double getRotationRad(){
        return mainOdometer.getRotationRadians();
    }
    public double getRotationDeg(){
        return bMath.toDeg(mainOdometer.getRotationRadians());
    }
    public double getLeftOdo(){
        return mainOdometer.getLeft();
    }
    public double getRightOdo(){
        return mainOdometer.getRight();
    }
    public double getMidOdo(){
        return mainOdometer.getMiddle();
    }
    public void doOdometerTelemetry(){
        telemetry.addData("rotation degress", getRotationDeg());
        type1odos.doTelemetryReadout();
        type2odos.doTelemetryReadout();
    }
}
