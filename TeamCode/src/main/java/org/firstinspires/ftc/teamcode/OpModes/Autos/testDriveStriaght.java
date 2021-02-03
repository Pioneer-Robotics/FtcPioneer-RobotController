package org.firstinspires.ftc.teamcode.OpModes.Autos;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Helpers.DataHub;
import org.firstinspires.ftc.teamcode.Helpers.Toggle;

public class testDriveStriaght extends AutoScript{
    Telemetry telemetry;
    Toggle reachedTarget; //used to make sure the autopilot stops when it should
    State state;

    enum State{
        MOVE,
        STOP
    }
    @Override
    public void loop() {
        switch(state){
            case MOVE:
                reachedTarget.toggle(Robot.get().driveStraight(50)); //looks terrible I know (Joe)
                if(reachedTarget.justBecameTrue()){
                    state = State.STOP;
                }
                break;
            case STOP:
                Robot.get().stopAllMotors();
        }
        telemetry.addData("distance travelled", Robot.get().avgRightAndLeftOdos());
    }

    @Override
    public void init() {
        startX = 0;
        startY = 0;
        telemetry = DataHub.telemetry;
        reachedTarget = new Toggle(false);
        state = State.MOVE;
    }
}
