package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class BasicRobot {
    static BasicRobot robot;
    public Telemetry telemetry;
    static HardwareMap hwMap;
    MotorData motorData;

    static BasicChassis chassis;
    static BasicOdometer mainOdometer;
    //static BasicOdometerArray odometers;


    //private constructor because we don't want anybody instantiating Robot more than once
    private BasicRobot(HardwareMap hwMap, Telemetry telemetry, double startX, double startY){
        this.telemetry = telemetry;
        this.hwMap = hwMap;
        motorData = new MotorData();
        //the robot object does not exist until this method completes, so trying to create
        //new "DriveTrain" objects and similer such will not work
    }
    public static void init(HardwareMap hwMap, Telemetry telemetry, double startX, double startY){
        robot = new BasicRobot(hwMap, telemetry, startX, startY);
        chassis = new BasicChassis();
        mainOdometer = new BasicOdometer(startX, startY, 100);
        //odometers = new BasicOdometerArray(20, 1000);

    }
    //this is the method all other classes will use to access the robot
    public static BasicRobot get(){
        return robot;
    }

    public void update(){
        //odometer.update();
        motorData.handleFullStop(); //this needs to go before the setMotorPowers stuff
        chassis.setMotorPowers(motorData.leftPower,motorData.rightPower);
        mainOdometer.update();
    }
    public void stopAllMotors(){
        motorData.fullStop = true;
    }
    public void setDrivePowers(double leftPower, double rightPower){
        motorData.leftPower = leftPower;
        motorData.rightPower = rightPower;
    }
    public void doOdometerTelemetry(){
        telemetry.addData("current rotation deg", getRotationDegrees());
        telemetry.addData("left odo", getLeftOdo());
        telemetry.addData("right odo", getRightOdo());
        telemetry.addData("mid odo", getMidOdo());
        telemetry.addLine();
        telemetry.addData("xPos", mainOdometer.getLocation().getX());
        telemetry.addData("yPos", mainOdometer.getLocation().getY());
        //odometers.doTelemetryReadout();
    }
    public double getRotationDegrees(){
        return mainOdometer.getRotationRadians() * 180.0 / Math.PI;
    }
    public double getRotationRadians(){
        return mainOdometer.getRotationRadians();
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


}