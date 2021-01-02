package org.firstinspires.ftc.teamcode.Hardware;

import android.renderscript.Int3;
import com.qualcomm.robotcore.util.Range;

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
    static int testCases = 10;
    static double maxWaitTimeMS = 500;
    static PosTrackerType1population type1odos; //same idea, but slightly different implementations
    static PosTrackerType2population type2odos; //same idea, but slightly different implementations
    static PositionTracker mainOdometer;
    HardwareMap hardwareMap;
    MotorData motorData;
    static AutoPilot autoPilot;


    //private constructor because we don't want anybody instantiating Robot more than once
    private Robot(){
        this.telemetry = DataHub.telemetry;
        this.hardwareMap = DataHub.hardwareMap;
        motorData = new MotorData();
        //the robot object does not exist until this method completes, so trying to create
            //new "DriveTrain" objects and similar such will not work
    }
    public static void init(double startX, double startY){
        robot = new Robot();
        chassis = new DriveTrain();
        mainOdometer = new PositionTracker(startX, startY, 0);
        autoPilot = new AutoPilot();
        //basically arrays of PositionTracker objects
        type1odos = new PosTrackerType1population(testCases, maxWaitTimeMS);
        type2odos = new PosTrackerType2population(testCases, maxWaitTimeMS);
        launcher = new Launcher();
    }

    /**
     * Gets the singleton {@code Robot} object. Use this anytime you need to interact with hardware
     * from an OpMode
     * @return the singleton {@code Robot} object
     */
    public static Robot get(){
        return robot;
    }
    public void update(){
        autoPilot.update(); //needs to go before setMotorPowers stuff
                            //when autoPilot is on, it will ignore user input
        motorData.handleFullStop(); //this needs to go immediately before the setMotorPowers stuff
        chassis.setMotorPowers(motorData.leftPower,motorData.rightPower);
        updateOdometers();
    }
    public void stopAllMotors(){
        motorData.fullStop = true;
    }
    public void allowMovement(){
        motorData.fullStop = false;
    }
    public void setDrivePowers(double leftPower, double rightPower){
        motorData.leftPower = leftPower;
        motorData.rightPower = rightPower;
    }

    /**
     * tell the robot if it should drive itself or not. Note:
     * <li> while in autoPilot mode, robot will ignore user input in terms of
     * directly setting motor values
     * @param bool true if you want autoPilot, false if you don't
     */
    public void useAutoPilot(boolean bool){
        if(bool){
            autoPilot.useAutoPilot();
        }
        if(!bool){
            autoPilot.deactivateAutoPilot();
        }
    }

    private void turnToAngle(double targetAngle){
        double headingOffset = targetAngle - mainOdometer.getRotationRadians();
        if(-0.1 > headingOffset || headingOffset > 0.1){
            headingOffset = Range.clip(headingOffset, -1.0, 1.0);
            double turn = headingOffset * 0.25;
            setDrivePowers(-turn, turn);
        }
    }
    private void turn(double speed){
        double turn = speed * 0.5;
        setDrivePowers(-turn, turn);
    }


    /**
     * sets the target location the robot will drive to on {@code autoPilot}
     * @param x the x coordinate you would like to go to
     * @param y the y coordinate you would like to go to
     */
    public ComplexNum setTargetLocation(double x, double y){
        autoPilot.targetLocation.equals(x,y);
        return autoPilot.targetLocation;
    }

    /**
     * sets the target rotation the robot will turn to
     * @param angleDegrees the angle, in degrees, that you want to robot to turn to
     */
    public void setTargetRotation(double angleDegrees){
        autoPilot.targetRotationDegrees = angleDegrees;
    }

    /**
     * sets the target location the robot will drive to on {@code autoPilot} as exactly forward of
     * where it is now
     * @param cmAhead the amount of cm you want it to drive forward
     */
    public void setTargetLocation(double cmAhead){
        double heading = getRotationRad();
        ComplexNum deltaTargetLocation = ComplexNum.newComplexNumPolar(cmAhead, heading);
        autoPilot.targetLocation.plusEquals(deltaTargetLocation);
    }
    public Vector2 getLocation(){
        return mainOdometer.getLocation();
    }
    public ComplexNum getLocationComplex(){
        return mainOdometer.getLocationComplex();
    }

    /**
     * gets the raw encoder values from the odometry wheels as a single "Int3" object
     * @return and Int3 where:  x = left, y = right, z = middle
     */
    public Int3 getOdometerTicks(){
        return mainOdometer.getOdoTicks();
    }
    void updateOdometers(){
        mainOdometer.update();
        type1odos.update();
        type2odos.update();
    }
    public double getX(){
        return mainOdometer.getLocationComplex().real;
    }
    public double getY(){
        return mainOdometer.getLocationComplex().imag;
    }
    public double getRotationRad(){
        double rotationRadians = mainOdometer.getRotationRadians();
        rotationRadians = bMath.regularizeAngleRad(rotationRadians);
        return rotationRadians;
    }
    public double getRotationDegrees(){
        double rotationDegrees = Math.toDegrees(mainOdometer.getRotationRadians());
        rotationDegrees = bMath.regularizeAngleDeg(rotationDegrees);
        return rotationDegrees;
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
        double rotation = getRotationDegrees();
        rotation = bMath.regularizeAngleDeg(rotation);
        telemetry.addData("rotation degress", rotation);
        type1odos.doTelemetryReadout();
        type2odos.doTelemetryReadout();
    }
}
