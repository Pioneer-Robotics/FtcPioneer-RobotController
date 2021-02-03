package org.firstinspires.ftc.teamcode.OpModes.MatchModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.OpModes.Autos.testDriveStriaght;
import org.firstinspires.ftc.teamcode.OpModes.TeleOps.ExampleTeleOp;

@TeleOp(name = "auto pilot test")
public class OnlyAuto extends GenericOpMode{
    @Override
    public void selectAutoAndTeleOp() {
        auto = new testDriveStriaght();
        teleOp = new ExampleTeleOp();
    }

    @Override
    public void runOpMode() throws InterruptedException {
        selectAutoAndTeleOp();
        initAndWaitForStart();
        while(opModeIsActive()){
            auto.loop();
            Robot.get().update(true);
            telemetry.update();
        }
    }
}
