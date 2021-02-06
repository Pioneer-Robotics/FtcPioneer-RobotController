package org.firstinspires.ftc.teamcode.OpModes.Autos.DeliverWobbleGoal_ABC;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Helpers.DataHub;
import org.firstinspires.ftc.teamcode.Helpers.Utils;

public class GoToC extends GoToSquare{
    boolean[] boolList = new boolean[3];
    boolean done;
    SquareMode codeMode;
    Telemetry telemetry;

    GoToC(){
        Utils.setBooleanArrayToFalse(boolList);
        done = false;
        codeMode = SquareMode.goToSquare;
        telemetry = DataHub.telemetry;
    }

    enum SquareMode{
        goToSquare,
        goToRingShootPos,
        DONE
    }

    @Override
    void goToSquareAndThenToShootPos() {
        telemetry.addLine("in GoToC");
        switch (codeMode) {
            case goToSquare:
                //drive forward a distance
                if (!boolList[0]){
                    boolList[0] =Robot.get().driveStraight(230);
                    telemetry.addLine("driving forward");
                }
                if (boolList[0]){
                    codeMode = SquareMode.goToRingShootPos;
                }
                break;
            case goToRingShootPos:
                //drive backwards
                if (!boolList[1]) {
                    boolList[1] = Robot.get().driveStraight(-150);
                    telemetry.addLine("driving back");
                }
                if (boolList[1]) { //if it has driven backwards
                    Robot.get().setDrivePowers(0.4, -0.4);
                    if (Math.abs(Robot.get().getHeading(AngleUnit.DEGREES)) > 87) { //turn
                        Robot.get().setDrivePowers(0, 0); //stop movement
                        if (!boolList[2]) { //drive forwards
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
