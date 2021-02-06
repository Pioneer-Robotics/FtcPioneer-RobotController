package org.firstinspires.ftc.teamcode.Hardware;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Helpers.DataHub;

public class AutoPilot {
    //these are used for when you just need to drive forward
    boolean driveStraightNeeded;
    boolean targetReached; //used to know if the driveStright() method has reached it's goal
    double startingRightOdoDistance;
    double startingLeftOdoDistance;
    double startingRotation; //not used rn
    double forwardSpeed; //how fast should it move by default?
    double threshold; //how close to target is close enough?
    public double forwardDistance; //how far does it need to move (if problems suspect this)
    DriveMode driveMode; //basically what state are we in?
    Telemetry telemetry;

    AutoPilot(){
        driveStraightNeeded = false;
        targetReached = false;
        startingRightOdoDistance = 0;
        startingLeftOdoDistance = 0;
        startingRotation = 0;
        forwardSpeed = 0.3;
        threshold = 10;
        forwardDistance = 0;
        driveMode = DriveMode.CALCULATE;


        telemetry = DataHub.telemetry;
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
    void driveStraight(){
        boolean ans = false;
        switch(driveMode){
            case CALCULATE: //this should only run once, not every cycle, and it shouldn't be possible to run again until the full run finishes
                startingRotation = Robot.get().getRotationRad(); //remembers the rotation we want to keep
                startingLeftOdoDistance = Robot.get().getLeftOdo(); //what does the left odo measure right now?
                startingRightOdoDistance = Robot.get().getRightOdo(); //what does the right odo measure rn?
                driveMode = DriveMode.DRIVE_FORWARD; //move on the next state

                telemetry.addLine("calculating");
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
                telemetry.addLine("driving forward");
                telemetry.addData("distance travelled", avgChangeInLeftAndRightOdo());
                break;
            case EXIT_AUTOPILOT:
                driveStraightNeeded = false;
                ans = true;
                driveMode = DriveMode.CALCULATE;
                break;
        }
        telemetry.addData("distance travelled", avgChangeInLeftAndRightOdo());
        targetReached = ans;
    }

     double avgChangeInLeftAndRightOdo(){
        double ansLeft = Robot.get().getLeftOdo() - startingLeftOdoDistance;
        double ansRight = Robot.get().getRightOdo() - startingRightOdoDistance;
        return (ansLeft + ansRight) / 2.0;
    }
    /**
     * we could have called this "{@code doAutoPilot()}". It handles all the movement.
     * <b> Important: if this doesn't work, suspect {@code bMath.regularizeAngle()} and
     * {@code bMath.subtractAnglesRad()}</b>
     */
    void update(){ //TODO test everything
        if(driveStraightNeeded) {driveStraight();}
    }

}
