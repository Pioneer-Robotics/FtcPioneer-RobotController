package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.Helpers.ComplexNum;
import org.firstinspires.ftc.teamcode.Helpers.DataHub;
import org.firstinspires.ftc.teamcode.Helpers.bMath;

public class AutoPilot {
    ComplexNum targetLocation;
    double targetRotationDegrees;
    double targetHeadingRadians;
    private boolean useAutoPilot;
    private int stateNumber;
    double rotationToleranceDegrees;
    double distanceToleranceCM;

    AutoPilot(){
        useAutoPilot = false;
        stateNumber = 0;
        targetLocation = new ComplexNum();
        targetRotationDegrees = 0;
        targetHeadingRadians = 0;
        rotationToleranceDegrees = 4;
    }

    void useAutoPilot(){
        useAutoPilot = true;
    }

    void deactivateAutoPilot(){
        stateNumber = 0;
        useAutoPilot = false;
    }

    void doFullTelemetry(){
        DataHub.telemetry.addData("state number", stateNumber);
        DataHub.telemetry.addData("target rotation degrees", targetRotationDegrees);
        DataHub.telemetry.addData("target X", targetLocation.real);
        DataHub.telemetry.addData("target Y", targetLocation.imag);
        DataHub.telemetry.addData(
                "target heading degrees",
                Math.toDegrees(targetHeadingRadians));
    }

    /**
     * we could have called this "{@code doAutoPilot()}". It handles all the movement.
     * <b> Important: if this doesn't work, suspect {@code bMath.regularizeAngle()} and
     * {@code bMath.subtractAnglesRad()}</b>
     */
    void update(){ //TODO test everything
        if(useAutoPilot){
            switch (stateNumber){
                case 0:
                    //calculate target heading
                    state0_calculateTargetHeading();
                    break;
                case 1:
                    state1_rotateToTargetHeading();
                    break;
                case 2:
                    state2_driveForwardToTargetPosition();
                    break;
                case 3:
                    state3_rotateToTargetRotation();
                    break;
                default:
                    deactivateAutoPilot();
            }
        }
        else{
            stateNumber = 0;
        }
        DataHub.telemetry.addData("auto pilot in use?", useAutoPilot);
    }

    void state0_calculateTargetHeading(){
        ComplexNum currentPosition = Robot.get().getLocationComplex().copy();
        targetHeadingRadians = bMath.acis(ComplexNum.subtract(targetLocation, currentPosition));
        stateNumber++; //move to next step
    }

    void state1_rotateToTargetHeading(){ //TODO finish
        double headingOffset = Robot.get().getRotationRad() - targetHeadingRadians;
        headingOffset = Math.abs(headingOffset);
        headingOffset = Math.toDegrees(headingOffset);
        if(headingOffset > rotationToleranceDegrees){
            //turn towards target heading
        }
        else{
            stateNumber++; //move to next step
        }
    }

    void state2_driveForwardToTargetPosition(){ //TODO finish
                                                //TODO put in a "safty" so it doesn't overshoot
        double xOffset = Robot.get().getLocationComplex().real - targetLocation.real;
        double yOffset = Robot.get().getLocationComplex().imag - targetLocation.imag;
        double distanceToTargetSqrd = bMath.sqd(xOffset) + bMath.sqd(yOffset);
        if(distanceToTargetSqrd > distanceToleranceCM){
            //drive towards target position
            //make sure to match up that the direction of movement is generally correct
        }
        else{
            stateNumber++; //move to next step
        }
    }

    void state3_rotateToTargetRotation(){
        double targetRotationRadians = Math.toRadians(targetRotationDegrees);
        double rotationOffset = bMath.subtractAnglesRad(
                targetRotationRadians,
                Robot.get().getRotationRad()
        );
        if(Math.abs(rotationOffset) > Math.toRadians(rotationToleranceDegrees)){
            rotationOffset = Range.clip(rotationOffset, -0.2, 0.2);
            Robot.get().setDrivePowers(-rotationOffset, rotationOffset);
        }
        else{
            stateNumber++;
        }

    }

    boolean isAutoPilotOn(){
        return useAutoPilot;
    }

}
