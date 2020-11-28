package org.firstinspires.ftc.teamcode.OpModes.TeleOps.SingleControllers;

import org.firstinspires.ftc.teamcode.Hardware.Robot;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;

public class Driver1ProgramExample extends DriverProgramScript {
    @Override
    public void loop(){ //TODO make this something you can actually use to drive around with
        basicTankControlls(gamepad1, whichStick.RIGHT);
    }
}
