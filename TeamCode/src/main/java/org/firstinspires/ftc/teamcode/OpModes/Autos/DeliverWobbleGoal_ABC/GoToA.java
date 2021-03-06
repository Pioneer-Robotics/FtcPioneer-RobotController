package org.firstinspires.ftc.teamcode.OpModes.Autos.DeliverWobbleGoal_ABC;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Helpers.*;

public class GoToA extends GoToSquare{
    boolean[] boolList = new boolean[10];
    Robot robot;
    Gamepad gamepad;
    SquareMode GoA;
    Telemetry telemetry;
    Toggle helper;

    //these are to make sure the turn was good
    double error;
    double tolerance;
    ElapsedTime deltaTime;

    GoToA(){
        Utils.setBooleanArrayToFalse(boolList);
        done = false;
        GoA = SquareMode.goToSquare;
        telemetry = DataHub.telemetry;
        gamepad = DataHub.gamepad1;
        helper = new Toggle(false);
        robot = Robot.get(); //this will make it easier to write and read the code


        deltaTime = new ElapsedTime();
    }

    enum SquareMode {
        goToSquare,
        backUp,
        turnTo90,
        forwardSlightly,
        turnTo180,
        adjustTurnAccurate,
        checkTurnWasCorrect,
        DONE
    }

    @Override
    void goToSquareAndThenToShootPos() {
        helper.toggle(gamepad.a);
        switch (GoA) {
            case goToSquare:
                //drive forward a distance
                if (!boolList[0]){
                    boolList[0] = robot.driveStraight(180-120);
                }
                if (boolList[0]){
                    robot.deactivateDriveStraight();
                    robot.setDrivePowers(0,0);
                    GoA = SquareMode.backUp;
                }
                break;
            case backUp:
                if(!boolList[1]){
                    boolList[1] = robot.driveStraight(-20);
                }
                if(boolList[1]){
                    robot.setDrivePowers(0,0);
                    GoA = SquareMode.turnTo90;
                }
                break;
            case turnTo90:
                robot.setDrivePowers(0.4,-0.4);
                if(Math.abs( robot.getRotationDegrees() ) > 85){
                    robot.brake();
                    robot.setDrivePowers(0,0);
                    GoA = SquareMode.forwardSlightly;
                }
                break;
            case forwardSlightly:{
                if (!boolList[2]){
                    boolList[2] = robot.driveStraight(62);
                }
                if(boolList[2]){
                    GoA = SquareMode.turnTo180;
                }
            }
            case turnTo180:
                robot.setDrivePowers(0.35,-0.35);
                if (Math.abs( robot.getRotationDegrees() ) > 175){
                    GoA = SquareMode.adjustTurnAccurate;
                }
                break;
            case adjustTurnAccurate:
                //error is in degrees
                error = bMath.subtractAnglesDeg(180, robot.getRotationDegrees());
                double P = 0.1;
                tolerance = 3; //tolerance is the amount of allowable error
                double speed = P * error + 0.32; //0.32 is about the minimum needed to move
                if(Math.abs(error) > tolerance){ //error is too large so adjust
                    robot.setDrivePowers(-speed, speed);
                }
                else{ //this means we're within tolerance
                    GoA = SquareMode.checkTurnWasCorrect;
                    deltaTime.reset();
                    robot.brake();
                    robot.setDrivePowers(0,0);
                }
                break;
            case checkTurnWasCorrect:
                error = bMath.subtractAnglesDeg(180, robot.getRotationDegrees());
                tolerance = 3; //tolerance is the amount of allowable error
                robot.setDrivePowers(0,0);
                if(Math.abs(error) < tolerance){
                    if(deltaTime.seconds() > 1){
                        GoA = SquareMode.DONE;
                    }
                }
                else{
                    GoA = SquareMode.adjustTurnAccurate;
                }

                break;
            case DONE:
                super.done = true;
                robot.dontBrake(); //sets zero power behavior of drive motors to float
                robot.setDrivePowers(0,0);
                break;
            default:
                GoA = SquareMode.DONE;
                break;
        }
    }

}
