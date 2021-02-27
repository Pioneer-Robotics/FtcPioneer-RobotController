package org.firstinspires.ftc.teamcode.OpModes.MatchModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.OpModes.Autos.DeliverWobbleGoal_ABC.GoToABC;
import org.firstinspires.ftc.teamcode.OpModes.Autos.TestDriveStraightPlusCheckRings;
import org.firstinspires.ftc.teamcode.OpModes.Autos.TestLasors;
import org.firstinspires.ftc.teamcode.OpModes.TeleOps.TeleOpStandard;

@Autonomous(name = "test lasors", group = "test")
public class TestAnyAutoCrap extends GenericOpMode{
    @Override
    public void selectAutoAndTeleOp() {
        auto = new TestDriveStraightPlusCheckRings();
        teleOp = new TeleOpStandard(); //not sure if this is right teleOp, good enough for now
    }

    @Override
    public void runOpMode() throws InterruptedException {
        selectAutoAndTeleOp();
        initAndWaitForStart();
        while(opModeIsActive()) {
            auto.loop();
            telemetry.update();
            Robot.get().update(auto.odos);
        }
        Robot.get().stopAllMotors();
    }
}
