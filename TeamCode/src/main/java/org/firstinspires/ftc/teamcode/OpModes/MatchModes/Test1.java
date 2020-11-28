package org.firstinspires.ftc.teamcode.OpModes.MatchModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Hardware.Robot;

@TeleOp (name = "Test1", group = "test")

public class Test1 extends LinearOpMode {
    ElapsedTime deltatime;

    @Override
    public void runOpMode() throws InterruptedException {
        init();
        deltatime = new ElapsedTime();
        Robot.init(hardwareMap, telemetry, 0,0);
        while(opModeIsActive()){
            double forward = -gamepad1.left_stick_y;
            double turn = gamepad1.left_stick_x;
            Robot.get().setDrivePowers(forward + turn, forward - turn);
            Robot.get().update();
            telemetry.addData("time", deltatime.seconds());
            telemetry.addData("xPos", Robot.get().getLocation().getX());
            telemetry.addData("yPos", Robot.get().getLocation().getY());
            telemetry.addData("rotation", Robot.get().getRotation());
            telemetry.update();
        }
    }
}
