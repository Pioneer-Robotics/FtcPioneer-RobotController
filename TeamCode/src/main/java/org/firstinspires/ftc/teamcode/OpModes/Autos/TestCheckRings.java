package org.firstinspires.ftc.teamcode.OpModes.Autos;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Helpers.DataHub;
import org.firstinspires.ftc.teamcode.Helpers.Toggle;

public class TestCheckRings extends AutoScript{
    Gamepad gamepad;
    Toggle A;
    @Override
    public void loop() {
        A.toggle(gamepad.a);
        checkRings();
        if(A.justChanged()){
            resetCheckRings();
        }

        telemetry.addData("laser high", robot.getLaserHigh());
        telemetry.addData("laser low", robot.getLaserLow());
        telemetry.addData("number of rings", numberOfRings);
    }

    @Override
    public void init() {
        standardInit();
        gamepad = DataHub.gamepad1;
        A = new Toggle(false);
    }

    void resetCheckRings(){
        numberOfRings = 0;
    }
}
