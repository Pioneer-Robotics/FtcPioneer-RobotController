package org.firstinspires.ftc.teamcode.OpModes.TeleOps;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Helpers.DataHub;
import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.OpModes.TeleOps.SingleControllers.DriverStandard;

public class PositionTrackerTester extends TeleOpScript{

    Telemetry telemetry;
    public PositionTrackerTester(){
        this.telemetry = DataHub.telemetry;
        selectDriverPrograms();
    }
    @Override
    public void loop() {
        driverProgram1.loop();
        Robot.get().doOdometerTelemetry();
        telemetry.addLine("telemetry working");
    }
    void selectDriverPrograms(){
        driverProgram1 = new DriverStandard();
    }
}
