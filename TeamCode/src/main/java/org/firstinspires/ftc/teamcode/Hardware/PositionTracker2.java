package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Helpers.ComplexNum;
import org.firstinspires.ftc.teamcode.Helpers.Vector2;
import org.firstinspires.ftc.teamcode.Helpers.bMath;

//this class is to do odometry and track the position of the robot. Specifically,
//we measure "position" as the location of the CENTRAL ODOMETER WHEEL
//if it doesn't track position accurately, start by looking at updatePositionComplex()
public class PositionTracker2 {
    private final ComplexNum complexPos;
    private final Vector2 Vpos;

    double waitIntervalMS; //MS = milliseconds
    ElapsedTime deltaTime;

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


    public PositionTracker2(double startX, double startY, double waitIntervalMS){
        setUpEncoders();
        Vpos = new Vector2(startX, startY);
        complexPos = new ComplexNum(startX, startY);
        deltaTime = new ElapsedTime();
        this.waitIntervalMS = waitIntervalMS;
        updateLastValues();
    }


    private void setUpEncoders(){ //sets where left, right, and middle will pull from
        left = Robot.get().hardwareMap.get(DcMotor.class, Config.motorRT);
        right = Robot.get().hardwareMap.get(DcMotor.class, Config.motorLT);
        middle = Robot.get().hardwareMap.get(DcMotor.class, Config.motorRB);
    }


    public void update(){ //this method is the heart of the class
        if(deltaTime.milliseconds() > waitIntervalMS) {
            calculateDeltas(); //finds all the "delta" values
            updatePositionComplex(); //calculates the actual change in position and applies it
            updateLastValues(); //sets all the "Last" doubles to the current values
            deltaTime.reset();
        }
    }

    private void updatePositionComplex() {
        ComplexNum f;

        if (deltaRight != deltaLeft){ //avoid dividing by zero
            double majorRotationRadius = (Config.distanceBetweenLeftAndRightOdometersCm * deltaLeft) / (deltaRight-deltaLeft);
            f = ComplexNum.newComplexNumPolar(
                    (deltaRight+deltaLeft)/2,
                    deltaTheta + Math.PI/2.0);
        } else {
            f = ComplexNum.newComplexNumPolar(deltaRight,deltaTheta + Math.PI/2.0);
        }

        ComplexNum m = ComplexNum.newComplexNumPolar(deltaMiddle, deltaTheta);
        ComplexNum deltaPositionRobot = ComplexNum.add(f,m);
        complexPos.plusEquals(deltaPositionRobot.rotateAboutOrigin(-rotationLast));
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
        Vpos.sexXandY(complexPos.real, complexPos.imag);
    }


    public Vector2 getLocation() {
        VposUpdate(); //TODO STOOPID
        return Vpos;
    }
    //TODO make a "motion profiler" to handle rotations
}