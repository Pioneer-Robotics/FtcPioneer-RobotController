package org.firstinspires.ftc.teamcode.OpModes.Autos.DeliverWobbleGoal_ABC;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Helpers.Utils;

public class GoToC {
    boolean[] boolList = new boolean[3];
    boolean done;
    SquareMode codeMode;

    GoToC(){
        Utils.setBooleanArrayToFalse(boolList);
        done = false;
        codeMode = SquareMode.goToSquare;
    }

    enum SquareMode{
        goToSquare,
        goToRingShootPos,
        DONE
    }

    void goToSquareAndThenToShootPos() {
        switch (codeMode) {
            case goToSquare:
                if (!boolList[0]){
                    boolList[0] =Robot.get().driveStraight(230);
                }
                if (boolList[0]){
                    codeMode = SquareMode.goToRingShootPos;
                }
                break;
            case goToRingShootPos:
                if (!boolList[1]) {
                    boolList[1] = Robot.get().driveStraight(-150);
                }
                if (boolList[1]) {
                    Robot.get().setDrivePowers(0.4, -0.4);
                    if (Math.abs(Robot.get().getHeading(AngleUnit.DEGREES)) > 87) {
                        Robot.get().setDrivePowers(0, 0);
                        if (!boolList[2]) {
                            boolList[2] = Robot.get().driveStraight(55);
                        }
                        if (boolList[2]) {
                            Robot.get().setDrivePowers(0.35, -0.35);
                            if (Math.abs(Robot.get().getRotationDegrees()) > 178.25) {
                                Robot.get().setDrivePowers(0, 0);
                                codeMode = SquareMode.DONE;
                            }

                        }
                    }
                }
                break;
            case DONE:
                done = true;
                break;
        }
    }
}
