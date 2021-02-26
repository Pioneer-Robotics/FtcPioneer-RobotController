package org.firstinspires.ftc.teamcode.OpModes.Autos.DeliverWobbleGoal_ABC;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Helpers.Utils;
import org.firstinspires.ftc.teamcode.Helpers.bMath;

public class GoToA extends GoToSquare {
    Robot robot;
    double error;
    double tolerance;
    ElapsedTime deltaTime;
    boolean[] boolList = new boolean[3];
    boolean done;
    SquareMode codeMode;

    GoToA() {
        Utils.setBooleanArrayToFalse(boolList);
        done = false;
        codeMode = SquareMode.goToSquare;
    }

    enum SquareMode {
        goToSquare,
        backward,
        turn90,
        forwardSlightly,
        turn180,
        goToRingShootPos,
        adjustTurnAccurate,
        checkTurnWasCorrect,
        DONE
    }

    @Override
    void goToSquareAndThenToShootPos() {
        switch (codeMode) {
            case goToSquare:
                if (!boolList[0]) {
                    boolList[0] = Robot.get().driveStraight(80);
                }
                if (boolList[0]) {
                    codeMode = SquareMode.goToRingShootPos;
                }
                break;
            case backward:
                if (!boolList[1]) {
                    boolList[1] = Robot.get().driveStraight(-35);
                }
                if (boolList[1]) {
                    codeMode = SquareMode.turn90;
                }
            case turn90:
                Robot.get().setDrivePowers(0.4, -0.4);
                if (Math.abs(Robot.get().getHeading(AngleUnit.DEGREES)) > 87) {
                    Robot.get().setDrivePowers(0, 0);
                    codeMode = SquareMode.forwardSlightly;
                }
                break;
            case forwardSlightly:
                if (!boolList[2]) {
                    boolList[2] = Robot.get().driveStraight(55);
                }
                if (boolList[2]) {
                    codeMode = SquareMode.turn180;
                }
                break;
            case turn180:
                Robot.get().setDrivePowers(0.35, -0.35);
                if (Math.abs(Robot.get().getRotationDegrees()) > 178.25) {
                    Robot.get().setDrivePowers(0, 0);
                    codeMode = SquareMode.adjustTurnAccurate;
                }
                break;
            case adjustTurnAccurate:
                //error is in degrees
                error = bMath.subtractAnglesDeg(180, robot.getRotationDegrees());
                double P = 0.1;
                tolerance = 3; //tolerance is the amount of allowable error
                double speed = P * error + 0.32; //0.32 is about the minimum needed to move
                if (Math.abs(error) > tolerance) { //error is too large so adjust
                    robot.setDrivePowers(-speed, speed);
                } else { //this means we're within tolerance
                    codeMode = SquareMode.checkTurnWasCorrect;
                    deltaTime.reset();
                    robot.brake();
                    robot.setDrivePowers(0, 0);
                }
                break;
            case checkTurnWasCorrect:
                error = bMath.subtractAnglesDeg(180, robot.getRotationDegrees());
                tolerance = 3; //tolerance is the amount of allowable error
                robot.setDrivePowers(0, 0);
                if (Math.abs(error) < tolerance) {
                    if (deltaTime.seconds() > 1) {
                        codeMode = SquareMode.DONE;
                    }
                } else {
                    codeMode = SquareMode.adjustTurnAccurate;
                }

                break;
            case DONE:
                done = true;
                break;
        }
    }
}
