package org.firstinspires.ftc.teamcode.OpModes.MatchModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.OpModes.Autos.DeliverWobbleGoal_ABC.GoToABC;
import org.firstinspires.ftc.teamcode.OpModes.TeleOps.ExampleTeleOp;

@Autonomous(name = "CoMpAuTo")
public class OnlyAuto extends GenericOpMode{
    @Override
    public void selectAutoAndTeleOp() {
        auto = new GoToABC();
        teleOp = new ExampleTeleOp();
    }

    @Override
    public void runOpMode() throws InterruptedException {
        selectAutoAndTeleOp();
        initAndWaitForStart();
        while(opModeIsActive()){
            auto.loop();
            Robot.get().update(auto.useOdos,true);
            telemetry.update();
        }
    }
}
