package org.firstinspires.ftc.teamcode.OpModes.TeleOps;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Helpers.DataHub;
import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.OpModes.TeleOps.SingleControllers.DriverStandard;

public class PositionTrackerTester extends TeleOpScript{
    Gamepad gamepad;
    Telemetry telemetry;

    @Override
    public void loop() {
        driverProgram1.loop();
        Robot.get().doOdometerTelemetry();
        telemetry.addLine("telemetry working");
    }
    void selectDriverPrograms(){
        driverProgram1 = new DriverStandard();
    }

    public void init(){
        this.telemetry = DataHub.telemetry;
        this.gamepad = DataHub.gamepad1;
        selectDriverPrograms();
    }
    public PositionTrackerTester(){

    }

}
