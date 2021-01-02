package org.firstinspires.ftc.teamcode.Hardware;

//This class has all of the names and data for all of the things in one place for easy of access / sanity!
public class Config {

    //Configuration names for all of our DriveTrain motors
    public static final String motorLT = "LT";
    public static final String motorLB = "LB";
    public static final String motorRT = "RT";
    public static final String motorRB = "RB";

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
