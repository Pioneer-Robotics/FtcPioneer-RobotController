package org.firstinspires.ftc.teamcode.OpModes.Autos;

import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class TestLasors extends AutoScript{

    @Override
    public void loop() {
        telemetry.addData("laser high", robot.getLaserHigh());
        telemetry.addData("laser low", robot.getLaserLow());
    }

    @Override
    public void init() {
        standardInit();
    }
}
