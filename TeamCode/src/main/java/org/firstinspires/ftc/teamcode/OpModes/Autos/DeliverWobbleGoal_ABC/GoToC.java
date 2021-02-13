package org.firstinspires.ftc.teamcode.OpModes.Autos.DeliverWobbleGoal_ABC;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Helpers.*;

public class GoToC extends GoToSquare{
    boolean[] boolList = new boolean[10];
    Robot robot;
    Gamepad gamepad;
    State state;
    Telemetry telemetry;
    Toggle helper;

    GoToC(){
        Utils.setBooleanArrayToFalse(boolList);
        done = false;
        state = State.goToSquare;
        telemetry = DataHub.telemetry;
        gamepad = DataHub.gamepad1;
        helper = new Toggle(false);
        robot = Robot.get(); //this will make it easier to write and read the code
    }

    enum State {
        goToSquare,
        backUp,
        turnTo90,
        forwardSlightly,
        turnTo180,
        DONE
    }

    @Override
    void goToSquareAndThenToShootPos() {
        telemetry.addLine("in GoToC");
        helper.toggle(gamepad.a);
        switch (state) {
            case goToSquare:
                //drive forward a distance
                if (!boolList[0]){
                    boolList[0] = robot.driveStraight(215);
                    telemetry.addLine("driving forward");
                }
                if (boolList[0]){
                    robot.deactivateDriveStraight();
                    robot.setDrivePowers(0,0);
                    telemetry.addLine("waiting to back up. Press A to continue");
                    state = State.backUp;
                }
                telemetry.addData("it thinks it's gone too far", boolList[0]);
                break;
            case backUp:
                if(!boolList[1]){
                    boolList[1] = robot.driveStraight(-140);
                }
                if(boolList[1]){
                    robot.setDrivePowers(0,0);
                    state = State.turnTo90;
                }
                break;
            case turnTo90:
                robot.setDrivePowers(0.4,-0.4);
                if(Math.abs( robot.getRotationDegrees() ) > 87.5){
                    robot.setDrivePowers(0,0);
                    state = State.forwardSlightly;
                }
                break;
            case forwardSlightly:{
                if (!boolList[2]){
                    boolList[2] = robot.driveStraight(50);
                }
                if(boolList[2]){
                    state = State.turnTo180;
                }
            }
            case turnTo180:
                robot.setDrivePowers(0.35,-0.35);
                if (Math.abs( robot.getRotationDegrees() ) > 175){
                    state = State.DONE;
                }
                break;
            case DONE:
                super.done = true;
                robot.setDrivePowers(0,0);
                break;
            default:
                state = State.DONE;
                break;
        }
    }
}
