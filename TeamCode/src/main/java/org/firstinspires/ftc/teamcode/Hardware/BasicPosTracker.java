package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Helpers.*;

//this class is to do odometry and track the position of the robot. Specifically,
//we measure "position" as the location of the CENTRAL ODOMETER WHEEL
//if it doesn't track position accurately, start by looking at updatePositionComplex()
public class BasicPosTracker {
    private ComplexNum complexPos;
    private Vector2 Vpos;
    private DcMotor left;
    private DcMotor right;
    private DcMotor middle;

    private double leftLast;
    private double rightLast;
    private double middleLast;
    private double rotationLast;

    private double deltaLeft;
    private double deltaRight;
    private double deltaMiddle;
    private double deltaRotation;


    public BasicPosTracker(double startX, double startY){
        setUpEncoders();
        Vpos = new Vector2(startX, startY);
        complexPos = new ComplexNum(startX, startY);
        updateLastValues();
    }
    private void setUpEncoders(){ //sets where left, right, and middle will pull from
        left = BasicRobot.get().hwMap.get(DcMotor.class, Config.motorLT);
        right = BasicRobot.get().hwMap.get(DcMotor.class, Config.motorRT);
        middle = BasicRobot.get().hwMap.get(DcMotor.class, Config.motorRB);
    }
    public void trackPosition(){ //this method is the heart of the class
        calculateDeltas(); //finds all the "delta" values
        updatePositionComplex(); //calculates the actual change in position and applies it
        VposUpdate(); //updates Vpos with the new position
        updateLastValues(); //sets all the "Last" doubles to the current values
    }
    private void updatePositionComplex(){
        ComplexNum middleOdometer = findMiddleOdometerRelativeToCenterOfTurn();
        ComplexNum relativePosition = middleOdometer.safeRotateAboutOrigin(deltaRotation);
        //relativePosition is initially relative to the center of the turn
        relativePosition.minusEquals(middleOdometer);
        //minusEquals() makes it relative to the robot's "current" reference frame
        ComplexNum deltaPosition = relativePosition.safeRotateAboutOrigin(-rotationLast);
        //deltaPosition is the change in position of the robot, relative to the entire field
        complexPos.plusEquals(deltaPosition);
    }
    private void calculateDeltas(){
        deltaLeft = getLeft() - leftLast;
        deltaRight = getRight() - rightLast;
        deltaMiddle = getMiddle() - middleLast;
        deltaRotation = (deltaRight - deltaLeft) / Config.distanceBetweenLeftAndRightOdometersCm;
    }
    private double getLeft(){
        return bMath.odoTicksToCm(left.getCurrentPosition());
    }
    private double getRight(){
        return bMath.odoTicksToCm(right.getCurrentPosition());
    }
    private double getMiddle(){
        return bMath.odoTicksToCm(middle.getCurrentPosition());
    }
    public double getRotation() {
        return ( getRight() - getLeft() ) / Config.distanceBetweenLeftAndRightOdometersCm;
    }

    private void updateLastValues(){
        leftLast = getLeft();
        rightLast = getRight();
        middleLast = getMiddle();
        rotationLast = getRotation();
    }

    private ComplexNum findMiddleOdometerRelativeToCenterOfTurn() {
        ComplexNum Ans = new ComplexNum();
        if(deltaRotation != 0){
            Ans.real = (getLeft() / deltaRotation) + Config.distanceFromLeftOdoToMiddleOdoCm;
            Ans.imag = getMiddle() / deltaRotation;
        }
        else{
            Ans.real = deltaLeft;
            Ans.imag = deltaMiddle;
        }
        return Ans;
    }

    private void VposUpdate(){ //makes Vpos match the complex number form of position
        Vpos.sexXandY(complexPos.real, complexPos.imag);
    }

    public Vector2 getLocation() {
        VposUpdate();
        return Vpos;

    }
    //TODO make a "motion profiler" to handle rotations
}

