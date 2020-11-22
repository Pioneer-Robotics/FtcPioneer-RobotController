package org.firstinspires.ftc.teamcode.OpModes.TeleOps.SingleControllers;

import org.firstinspires.ftc.teamcode.Hardware.Robot;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;

public class Driver1ProgramExample extends DriverProgramScript {
    @Override
    public void loop(){
        Robot.get().setDrivePowers(gamepad1.left_stick_y);
    }
}
