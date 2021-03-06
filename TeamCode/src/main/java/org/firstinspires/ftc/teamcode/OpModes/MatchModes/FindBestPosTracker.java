package org.firstinspires.ftc.teamcode.OpModes.MatchModes;

import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.OpModes.Autos.NullAuto;
import org.firstinspires.ftc.teamcode.OpModes.TeleOps.TeleOpStandard;

//@TeleOp(name = "find best odometer", group = "tests")
public class FindBestPosTracker extends GenericOpMode {

    @Override
    public void selectAutoAndTeleOp() {
        auto = new NullAuto();
        teleOp = new TeleOpStandard();
    }

    @Override
    public void runOpMode() throws InterruptedException {
        selectAutoAndTeleOp();
        initAndWaitForStart();
        while (opModeIsActive()) {
            teleOp.loop();
            Robot.get().update(true);
            Robot.get().doOdometerTelemetry();
            telemetry.update();
        }
    }
}

