package org.firstinspires.ftc.teamcode.OpModes.Autos;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Helpers.DataHub;
import org.firstinspires.ftc.teamcode.Helpers.Toggle;

public class testDriveStriaght extends AutoScript{
    Telemetry telemetry;
    Gamepad gamepad;
    Toggle reachedTarget; //used to make sure the autopilot stops when it should
    State state;
    boolean check;
    boolean helper = false;

    enum State{
        MOVE_FORWARD,
        MOVE_BACK,
        STOP
    }
    @Override
    public void loop() {
        telemetry.addData("state (move or not)", state);
        switch(state){
            case MOVE_FORWARD:
                helper = Robot.get().driveStraight(60);
                //reachedTarget.toggle(helper);
                if(helper){
                    state = State.MOVE_BACK;
                    check = true;
                }
                break;
            case MOVE_BACK:
                Robot.get().setDrivePowers(0,0);
                if(gamepad.left_stick_y > 0.5){
                    if(Robot.get().driveStraight(-20)){
                        state = State.STOP;
                    }
                }
                break;
            case STOP:
                Robot.get().setDrivePowers(0,0);
                break;
        }
        telemetry.addData("helper value", helper);
        telemetry.addData("check value (should be false)", check);
        telemetry.addData("distance travelled", Robot.get().avgRightAndLeftOdos());
        telemetry.addData("state", state);
        telemetry.addData("forward distance value", Robot.get().autoPilot.targetDistance);
    }

    @Override
    public void init() {
        startX = 0;
        startY = 0;
        telemetry = DataHub.telemetry;
        gamepad = DataHub.gamepad1;
        reachedTarget = new Toggle(false);
        state = State.MOVE_FORWARD;
        check = false;
    }
}
