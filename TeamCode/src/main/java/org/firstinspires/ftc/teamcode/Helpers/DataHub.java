package org.firstinspires.ftc.teamcode.Helpers;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * the idea of this class is to hold all the objects that tend to get passed around a lot in one
 * centralized place, so that you don't have to pass them around!
 */
public class DataHub { //the point of this class is to make objects accessible to anywhere that needs it
    public static Gamepad gamepad1;
    public static Gamepad gamepad2;
    public static Telemetry telemetry;
    public static HardwareMap hardwareMap;

    /**
     * call this method in the init stage of the OpMode, make sure it has everything it needs.
     * @param gamepad1 gamepad1
     * @param gamepad2 gamepad2
     * @param telemetry the OpMode's telemtery object
     * @param hardwareMap the OpMode's hardwareMap object
     */
    public static void init(Gamepad gamepad1, Gamepad gamepad2,
                            Telemetry telemetry, HardwareMap hardwareMap){
        DataHub.gamepad1 = gamepad1;
        DataHub.gamepad2 = gamepad2;
        DataHub.telemetry = telemetry;
        DataHub.hardwareMap = hardwareMap;
    }
}
