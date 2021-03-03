package org.firstinspires.ftc.teamcode.OpModes.MatchModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.OpModes.Autos.DeliverWobbleGoal_ABC.GoToABC;
import org.firstinspires.ftc.teamcode.OpModes.Autos.TestCheckRings;
import org.firstinspires.ftc.teamcode.OpModes.Autos.TestDriveStraightPlusCheckRings;
import org.firstinspires.ftc.teamcode.OpModes.Autos.TestLasors;
import org.firstinspires.ftc.teamcode.OpModes.TeleOps.ExampleTeleOp;

@Autonomous(name = "Test check rings")
public class OnlyAuto extends GenericOpMode{
    @Override
    public void selectAutoAndTeleOp() {
        auto = new TestCheckRings();
        teleOp = new ExampleTeleOp();
    }

    @Override
    public void runOpMode() throws InterruptedException {
        selectAutoAndTeleOp();
        initAndWaitForStart();
        while(opModeIsActive()){
            auto.loop();
            Robot.get().update(auto.odos);
            telemetry.update();
        }
    }
}
