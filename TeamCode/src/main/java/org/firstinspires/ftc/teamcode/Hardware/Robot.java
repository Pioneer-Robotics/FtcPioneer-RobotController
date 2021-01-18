package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.Helpers.ComplexNum;
import org.firstinspires.ftc.teamcode.Helpers.DataHub;
import org.firstinspires.ftc.teamcode.Helpers.Vector2;
import org.firstinspires.ftc.teamcode.Helpers.bMath;

public class Robot{
    static BNO055IMU imu;
    //this class is meant to be a singleton
    //static robot so that it is the same everywhere (redundant)
    static Robot robot;
    public Telemetry telemetry;
    //all the other stuff in the Hardware package should be instantiated here
    //don't put modifier on them like "public" or "private". the default is "package" and is perfect
    static DriveTrain chassis;
    static Launcher launcher;
    public static WobbleMotor wobblemotor;
    static Collector collector;
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
        //basically arrays of PositionTracker objects
        launcher = new Launcher();
        collector = new Collector();
        imu = Robot.get().hardwareMap.get(BNO055IMU.class, "imu");
        BNO055IMU.Parameters params = new BNO055IMU.Parameters();
        imu.initialize(params);
        wobblemotor = new WobbleMotor();
    }
    public double getHeading(AngleUnit angleUnit) {
        double angle;
        Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, angleUnit);
        angle = 360-angles.firstAngle;
        if(angle > 360)
            angle -= 360;
        return angle;
    }
    public double getWobblePower(){
        return wobblemotor.getWobblePower();
    }

    public void setWobblePower(double power) {
        wobblemotor.setWobblePower(power);
    }
    public void servoPosition(double position){
        wobblemotor.wobbleservo.setPosition(position);
    }
    public void WobbleMotorStartPos(){
        wobblemotor.wobblemotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        wobblemotor.wobblemotor.setTargetPosition(0);
    }
    public void WobbleMotorUp(){
        wobblemotor.wobblemotor.setTargetPosition(1200);
        setWobblePower(0.5);
        wobblemotor.wobblemotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public void WobbleMotorDown(){
        wobblemotor.wobblemotor.setTargetPosition(2000);
        setWobblePower(0.5);
        wobblemotor.wobblemotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
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
        motorData.handleFullStop(); //needs to go immediately before handleBreaking()
        chassis.setMotorPowers(motorData.leftPower,motorData.rightPower);
        launcher.setPower(motorData.launcherPower);
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
    public void setLauncherPower(double power){
        motorData.launcherPower = power;
    }
    public Vector2 getLocation(){
        return mainOdometer.getLocation();
    }
    public ComplexNum getLocationComplex(){
        return mainOdometer.getLocationComplex();
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
    public void setCollectorSpeed(float collectorSpeed) {collector.setCollectorSpeed(collectorSpeed);}
    public void startCollecting(){collector.startCollecting();}
    public void toggleCollector(){}
}
