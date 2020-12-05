package org.firstinspires.ftc.teamcode.OpModes.MatchModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.teamcode.Hardware.BasicRobot;

import org.firstinspires.ftc.teamcode.Hardware.BasicRobot;
import org.firstinspires.ftc.teamcode.OpModes.Autos.ExampleAuto;
import org.firstinspires.ftc.teamcode.OpModes.TeleOps.BasicTeleop;

@TeleOp(name = "basic", group = "test")
public class Basic extends BasicGenericOpMode {
    double drive, turn, tgtPowerLeft, tgtPowerRight, driveScale;

    @Override
    public void runOpMode() throws InterruptedException
    {
        initAndWaitForStart();
        selectAutoAndTeleOp();
        while (opModeIsActive())
        {
            teleOp.loop();
            BasicRobot.get().update();
            telemetry.update();
        }
    }

    @Override
    public void selectAutoAndTeleOp() {
        teleOp = new BasicTeleop(gamepad1, telemetry);
    }
}
