package org.firstinspires.ftc.teamcode.OpModes.MatchModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.OpModes.Autos.ExampleAuto;
import org.firstinspires.ftc.teamcode.OpModes.Autos.NullAuto;
import org.firstinspires.ftc.teamcode.OpModes.TeleOps.OneController;

@TeleOp (name = "SkipAuto", group = "example")
public class SkipAuto extends GenericOpMode {
    @Override
    public void selectAutoAndTeleOp(){
        auto = new NullAuto(); //you need this even if you don't use it
        teleOp = new OneController(gamepad1, telemetry);
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
