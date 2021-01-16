package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Helpers.ComplexNum;
import org.firstinspires.ftc.teamcode.Helpers.Vector2;
import org.firstinspires.ftc.teamcode.Helpers.bMath;

public class BasicOdometer {
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
    private double deltaRotation;


    public BasicOdometer(double startX, double startY, double waitIntervalMS){
        setUpEncoders();
        Vpos = new Vector2(startX, startY);
        complexPos = new ComplexNum(startX, startY);
        deltaTime = new ElapsedTime();
        this.waitIntervalMS = waitIntervalMS;
        updateLastValues();
    }
    private void setUpEncoders(){ //sets where left, right, and middle will pull from
        left = BasicRobot.get().hwMap.get(DcMotor.class, Config.motorRT);
        right = BasicRobot.get().hwMap.get(DcMotor.class, Config.motorLT);
        middle = BasicRobot.get().hwMap.get(DcMotor.class, Config.motorRB);
    }
    public void update(){ //this method is the heart of the class
        if(deltaTime.milliseconds() > waitIntervalMS) {
            calculateDeltas(); //finds all the "delta" values
            updatePositionComplex(); //calculates the actual change in position and applies it
            updateLastValues(); //sets all the "Last" doubles to the current values
            deltaTime.reset();
        }
    }
    private void updatePositionComplex(){ //this is the meat of the class, most complex bit (puns)
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

    private ComplexNum findMiddleOdometerRelativeToCenterOfTurn() {
        ComplexNum Ans = new ComplexNum();
        if(deltaRotation != 0){
            Ans.real = (deltaLeft / deltaRotation) + Config.distanceFromLeftOdoToMiddleOdoCm;
            Ans.imag = deltaMiddle / deltaRotation;
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
}