package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.sql.Time;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

//ALL METHODS IN THIS CLASS SHOULD BE EITHER private OR static OR BOTH!!!!!
public class Robot  extends Thread{
    //this class is meant to be a singleton
    //static robot so that it is the same everywhere (redundant)
    static Robot robot;

    //all the other stuff in the Hardware package should be instantiated here as private variables
    private DriveTrain chassis;
    private Launcher launcher;
    public PositionTracker odometer;
    public HardwareMap hwMap;


    //private constructor because we don't want anybody instantiating Robot more than once
    private Robot(HardwareMap hwMap){
        this.hwMap = hwMap;
        chassis = new DriveTrain();
        launcher = new Launcher();
    }
    public static void init(HardwareMap hwMap){
        robot = new Robot(hwMap);
    }
    //this is the method all other classes will use to access the robot
    public static Robot get(){
        return robot;
    }

    public void stopRobot(){
        stopAllMotors();
        chassis.setDriveMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    public void stopAllMotors(){ //TODO keep this up to date
        setDrivePowers(0,0);
    }
    public void setDrivePowers(double leftPower, double rightPower){
        chassis.setMotorPowers(leftPower,rightPower);
    }
    public void setDrivePowers(double forwardPower){
        chassis.setMotorPowers(forwardPower,forwardPower);
    }
}
