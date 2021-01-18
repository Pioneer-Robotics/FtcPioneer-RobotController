package org.firstinspires.ftc.teamcode.OpModes.MatchModes;

import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.OpModes.Autos.TestWobbleMotor;
import org.firstinspires.ftc.teamcode.OpModes.TeleOps.ExampleTeleOp;

public class OnlyAuto extends GenericOpMode{
    @Override
    public void selectAutoAndTeleOp() {
        auto = new TestWobbleMotor();
        teleOp = new ExampleTeleOp();
    }

    @Override
    public void runOpMode() throws InterruptedException {
        selectAutoAndTeleOp();
        initAndWaitForStart();
        while(opModeIsActive()){
            auto.loop();
            Robot.get().update();
            telemetry.update();
        }
    }
}
