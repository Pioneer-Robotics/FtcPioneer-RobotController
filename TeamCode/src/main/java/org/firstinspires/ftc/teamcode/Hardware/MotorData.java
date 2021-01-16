package org.firstinspires.ftc.teamcode.Hardware;

class MotorData {
    //the point of this class is to hold "desired motor values" so that the robot
        //just updates at the end of every cycle and doesn't try to set the same motor to
        //multiple values.
    double leftPower;
    double rightPower;
    double launcherPower;
    double wobblePower;
    boolean fullStop = false;
    void handleFullStop(){
        if(fullStop){ //TODO keep this up to data
            leftPower = 0;
            rightPower = 0;
            fullStop = false;
        }
    }
    MotorData(){
        leftPower = 0;
        rightPower = 0;
        launcherPower = 0;
        wobblePower = 0;
        fullStop = false;
    }
}
