package org.firstinspires.ftc.teamcode.OpModes.MatchModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.OpModes.Autos.*;
import org.firstinspires.ftc.teamcode.OpModes.TeleOps.*;

//@TeleOp(name = "test auto pilot", group = "example")
public class ExampleMatchMode extends GenericOpMode {
    @Override
    //this is the method you change when you make new MatchModes. Change "ExampleAuto" and
    //"ExampleTeleOp" to the specific autonomous and teleOp programs you want.
    public void selectAutoAndTeleOp(){
        auto = new NullAuto();
        teleOp = new TeleOpStandard();
    }
    @Override
    public void runOpMode() throws InterruptedException {
        selectAutoAndTeleOp();
        initAndWaitForStart();
        while(opModeIsActive()) {
            runStandardLoop();
        }
        Robot.get().stopAllMotors();
    }
}
