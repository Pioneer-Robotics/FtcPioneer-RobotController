package org.firstinspires.ftc.teamcode.OpModes.Autos;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Helpers.DataHub;
import org.firstinspires.ftc.teamcode.Helpers.Toggle;

public class testDriveStriaght extends AutoScript{
    Telemetry telemetry;
    Toggle reachedTarget; //used to make sure the autopilot stops when it should
    State state;
    boolean check;
    boolean helper = false;

    enum State{
        MOVE,
        STOP
    }
    @Override
    public void loop() {
        telemetry.addData("state (move or not)", state);
        switch(state){
            case MOVE:
                helper = Robot.get().driveStraight(60);
                //reachedTarget.toggle(helper);
                if(helper){
                    state = State.STOP;
                    check = true;
                }
                break;
            case STOP:
                Robot.get().setDrivePowers(0,0);
                break;
        }
        telemetry.addData("helper value", helper);
        telemetry.addData("distance travelled", Robot.get().avgRightAndLeftOdos());
        telemetry.addData("check value (should be false)", check);
        telemetry.addData("forward distance value", Robot.get().autoPilot.forwardDistance);
    }

    @Override
    public void init() {
        startX = 0;
        startY = 0;
        telemetry = DataHub.telemetry;
        reachedTarget = new Toggle(false);
        state = State.MOVE;
        check = false;
    }
}
