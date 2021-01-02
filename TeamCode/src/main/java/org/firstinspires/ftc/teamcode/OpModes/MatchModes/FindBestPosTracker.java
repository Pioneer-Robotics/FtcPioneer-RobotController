package org.firstinspires.ftc.teamcode.OpModes.MatchModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.OpModes.Autos.ExampleAuto;
import org.firstinspires.ftc.teamcode.OpModes.Autos.NullAuto;
import org.firstinspires.ftc.teamcode.OpModes.TeleOps.OneController;
import org.firstinspires.ftc.teamcode.OpModes.TeleOps.PositionTrackerTester;
@TeleOp(name = "find best odometer", group = "tests")
public class FindBestPosTracker extends GenericOpMode {

    @Override
    public void selectAutoAndTeleOp() {
        auto = new NullAuto();
        teleOp = new OneController();
    }

    @Override
    public void runOpMode() throws InterruptedException {
        selectAutoAndTeleOp();
        initAndWaitForStart();
        while (opModeIsActive()) {
            teleOp.loop();
            Robot.get().update();
            Robot.get().doOdometerTelemetry();
            telemetry.update();
        }
    }
}

