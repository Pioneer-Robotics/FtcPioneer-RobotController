package org.firstinspires.ftc.teamcode.OpModes.MatchModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.OpModes.Autos.choosingPosition;
import org.firstinspires.ftc.teamcode.OpModes.TeleOps.TeleOpStandard;

@TeleOp(name = "Test Auto", group="example")
public class TestAuto extends GenericOpMode{

    @Override
    public void selectAutoAndTeleOp() {
        teleOp = new TeleOpStandard();
        auto = new choosingPosition();
    }

    @Override
    public void runOpMode() throws InterruptedException {
        selectAutoAndTeleOp();
        initAndWaitForStart();
        while(opModeIsActive()){
            auto.loop();
            telemetry.update();
        }
    }
}
