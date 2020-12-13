package org.firstinspires.ftc.teamcode.OpModes.TeleOps;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.OpModes.TeleOps.SingleControllers.DriverStandard;

public class PositionTrackerTester extends TeleOpScript{
    Gamepad gamepad1;
    Telemetry telemetry;
    public PositionTrackerTester(Gamepad gamepad1, Telemetry telemetry){
        this.gamepad1 = gamepad1;
        this.telemetry = telemetry;
        selectDriverPrograms();
    }
    @Override
    public void loop() {
        driverProgram1.loop();
        Robot.get().doOdometerTelemetry(); //TODO implement
    }
    void selectDriverPrograms(){
        driverProgram1 = new DriverStandard(gamepad1, telemetry);
    }
}
