package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Helpers.DataHub;

public class RingMeasurer {
    DistanceSensor laserHigh;
    DistanceSensor laserLow;
    double highCM;
    double lowCM;

    RingMeasurer(){
        laserHigh = DataHub.hardwareMap.get(DistanceSensor.class, "laserHigh");
        laserLow = DataHub.hardwareMap.get(DistanceSensor.class, "laserLow");

        highCM = 0;
        lowCM = 0;
    }

    void update(){
        highCM = laserHigh.getDistance(DistanceUnit.CM);
        lowCM = laserLow.getDistance(DistanceUnit.CM);
    }
}
