package org.firstinspires.ftc.teamcode.OpModes.MatchModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.OpModes.Autos.ExampleAuto;
import org.firstinspires.ftc.teamcode.OpModes.TeleOps.ExampleTeleOp;
import org.firstinspires.ftc.teamcode.OpModes.TeleOps.OneController;

@TeleOp (name = "noAuto", group = "example")
public class SkipAuto extends GenericOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        initAndWaitForStart();
        selectAutoAndTeleOp();
        while(opModeIsActive()) {
            teleOp.loop();
            Robot.get().update();
            telemetry.update();
        }
        telemetry.addData("opModeActive", opModeIsActive());
        telemetry.update();
        Robot.get().stopAllMotors();
    }
    @Override
    public void selectAutoAndTeleOp(){
        auto = new ExampleAuto(); //you need this even if you don't use it
        teleOp = new OneController(telemetry);
    }
}
