package org.firstinspires.ftc.teamcode.OpModes.TeleOps.SingleControllers;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad2;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

public class Driver2ProgramExample extends DriverProgramScript {
    @Override
    public void loop() {
        boolean a = gamepad2.b;
        telemetry.addLine(String.valueOf(a));
        basicTankControlls(gamepad2, whichStick.LEFT);
    }
}
