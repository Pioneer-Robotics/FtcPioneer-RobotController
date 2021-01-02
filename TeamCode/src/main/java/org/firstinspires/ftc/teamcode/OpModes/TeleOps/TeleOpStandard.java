package org.firstinspires.ftc.teamcode.OpModes.TeleOps;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Helpers.*;
import org.firstinspires.ftc.teamcode.OpModes.TeleOps.SingleControllers.DriverStandard;

public class TeleOpStandard extends TeleOpScript{
    Telemetry telemetry;
    Gamepad gamepad;
    @Override
    public void loop() {
        driverProgram1.loop();
        telemetry.addLine("telemetry working");
    }
    @Override
    public void init(){
        selectDriverPrograms();
        telemetry = DataHub.telemetry;
        this.gamepad = DataHub.gamepad1;
    }
    void selectDriverPrograms(){
        driverProgram1 = new DriverStandard();
    }
}
