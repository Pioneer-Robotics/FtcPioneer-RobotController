package org.firstinspires.ftc.teamcode.Helpers;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * the idea of this class is to hold all the objects that tend to get passed around a lot in one
 * centralized place, so that you don't have to pass them around!
 */

public class DataHub {
    public static Telemetry telemetry;
    public static HardwareMap hardwareMap;
    public static Gamepad gamepad1;

    /**
     * call this method in the init stage of the OpMode, make sure it has everything it needs.
     * @param telemetry the OpMode's telemtery object
     * @param hardwareMap the OpMode's hardwareMap object
     */
    public static void init(Telemetry telemetry, HardwareMap hardwareMap, Gamepad gamepad1){
        DataHub.telemetry = telemetry;
        DataHub.hardwareMap = hardwareMap;
        DataHub.gamepad1 = gamepad1;
    }
}
