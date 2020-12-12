package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Helpers.Vector2;
import org.firstinspires.ftc.teamcode.Helpers.bMath;

public class Robot{
    static final int ODOMETER_POLL_PERIOD_MS = 250;

    //this class is meant to be a singleton
    //static robot so that it is the same everywhere (redundant)
    static Robot robot;
    public Telemetry telemetry;
    //all the other stuff in the Hardware package should be instantiated here
    //don't put modifier on them like "public" or "private". the default is "package" and is perfect
    static DriveTrain chassis;
    static Launcher launcher;
    static PositionTracker2 odometer;
    HardwareMap hwMap;
    MotorData motorData;
    ElapsedTime odometerPollInterval;


    //private constructor because we don't want anybody instantiating Robot more than once
    public Robot(HardwareMap hwMap, Telemetry telemetry, double startX, double startY){
        this.telemetry = telemetry;
        this.hwMap = hwMap;
        motorData = new MotorData();

        odometerPollInterval = new ElapsedTime();

        //the robot object does not exist until this method completes, so trying to create
            //new "DriveTrain" objects and similer such will not work
    }
    public static void init(HardwareMap hwMap, Telemetry telemetry, double startX, double startY){
        robot = new Robot(hwMap, telemetry, startX, startY);
        chassis = new DriveTrain();
        launcher = new Launcher();
        odometer = new PositionTracker2(startX, startY);
    }
    //this is the method all other classes will use to access the robot
    public static Robot get(){
        return robot;
    }

    public void update(){
        if (odometerPollInterval.milliseconds()>=ODOMETER_POLL_PERIOD_MS) {
            odometer.trackPosition();
            odometerPollInterval.reset();
        }
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
    public double getRotationRad(){
        return odometer.getRotationRadians();
    }
    public double getRotationDeg(){
        return bMath.toDeg(odometer.getRotationRadians());
    }
    public double getLeftOdo(){
        return odometer.getLeft();
    }
    public double getRightOdo(){
        return odometer.getRight();
    }
    public double getMidOdo(){
        return odometer.getMiddle();
    }
}
