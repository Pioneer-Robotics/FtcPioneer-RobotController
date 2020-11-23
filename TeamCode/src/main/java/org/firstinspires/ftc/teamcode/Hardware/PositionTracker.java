package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Helpers.*;

//this class is to do odometry and track the position of the robot. Specifically,
//we measure "position" as the location of the CENTRAL ODOMETRY WHEEL
public class PositionTracker {
    private ComplexNum complexPos;
    private Vector2 Vpos;
    private DcMotor left;
    private DcMotor right;
    private DcMotor middle;

    private double leftLast;
    private double rightLast;
    private double middleLast;

    private double deltaLeft;
    private double deltaRight;
    private double deltaMiddle;
    private double deltaRotation;


    public PositionTracker(double startX, double startY){
        setUpEncoders();
        Vpos = new Vector2(startX, startY);
        complexPos = new ComplexNum(startX, startY);
    }
    private void setUpEncoders(){ //sets where left and right and middle will pull from
        left = Robot.get().hwMap.get(DcMotor.class, Config.motorLT);
        right = Robot.get().hwMap.get(DcMotor.class, Config.motorRT);
        middle = Robot.get().hwMap.get(DcMotor.class, Config.motorLB);
    }
    public void trackPosition(){
        calculateDeltas();
        updateLastValues();
        updatePositionComplex();
        VposUpdate();
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
    private void updateLastValues(){
        leftLast = getLeft();
        rightLast = getRight();
        middleLast = getMiddle();
    }
    private void updatePositionComplex(){
        ComplexNum middleOdometer = findMiddleOdometerRelativeToCenterOfTurn();
        middleOdometer.rotateAboutOrigin(deltaRotation);
        complexPos.plusEquals(middleOdometer);
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
    // TODO - implement me!
    public double getRotation() {
        return ( getRight() - getLeft() ) / Config.distanceBetweenLeftAndRightOdometersCm;
    }
}
