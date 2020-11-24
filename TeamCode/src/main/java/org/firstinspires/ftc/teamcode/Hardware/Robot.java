package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.sql.Time;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public class Robot  extends Thread{
    //this class is meant to be a singleton
    //static robot so that it is the same everywhere (redundant)
    static Robot robot;

    //all the other stuff in the Hardware package should be instantiated here
    //don't put modifier on them like "public" or "private". the default is "package" and is perfect
    DriveTrain chassis;
    Launcher launcher;
    PositionTracker odometer;
    HardwareMap hwMap;


    //private constructor because we don't want anybody instantiating Robot more than once
    private Robot(HardwareMap hwMap, double startX, double startY){
        this.hwMap = hwMap;
        chassis = new DriveTrain();
        launcher = new Launcher();
        odometer = new PositionTracker(startX, startY);
    }
    public static void init(HardwareMap hwMap, double startX, double startY){
        robot = new Robot(hwMap, startX, startY);
    }
    //this is the method all other classes will use to access the robot
    public static Robot get(){
        return robot;
    }

    public void update(){
        odometer.trackPosition();
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