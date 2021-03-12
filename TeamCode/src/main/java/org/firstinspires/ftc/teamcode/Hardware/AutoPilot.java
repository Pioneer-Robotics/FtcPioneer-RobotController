package org.firstinspires.ftc.teamcode.Hardware;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Helpers.*;


public class AutoPilot {
    //these are all used as part of the "driveForward()" method
    boolean driveStraightNeeded;
    boolean targetReached; //used to know if the driveStright() method has reached it's goal
    private double startingRightOdoDistance;
    private double startingLeftOdoDistance;
    double forwardSpeed; //how fast should it move by default?
    double threshold; //how close to target is close enough?
    public double targetDistance; //how far does it need to move (if problems suspect this)
    boolean FixingStraightNeeded;
    DriveMode driveMode; //basically what state are we in?

    //these are used as part of the "turnAbsolute()" method
    private double startingRotation; //not used rn
    TurnMode turnMode;
    boolean turnNeeded;
    double targetAngle;
    double turnThreshold;
    double P; //used to PID the speed thing
    double SavedRotation;
    double AngleToleranceSmall = Robot.get().getRotationDegrees() -3;
    double AngleToleranceBig = Robot.get().getRotationDegrees() + 3;

    FixDS DS;

    //more general variables
    Robot robot;
    Telemetry telemetry;
    Toggle stopReps; //basically the idea here is to prevent autoPilot from accidentally running forever
    boolean autoPilotNeeded;

    AutoPilot(){
        //block used for "driveStriaght()"
        driveStraightNeeded = false;
        targetReached = false;
        startingRightOdoDistance = 0;
        startingLeftOdoDistance = 0;
        forwardSpeed = 0.3;
        threshold = 10;
        targetDistance = 0;
        driveMode = DriveMode.CALCULATE;


        //block used for "turnAbsolute()"
        startingRotation = 0;
        turnMode = TurnMode.CALCULATE;

        turnNeeded = false;
        targetAngle = 0;
        turnThreshold = Math.toRadians(10); //how close is close enough when it comes to turing?
        P = 0.1;


        //block used for more general things
        robot = Robot.get();
        telemetry = DataHub.telemetry;
        stopReps = new Toggle(false);
        autoPilotNeeded = false;
    }
    enum FixDS{
        CALCULATE,
        FIX,
        EXIT_FixDS
    }
    enum DriveMode{
        CALCULATE,
        DRIVE_FORWARD,
        EXIT_AUTOPILOT
    }
    enum TurnMode {
        CALCULATE,
        TURN,
        EXIT_AUTOPILOT
    }

    /**
     * we could have called this "{@code doAutoPilot()}". It handles all the movement.
     */
    void update(){ //TODO test everything
        stopReps.set(autoPilotNeeded);
        if(autoPilotNeeded && !stopReps.justBecameTrue()){
            //this means you have to call autopilot twice in a row before anything happens
            if (driveStraightNeeded) {
                driveStraight();
            }
            else if(turnNeeded){ //you shouldn't try to drive straight while turning
                // (in fact it's rather difficult)
                turnAbsolute();
            }
        }
    }

    /**
     * will drive straight forward an amount set by the "forwardDistance" class variable
     * return true when it finishes
     */
    void driveStraight(){
        boolean ans = false;
        double startingDeg = 0;
        switch(driveMode){
            case CALCULATE: //this should only run once, not every cycle, and it shouldn't be possible to run again until the full run finishes
                startingRotation = Robot.get().getRotationRad(); //remembers the rotation we want to keep
                startingLeftOdoDistance = Robot.get().getLeftOdo(); //what does the left odo measure right now?
                startingRightOdoDistance = Robot.get().getRightOdo(); //what does the right odo measure rn?
                driveMode = DriveMode.DRIVE_FORWARD; //move on the next state
                startingDeg = robot.getRotationDegrees();
                telemetry.addLine("calculating");
                break;
            case DRIVE_FORWARD:
                double AngleDelta = bMath.subtractAnglesDeg(startingDeg, robot.getRotationDegrees());
                if((avgChangeInLeftAndRightOdo() - targetDistance) > threshold){ //we've overshot (gone to far)
                    Robot.get().setDrivePowers(-forwardSpeed, -forwardSpeed);
                }
                else if ((avgChangeInLeftAndRightOdo() - targetDistance) < -threshold){ //haven't gone far enough
                    Robot.get().setDrivePowers(forwardSpeed, forwardSpeed);
                }
                else{ //this means we're in the target range
                    driveMode = DriveMode.EXIT_AUTOPILOT;
                }
                if (Math.abs(AngleDelta) > 3){
                SuperStraight();
                FixingStraightNeeded = true;
            }
                telemetry.addLine("driving forward");
                telemetry.addData("distance travelled", avgChangeInLeftAndRightOdo());
                break;
            case EXIT_AUTOPILOT:
                driveStraightNeeded = false;
                autoPilotNeeded = false;
                ans = true;
                robot.setDrivePowers(0,0);
                driveMode = DriveMode.CALCULATE;
                break;
        }
        telemetry.addLine("auto pilot active, running 'drive straight()'");
        telemetry.addData("distance travelled", avgChangeInLeftAndRightOdo());
        targetReached = ans;
    }

    double avgChangeInLeftAndRightOdo(){
        double ansLeft = Robot.get().getLeftOdo() - startingLeftOdoDistance;
        double ansRight = Robot.get().getRightOdo() - startingRightOdoDistance;
        return (ansLeft + ansRight) / 2.0;
    }

    void turnAbsolute(){ //TODO implement me in Robot
        switch(turnMode){
            case CALCULATE:
                startingRotation = robot.getRotationRad();
                turnMode = TurnMode.TURN;
                break;
            case TURN:
                double error = robot.getRotationRad() - targetAngle;
                if(Math.abs(error) > turnThreshold){
                    //this means we're not close enough to the target angle
                    double speed = P * error; //if error is negative, this will be negative
                    robot.setDrivePowers(-speed, speed); //left should have the opposite sign of speed
                }
                else{
                    turnMode = TurnMode.EXIT_AUTOPILOT;
                }
                break;
            case EXIT_AUTOPILOT:
                break;
        }
    }
    void SuperStraight(){
        switch (DS){
            case FIX:{
                CheckLeavingDM();
                SavedRotation = robot.getRotationDegrees();
                if (SavedRotation < SavedRotation +3){
                    robot.setDrivePowers(0.25,0.4);
                } else if (SavedRotation < SavedRotation -3){
                    robot.setDrivePowers(0.4,0.25);
                } else {
                    DS = FixDS.EXIT_FixDS;
                }
            }
            break;
            case EXIT_FixDS:{
                FixingStraightNeeded = false;
                driveMode = DriveMode.DRIVE_FORWARD;
            }
            }
    }
    void CheckLeavingDM(){
        if((avgChangeInLeftAndRightOdo() - targetDistance) > threshold){ //we've overshot (gone to far)
            Robot.get().setDrivePowers(-forwardSpeed, -forwardSpeed);
        }
        else if ((avgChangeInLeftAndRightOdo() - targetDistance) < -threshold){ //haven't gone far enough
            Robot.get().setDrivePowers(forwardSpeed, forwardSpeed);
        }
        else{ //this means we're in the target range
            driveMode = DriveMode.EXIT_AUTOPILOT;
        }
    }
}
