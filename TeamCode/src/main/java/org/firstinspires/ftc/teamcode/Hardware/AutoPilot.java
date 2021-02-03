package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.Helpers.ComplexNum;
import org.firstinspires.ftc.teamcode.Helpers.DataHub;
import org.firstinspires.ftc.teamcode.Helpers.bMath;

public class AutoPilot {
    //these are used for when you just need to drive forward
    boolean driveStraightNeeded;
    double startingRightOdoDistance;
    double startingLeftOdoDistance;
    double startingRotation;
    double forwardSpeed;
    double sign;
    double threshold;
    double forwardDistance;
    DriveMode driveMode;

    AutoPilot(){
        driveStraightNeeded = false;
        startingRightOdoDistance = 0;
        startingLeftOdoDistance = 0;
        startingRotation = 0;
        forwardSpeed = 0.2;
        sign = 0;
        threshold = 10;
        driveMode = DriveMode.EXIT_AUTOPILOT;
        forwardDistance = 0;
    }
    enum DriveMode{
        CALCULATE,
        DRIVE_FORWARD,
        EXIT_AUTOPILOT
    }


    void deactivateAutoPilot(){
        driveStraightNeeded = false;
    }

    /**
     * will drive straight forward an amount set by the "forwardDistance" class variable
     * return true when it finishes
     */
    boolean driveStraight(){
        boolean ans = false;
        if(driveStraightNeeded){
            switch(driveMode){
                case CALCULATE: //this should only run once, not every cycle, and it shouldn't be possible to run again until the full run finishes
                    startingRotation = Robot.get().getRotationRad(); //remembers the rotation we want to keep
                    startingLeftOdoDistance = Robot.get().getLeftOdo(); //what does the left odo measure right now?
                    startingRightOdoDistance = Robot.get().getRightOdo(); //what does the right odo measure rn?
                    driveMode = DriveMode.DRIVE_FORWARD; //move on the next state
                    break;
                case DRIVE_FORWARD:
                    if((avgChangeInLeftAndRightOdo() - forwardDistance) > threshold){ //we've overshot (gone to far)
                        Robot.get().setDrivePowers(-forwardSpeed, -forwardSpeed);
                    }
                    else if ((avgChangeInLeftAndRightOdo() - forwardDistance) < -threshold){ //haven't gone far enough
                        Robot.get().setDrivePowers(forwardSpeed, forwardSpeed);
                    }
                    else{ //this means we're in the target range
                        driveMode = DriveMode.EXIT_AUTOPILOT;
                    }
                    break;
                case EXIT_AUTOPILOT:
                    driveStraightNeeded = false;
                    ans = true;
                    break;
            }
        }
        return ans;
    }

    double avgChangeInLeftAndRightOdo(){
        double ansLeft = startingLeftOdoDistance - Robot.get().getLeftOdo();
        double ansRight = startingRightOdoDistance - Robot.get().getRightOdo();
        return (ansLeft + ansRight) / 2.0;
    }
    /**
     * we could have called this "{@code doAutoPilot()}". It handles all the movement.
     * <b> Important: if this doesn't work, suspect {@code bMath.regularizeAngle()} and
     * {@code bMath.subtractAnglesRad()}</b>
     */
    void update(){ //TODO test everything
        if(driveStraightNeeded){
            driveStraight();
        }
    }

}
