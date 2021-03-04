package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.hardware.bosch.BNO055IMU;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.Helpers.ComplexNum;
import org.firstinspires.ftc.teamcode.Helpers.DataHub;
import org.firstinspires.ftc.teamcode.Helpers.Vector2;
import org.firstinspires.ftc.teamcode.Helpers.bMath;

public class Robot{
    static RingMeasurer ringMeasurer;

    static BNO055IMU imu;
    //this class is meant to be a singleton
    //static robot so that it is the same everywhere (redundant)
    static Robot robot;
    public Telemetry telemetry;
    //all the other stuff in the Hardware package should be instantiated here
    //don't put modifier on them like "public" or "private". the default is "package" and is perfect
    static DriveTrain chassis;
    static Launcher launcher;
    static Collector collector;
    static PositionTracker mainOdometer;
    static WobbleArm wobbleArm;
    HardwareMap hardwareMap;
    MotorData motorData;
    public static AutoPilot autoPilot;


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
        ringMeasurer = new RingMeasurer();
        chassis = new DriveTrain();
        mainOdometer = new PositionTracker(startX, startY, 0);
        autoPilot = new AutoPilot();
        launcher = new Launcher(robot.telemetry);
        collector = new Collector();
        imu = Robot.get().hardwareMap.get(BNO055IMU.class, "imu");
        wobbleArm = new WobbleArm();
        BNO055IMU.Parameters params = new BNO055IMU.Parameters();
        imu.initialize(params);
    }
    public void updateSettings (){
        launcher.updatePIDF();
    }
    /**
     * Gets the singleton {@code Robot} object. Use this anytime you need to interact with hardware
     * from an OpMode
     * @return the singleton {@code Robot} object
     */
    public static Robot get(){
        return robot;
    }
    public void update(boolean useOdometers){
        update(useOdometers, false);
    }

    public void update(boolean useOdometers, boolean dumbLauncher){
        autoPilot.update(); //needs to go before setMotorPowers stuff
        //when autoPilot is on, it will ignore user input
        motorData.handleFullStop(); //this needs to go immediately before the setMotorPowers stuff
        chassis.setMotorPowers(motorData.leftPower,motorData.rightPower);
        ringMeasurer.update();
        if (useOdometers) {updateOdometers();}
        launcher.updateLauncher(!dumbLauncher);
    }

    public double getLaserHigh(){
        return ringMeasurer.highCM;
    }
    public double getLaserLow(){
        return ringMeasurer.lowCM;
    }


    public double getHeading(AngleUnit angleUnit) {
        double angle;
        Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, angleUnit);
        angle = 360-angles.firstAngle;
        if(angle > 360)
            angle -= 360;
        return angle;
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
    public void brake(){
        chassis.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        setDrivePowers(0,0);
    }
    public void dontBrake(){
        chassis.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }
    public void setLauncherPower(double power){ motorData.launcherPower = power; }
    public Launcher setLaunchMode(Launcher.LaunchMode launchMode){return setLaunchMode(launchMode);}
    public Launcher updateLauncher(){return updateLauncher();}
    public Vector2 getLocation(){
        return mainOdometer.getLocation();
    }
    public ComplexNum getLocationComplex(){
        return mainOdometer.getLocationComplex();
    }
    void updateOdometers(){
        mainOdometer.update();
    }
    public double getX(){
        return mainOdometer.getLocationComplex().real;
    }
    public double getY(){
        return mainOdometer.getLocationComplex().imag;
    }

    /**
     * gets the current rotation of the robot as calculated by the odometers
     * @return the robot's current rotation in radians
     */
    public double getRotationRad(){
        double rotationRadians = mainOdometer.getRotationRadiansSafe();
        rotationRadians = bMath.regularizeAngleRad(rotationRadians);
        return rotationRadians;
    }
    public double getRotationDegrees(){
        double rotationDegrees = Math.toDegrees(mainOdometer.getRotationRadiansSafe());
        rotationDegrees = bMath.regularizeAngleDeg(rotationDegrees);
        return rotationDegrees;
    }
    public double getRotationDegreesNonRegularized(){
        double rotationDegrees = Math.toDegrees(mainOdometer.getRotationRadiansSafe());
        return rotationDegrees;
    }
    public double getLeftOdo(){
        return mainOdometer.getLeftSafe();
    }
    public double getRightOdo(){
        return mainOdometer.getRightSafe();
    }
    public double getMidOdo(){
        return mainOdometer.getMiddleSafe();
    }

    /**
     * used to switch autopilot on and make it run straight
     * WARNING, WILL ROBINSON!!! this will run forever if you let it, need to always check the output
     * for when it becomes true and then stop it.
     * @param distance the distance you want the robot to drive forward
     * @return true if it has just reached the target distance, false otherwise
     */
    public boolean driveStraight(double distance){
        autoPilot.autoPilotNeeded = true;
        autoPilot.driveStraightNeeded = true;
        autoPilot.targetDistance = distance;
        autoPilot.forwardSpeed = 0.3;
        autoPilot.threshold = 10;
        return autoPilot.targetReached;
    }
    public boolean driveStraight(double distanceCM, double speed){
        autoPilot.autoPilotNeeded = true;
        autoPilot.driveStraightNeeded = true;
        autoPilot.targetDistance = distanceCM;
        autoPilot.forwardSpeed = speed;
        return autoPilot.targetReached;
    }
    public boolean driveStraight(double distance, double speed, double tolerance){
        autoPilot.autoPilotNeeded = true;
        autoPilot.driveStraightNeeded = true;
        autoPilot.targetDistance = distance;
        autoPilot.forwardSpeed = speed;
        autoPilot.threshold = tolerance;
        return autoPilot.targetReached;
    }

    /**
     * use to make sure that the robot doesn't keep going when you want it to stop
     */
    public void deactivateDriveStraight(){
        autoPilot.driveStraightNeeded = false;
    }

    /**
     * averages the distance travelled by the left and right odometry wheels
     * @return the average distance, in cm, that the right and left odos have measured
     */
    public double avgRightAndLeftOdos(){
        double ans = getLeftOdo() + getRightOdo();
        ans /= 2;
        return ans;
    }

    public void doOdometerTelemetry(){
        double rotation = getRotationDegrees();
        rotation = bMath.regularizeAngleDeg(rotation);
        telemetry.addData("rotation degress", rotation);
    }
    public void setCollectorSpeed(float collectorSpeed) {collector.setCollectorSpeed(collectorSpeed);}
    public void startCollecting(){collector.start();}
    public void stopCollecting(){collector.stop();}
    public void retractCollector(){collector.retract();}
    public void isCollecting(){collector.isCollecting();}

    public void requestLaunch(){launcher.requestLaunch();}
    public void launchOverride(boolean launchOverride){launcher.setLaunchOverride(launchOverride);}
    public void setContinousFire(boolean setContinousFire){launcher.setContinuousFire(setContinousFire);}
    public void fire(){launcher.fire();}
    public void spool(){launcher.requestSpool();}
    public void emergencyStop(){launcher.emergencyStop();}
    public boolean justLaunched(){return launcher.justLaunched();}
    public Launcher.LaunchMode getLaunchMode() {return launcher.launchMode;}
    public double getLauncherVelocity() {return launcher.getLaunchVelocity();}
    public void setLauncherTargetVelocity(double targetVelocity) {launcher.setTargetVelocity(targetVelocity);}

    public void setWobbleMotorPower (double power){wobbleArm.setWobbleMotorPower(power);}
    public void setWobbleServoPosition(boolean open){wobbleArm.setWobbleServoPosition(open);}

    public void setWobble90(){
        wobbleArm.WobbleGoTo90();
    }
    public void wobbleChillPos(){
        wobbleArm.WobbleChillPos();
    }

    public boolean justShot() {return launcher.justLaunched();}

}
