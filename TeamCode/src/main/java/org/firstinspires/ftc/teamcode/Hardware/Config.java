package org.firstinspires.ftc.teamcode.Hardware;

//This class has all of the names and data for all of the things in one place for easy of access / sanity!
public class Config {

    //Configuration names for all of our DriveTrain motors
    public static final String motorLT = "LT";
    public static final String motorLB = "LB";
    public static final String motorRT = "RT";
    public static final String motorRB = "RB";

    //LAUNCHER
    public static final String launcherMotor1 = "launcher1";
    public static final String launcherMotor2 = "launcher2";

    public static final String wobblemotor = "wobblemotor";
    public static final String wobbleservo = "wobbleservo";

    public static final String collectorMotor = "collector";
    public static final String collectorServoLeft = "collectorServoLeft";
    public static final String collectorServoRight = "collectorServoRight";
    static final float COLLECTOR_SERVO_UP_POS = -0.8f, COLLECTOR_SERVO_DOWN_POS = 0.45f, COLLECTOR_SERVO_MID_POS = 0.3f;
    public static final float COLLECTOR_MAX_SPEED_FRACTION = 0.5f;


    public static final String launcherServo = "launcherS";
    public static final float launcherServoOut = 0.55f, launcherServoIn = 0.425f;
    public static double maxLauncherSpeed =  2500;
    public static double defaultTargetLauncherSpeed = 2000;

    public static final double launchVelocityThreshold = 10; //allowable difference in angular velocity before a launch is initiated
    public static final double launcherServoTime = 200; //launcher flick time in ms

    //ODOMETERS
    //Info on the specs of the odometers
    public static final int odoTicksPerRotation = 8192; //encoder ticks per full rotation
    public static final double odoDiameterIn = 2; //diameter of the odometry wheels in inches
    public static final double odoDiameterCM = odoDiameterIn * 2.54; //diameter of the wheel in cm
    public static final double odoCircumferenceCM = odoDiameterCM * Math.PI;
    public static final double odoTicksToCm = odoCircumferenceCM / odoTicksPerRotation;
    public static final double distanceBetweenLeftAndRightOdometersCm = 19.7;
    public static final double distanceFromLeftOdoToMiddleOdoCm = 7.5;

    //The max speed our wheel motors will ever rotate (in ticks per second),
        // 3 rotations per second. Used in calibration.
    @Deprecated
    public static final int wheel_maxTicksPerSecond = 4320; //TODO see if this is still needed + correct

}
