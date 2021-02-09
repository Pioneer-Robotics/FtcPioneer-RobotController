package org.firstinspires.ftc.teamcode.OpModes.Autos.DeliverWobbleGoal_ABC;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Helpers.*;

public class GoToC extends GoToSquare{
    boolean[] boolList = new boolean[3];
    boolean done;
    Robot robot;
    Gamepad gamepad;
    SquareMode codeMode;
    Telemetry telemetry;
    Toggle helper;

    GoToC(){
        Utils.setBooleanArrayToFalse(boolList);
        done = false;
        codeMode = SquareMode.goToSquare;
        telemetry = DataHub.telemetry;
        gamepad = DataHub.gamepad1;
        helper = new Toggle(false);
        robot = Robot.get(); //this will make it easier to write and read the code
    }

    enum SquareMode{
        goToSquare,
        goToRingShootPos,
        DONE
    }

    @Override
    void goToSquareAndThenToShootPos() {
        telemetry.addLine("in GoToC");
        helper.toggle(gamepad.a);
        switch (codeMode) {
            case goToSquare:
                //drive forward a distance
                if (!boolList[0]){
                    boolList[0] = robot.driveStraight(150);
                    telemetry.addLine("driving forward");
                }
                if (boolList[0]){
                    robot.deactivateDriveStraight();
                    robot.setDrivePowers(0,0);
                    telemetry.addLine("waiting to back up. Press A to continue");
                    if(helper.justChanged()) {
                        codeMode = SquareMode.goToRingShootPos;
                    }
                }
                telemetry.addData("it thinks it's gone too far", boolList[0]);
                break;
            case goToRingShootPos:
                //drive backwards
                if (!boolList[1]) {
                    boolList[1] = Robot.get().driveStraight(-100);
                    telemetry.addLine("driving back");
                }
                if (boolList[1]) { //if it has driven backwards
                    robot.setDrivePowers(0.4, -0.4); //start rotating
                    if (Math.abs(Robot.get().getHeading(AngleUnit.DEGREES)) > 87) { //turn
                        robot.setDrivePowers(0, 0); //stop movement
                        if (!boolList[2]) { //drive forwards
                            boolList[2] = Robot.get().driveStraight(55);
                        }
                        if (boolList[2]) {
                            robot.setDrivePowers(0.35, -0.35);
                            if (Math.abs(Robot.get().getRotationDegrees()) > 178.25) {
                                telemetry.addLine("waiting to start firing. Press A to continue");
                                if(helper.justChanged()) {
                                    robot.setDrivePowers(0, 0);
                                    codeMode = SquareMode.DONE;
                                }
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
