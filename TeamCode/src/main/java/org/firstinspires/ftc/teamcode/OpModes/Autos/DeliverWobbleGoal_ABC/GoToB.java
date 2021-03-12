package org.firstinspires.ftc.teamcode.OpModes.Autos.DeliverWobbleGoal_ABC;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Helpers.bMath;

class GoToB extends GoToSquare {
    Robot robot;
    double error;
    double tolerance;
    ElapsedTime deltaTime;

    void goToSquareAndThenToShootPos() {

    }

    enum SquareMode {
        goToSquare,
        goToRingShootPos,
        turnTo0,
        backwardSlightly,
        adjustTurnAccurate,
        checkTurnWasCorrect,
        DONE
    }

    SquareMode GoB;
    boolean[] moveAUTO = new boolean[15];
    double startingPos = 0;
    double DriveDistance = 0;

    public void loop() {
        switch (GoB) {
            case goToSquare:
                moveAUTO[1] = false;
                driveDistance(153, .5, .15);
                if (moveAUTO[1]) {
                    GoB = SquareMode.turnTo0;
                }
                break;
            case turnTo0:
                Robot.get().setDrivePowers(-0.3, 0.3);
                if ((Robot.get().getRotationDegrees()) < 0) {
                    Robot.get().setDrivePowers(0, 0);
                }
                break;
            case adjustTurnAccurate:
                //error is in degrees
                error = bMath.subtractAnglesDeg(2, robot.getRotationDegrees());
                double P = 0.1;
                tolerance = 3; //tolerance is the amount of allowable error
                double speed = P * error + 0.32; //0.32 is about the minimum needed to move
                if (Math.abs(error) > tolerance) { //error is too large so adjust
                    robot.setDrivePowers(-speed, speed);
                } else { //this means we're within tolerance
                    GoB = GoB.checkTurnWasCorrect;
                    deltaTime.reset();
                    robot.brake();
                    robot.setDrivePowers(0, 0);
                }
                break;
            case checkTurnWasCorrect:
                error = bMath.subtractAnglesDeg(2, robot.getRotationDegrees());
                tolerance = 3; //tolerance is the amount of allowable error
                robot.setDrivePowers(0, 0);
                if (Math.abs(error) < tolerance) {
                    if (deltaTime.seconds() > 1) {
                        GoB = GoB.backwardSlightly;
                    }
                } else {
                    GoB = GoB.adjustTurnAccurate;
                }

            case backwardSlightly:
                if (!moveAUTO[2]) {
                    moveAUTO[2] = Robot.get().driveStraight(-40);
                }
                if (moveAUTO[2]) {
                    GoB = SquareMode.DONE;
                }
                break;
            case DONE: {
                done = true;
                break;
            }
        }
    }

    void driveDistance(double distance, double leftPower, double rightPower) {
        if (startingPos == 5)
            startingPos = Robot.get().getLeftOdo();
        DriveDistance = startingPos + distance;
        moveAUTO[1] = false;
        Robot.get().setDrivePowers(leftPower, rightPower);
        if (Robot.get().getLeftOdo() >= DriveDistance) {
            Robot.get().setDrivePowers(0, 0);
            moveAUTO[1] = true;
            startingPos = 0;
        }
    }

    public void init() {
        GoB = SquareMode.goToSquare;
    }

}
