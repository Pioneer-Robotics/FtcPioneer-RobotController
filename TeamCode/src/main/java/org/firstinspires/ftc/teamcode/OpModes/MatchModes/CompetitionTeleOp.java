package org.firstinspires.ftc.teamcode.OpModes.MatchModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.OpModes.Autos.NullAuto;
import org.firstinspires.ftc.teamcode.OpModes.TeleOps.ExampleTeleOp;
import org.firstinspires.ftc.teamcode.OpModes.TeleOps.TwoController.TwoControllerTwo;

@TeleOp (name = "CompTeleOp", group = "example")
public class CompetitionTeleOp extends GenericOpMode {
    @Override
    public void selectAutoAndTeleOp(){
        auto = new NullAuto(); //you need this even if you don't use it
        teleOp = new TwoControllerTwo();
    }
    @Override
    public void runOpMode() throws InterruptedException {
        selectAutoAndTeleOp();
        initAndWaitForStart();
        while(opModeIsActive()) {
            teleOp.loop();
            telemetry.update();
            Robot.get().update(false);
        }
    }
}
