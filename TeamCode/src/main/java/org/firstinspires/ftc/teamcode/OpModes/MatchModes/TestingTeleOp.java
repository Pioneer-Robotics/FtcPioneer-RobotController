package org.firstinspires.ftc.teamcode.OpModes.MatchModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.OpModes.Autos.DeliverWobbleGoal_ABC.ABC;
import org.firstinspires.ftc.teamcode.OpModes.Autos.NullAuto;
import org.firstinspires.ftc.teamcode.OpModes.TeleOps.ExampleTeleOp;

@TeleOp (name = "TestingTeleOp", group = "example")
public class TestingTeleOp extends GenericOpMode {
    @Override
    public void selectAutoAndTeleOp(){
        auto = new ABC(); //you need this even if you don't use it
        teleOp = new ExampleTeleOp();
    }
    @Override
    public void runOpMode() throws InterruptedException {
        selectAutoAndTeleOp();
        initAndWaitForStart();
        while(opModeIsActive()) {
            teleOp.loop();
            telemetry.update();
            Robot.get().update(true);
        }
    }
}
