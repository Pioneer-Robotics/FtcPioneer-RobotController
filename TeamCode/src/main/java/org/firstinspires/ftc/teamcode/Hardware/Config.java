package org.firstinspires.ftc.teamcode.Hardware;

//This class has all of the names and data for all of the things in one place for easy of access / sanity!
public class Config {

    //The IMU's names
    public static final String imu_0 = "imu";



    //Configuration names for all of our DriveTrain motors
    public static final String motorLT = "Left Top";
    public static final String motorLB = "Left Bottom";
    public static final String motorRT = "Right Top";
    public static final String motorRB = "Right Bottom";

    //Info on the specs of the odometers
    public static final int odoTicksPerRotation = 8192; //encoder ticks per full rotation
    public static final double odoDiameterIn = 2; //diameter of the odometry wheels in inches
    public static final double odoDiameterCM = odoDiameterIn * 2.54; //diameter of the wheel in cm
    public static final double odoCircumferenceCM = odoDiameterCM * Math.PI;
    public static final double odoTicksToCm = odoCircumferenceCM / odoTicksPerRotation;
    public static final double distanceBetweenLeftAndRightOdometersCm = 32.6; //TODO measure this
    public static final double distanceFromLeftOdoToMiddleOdoCm = 18.9; //TODO measure this

    //The max speed our wheel motors will ever rotate (in ticks per second),
        // 3 rotations per second. Used in calibration.
    public static final int wheel_maxTicksPerSecond = 4320; //TODO see if this is still needed + correct

}
