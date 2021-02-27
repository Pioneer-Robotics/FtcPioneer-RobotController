package org.firstinspires.ftc.teamcode.OpModes.MatchModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.OpModes.Autos.TestDriveStriaght;
import org.firstinspires.ftc.teamcode.OpModes.TeleOps.TeleOpStandard;

@Autonomous(name = "test drive straight", group = "test")
public class TestAnyAutoCrap extends GenericOpMode{
    @Override
    public void selectAutoAndTeleOp() {
        auto = new TestDriveStriaght();
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
