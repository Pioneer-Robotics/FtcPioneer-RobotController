package org.firstinspires.ftc.teamcode.OpModes.TeleOps;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Helpers.*;
import org.firstinspires.ftc.teamcode.OpModes.TeleOps.SingleControllers.DriverStandard;

public class TeleOpStandard extends TeleOpScript{
    Telemetry telemetry;
    @Override
    public void loop() {
        driverProgram1.loop();
        telemetry.addLine("telemetry working");
    }
    public TeleOpStandard(){
        selectDriverPrograms();
        telemetry = DataHub.telemetry;
    }
    void selectDriverPrograms(){
        driverProgram1 = new DriverStandard();
    }
}
