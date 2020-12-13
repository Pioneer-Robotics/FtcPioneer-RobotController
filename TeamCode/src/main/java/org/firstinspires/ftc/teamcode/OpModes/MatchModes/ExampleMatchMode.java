package org.firstinspires.ftc.teamcode.OpModes.MatchModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.OpModes.Autos.*;
import org.firstinspires.ftc.teamcode.OpModes.TeleOps.*;

@Autonomous (name = "example", group = "example")
public class ExampleMatchMode extends GenericOpMode {
    @Override
    //this is the method you change when you make new MatchModes. Change "ExampleAuto" and
    //"ExampleTeleOp" to the specific autonomous and teleOp programs you want.
    public void selectAutoAndTeleOp(){
        auto = new ExampleAuto();
        teleOp = new ExampleTeleOp();
    }
    @Override
    public void runOpMode() throws InterruptedException {
        selectAutoAndTeleOp();
        initAndWaitForStart();
        while(opModeIsActive()) {
            if(deltaTime.seconds() < 30.0){
                auto.loop();
            }
            else{
                teleOp.loop();
            }
            makeSureRobotDoesntMoveBetweenAutoAndTeleOp();
            Robot.get().update();
            telemetry.update();
        }
        Robot.get().stopAllMotors();
    }
}
