package org.firstinspires.ftc.teamcode.OpModes.TeleOps;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Hardware.BasicRobot;
import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.OpModes.TeleOps.SingleControllers.DriverProgramScript;
import org.firstinspires.ftc.teamcode.OpModes.TeleOps.SingleControllers.Driver1ProgramExample;
import org.firstinspires.ftc.teamcode.OpModes.TeleOps.SingleControllers.Driver2ProgramExample;

public class OneController extends TeleOpScript {
    double drive, turn, tgtPowerLeft, tgtPowerRight, driveScale;
    ElapsedTime deltaTime = new ElapsedTime();
    Telemetry telemetry;
    Gamepad gamepad;

    @Override
    public void loop() {
        // use left stick to move robot
        drive = gamepad.left_stick_y;
        turn = gamepad.left_stick_x;

        // press both bumpers to get full power
        if (gamepad.right_bumper && gamepad.left_bumper)
            driveScale = 1.0;
            // press either bumper to get half power
        else if (gamepad.left_bumper || gamepad.right_bumper)
            driveScale = 0.5;
            // press neither bumper to get quarter power
        else
            driveScale = 0.25;

        tgtPowerLeft = driveScale * turn;
        tgtPowerRight = -driveScale * turn;
        tgtPowerLeft -= driveScale * drive;
        tgtPowerRight -= driveScale * drive;
        tgtPowerLeft = Range.clip(tgtPowerLeft, -1.0,1.0);
        tgtPowerRight = Range.clip(tgtPowerRight, -1.0,1.0);
        Robot.get().setDrivePowers(tgtPowerLeft,tgtPowerRight);
        telemetry.addLine("working!!!");
        telemetry.addData("xPos", Robot.get().getLocation().getX());
        telemetry.addData("yPos", Robot.get().getLocation().getY());
        telemetry.addData("rotation", Robot.get().getRotation());
    }
    public OneController(Gamepad gamepad, Telemetry telemetry){
        this.gamepad = gamepad;
        this.telemetry = telemetry;
    }
}