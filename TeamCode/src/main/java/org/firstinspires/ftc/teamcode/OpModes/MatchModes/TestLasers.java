package org.firstinspires.ftc.teamcode.OpModes.MatchModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Hardware.Robot;

import org.firstinspires.ftc.teamcode.OpModes.Autos.DeliverWobbleGoal_ABC.DriveSuperStragiht;
import org.firstinspires.ftc.teamcode.OpModes.Autos.TestCheckRings;
import org.firstinspires.ftc.teamcode.OpModes.TeleOps.TeleOpStandard;


//IMPORTANT!!!
//NO ONE SKREW WITH THIS, WE USE IT A LOT!!!
@Autonomous(name = "test check rings", group = "test")
public class TestLasers extends GenericOpMode{
    @Override
    public void selectAutoAndTeleOp() {

        auto = new DriveSuperStragiht();//TODO auto = new TestLasors(). DO NOT CHANGE!!!
        teleOp = new TeleOpStandard(); //not sure if this is right teleOp, good enough for now
    }

    @Override
    public void runOpMode() throws InterruptedException {
        selectAutoAndTeleOp();
        initAndWaitForStart();
        while(opModeIsActive()) {
            auto.loop();
            telemetry.update();
            Robot.get().update(auto.useOdos);
        }
        Robot.get().stopAllMotors();
    }
}
