package org.firstinspires.ftc.teamcode.OpModes.MatchModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.OpModes.Autos.NullAuto;
import org.firstinspires.ftc.teamcode.OpModes.TeleOps.TeleOpStandard;

//@TeleOp (name = "launcher test")
public class TestLauncher extends GenericOpMode{

    @Override
    public void selectAutoAndTeleOp() {
        teleOp = new TeleOpStandard();
        auto = new NullAuto();
    }

    @Override
    public void runOpMode() throws InterruptedException {
        selectAutoAndTeleOp();
        initAndWaitForStart();
        while(opModeIsActive()){
            telemetry.addData("elapsed time", deltaTime.seconds());
            Robot.get().setLauncherPower(gamepad1.left_trigger);
            Robot.get().update(false);
            telemetry.update();
        }
    }
}
