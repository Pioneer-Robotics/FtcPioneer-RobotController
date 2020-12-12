package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Helpers.ComplexNum;
import org.firstinspires.ftc.teamcode.Helpers.Vector2;
import org.firstinspires.ftc.teamcode.Helpers.bMath;

//this class is to do odometry and track the position of the robot. Specifically,
    //we measure "position" as the location of the CENTRAL ODOMETER WHEEL
//if it doesn't track position accurately, start by looking at updatePositionComplex()
public class PositionTracker2 {
    private final ComplexNum pos;
    private final Vector2 Vpos;
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
    private double deltaTheta;


    public PositionTracker2(double startX, double startY){
        setUpEncoders();
        Vpos = new Vector2(startX, startY);
        pos = new ComplexNum(startX, startY);
        updateLastValues();
    }


    private void setUpEncoders(){ //sets where left, right, and middle will pull from
        left = Robot.get().hwMap.get(DcMotor.class, Config.motorRT);
        right = Robot.get().hwMap.get(DcMotor.class, Config.motorLT);
        middle = Robot.get().hwMap.get(DcMotor.class, Config.motorRB);
    }


    public void trackPosition(){ //this method is the heart of the class
        calculateDeltas(); //finds all the "delta" values
        updatePositionComplex(); //calculates the actual change in position and applies it
        updateLastValues(); //sets all the "Last" doubles to the current values
    }

    private void updatePositionComplex() {
        double majorRotationRadius = (Config.distanceBetweenLeftAndRightOdometersCm * deltaLeft) / (deltaRight-deltaLeft);
        ComplexNum f = ComplexNum.newComplexNumPolar(
                2*(majorRotationRadius+ (Config.distanceBetweenLeftAndRightOdometersCm/2)) * Math.sin(deltaTheta/2),
                deltaTheta + Math.PI/2);
        ComplexNum m = ComplexNum.newComplexNumPolar(deltaMiddle, deltaTheta);
        ComplexNum deltaPositionRobot = ComplexNum.add(f,m);
        pos.plusEquals(deltaPositionRobot.rotateAboutOrigin(-rotationLast));
    }

    private void calculateDeltas(){
        deltaLeft = getLeft() - leftLast;
        deltaRight = getRight() - rightLast;
        deltaMiddle = getMiddle() - middleLast;
        deltaTheta = (deltaRight - deltaLeft) / Config.distanceBetweenLeftAndRightOdometersCm;
    }
    double getLeft(){
        return bMath.odoTicksToCm(left.getCurrentPosition());
    }
    double getRight(){
        return bMath.odoTicksToCm(right.getCurrentPosition());
    }
    double getMiddle(){
        return bMath.odoTicksToCm(middle.getCurrentPosition());
    }
    double getRotationRadians() {
        return ( getRight() - getLeft() ) / Config.distanceBetweenLeftAndRightOdometersCm;
    }



    private void updateLastValues(){
        leftLast = getLeft();
        rightLast = getRight();
        middleLast = getMiddle();
        rotationLast = getRotationRadians();
    }


    private void VposUpdate(){ //makes Vpos match the complex number form of position
        Vpos.sexXandY(pos.real, pos.imag);
    }


    public Vector2 getLocation() {
        VposUpdate(); //TODO STOOPID
        return Vpos;
    }
    //TODO make a "motion profiler" to handle rotations
}
