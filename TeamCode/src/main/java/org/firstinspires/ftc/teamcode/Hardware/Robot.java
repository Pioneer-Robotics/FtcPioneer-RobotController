package org.firstinspires.ftc.teamcode.Hardware;

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

    //private constructor because we don't want anybody instantiating Robot more than once
    private Robot(){
        chassis = new DriveTrain();
        launcher = new Launcher();
    }

    //this is the method all other classes will use to access the robot
    public static Robot get(){
        if(robot == null){
            robot = new Robot();
        }
        return robot;
    }

    //TODO make all these methods actually do stuff
    public static void driveForward(double distanceCM){

    }
}
