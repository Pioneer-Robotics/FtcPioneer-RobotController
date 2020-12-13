package org.firstinspires.ftc.teamcode.OpModes.MatchModes;

import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.OpModes.Autos.ExampleAuto;
import org.firstinspires.ftc.teamcode.OpModes.TeleOps.PositionTrackerTester;

public class FindBestPosTracker extends GenericOpMode{

    @Override
    public void selectAutoAndTeleOp() {
        auto = new ExampleAuto();
        teleOp = new PositionTrackerTester(gamepad1, telemetry);
    }

    @Override
    public void runOpMode() throws InterruptedException {
        selectAutoAndTeleOp();
        initAndWaitForStart();
        while(opModeIsActive()) {
            teleOp.loop();
            Robot.get().update();
            telemetry.update();
        }
    }
}
