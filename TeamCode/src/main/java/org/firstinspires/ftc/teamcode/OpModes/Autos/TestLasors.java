package org.firstinspires.ftc.teamcode.OpModes.Autos;

import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class TestLasors extends AutoScript{
    DistanceSensor laserHigh;
    DistanceSensor laserLow;
    @Override
    public void loop() {
        telemetry.addData("laser high", laserHigh.getDistance(DistanceUnit.CM));
        telemetry.addData("laser low", laserLow.getDistance(DistanceUnit.CM));
    }

    @Override
    public void init() {
        standardInit();
        laserHigh = robot.laserHigh;
        laserLow = robot.laserLow;
    }
}
