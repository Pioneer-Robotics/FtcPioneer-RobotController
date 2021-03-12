package org.firstinspires.ftc.teamcode.OpModes.MatchModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.Helpers.DataHub;
import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Helpers.bMath;
import org.firstinspires.ftc.teamcode.OpModes.Autos.AutoScript;
import org.firstinspires.ftc.teamcode.OpModes.TeleOps.TeleOpScript;

public abstract class GenericOpMode extends LinearOpMode {
    ElapsedTime deltaTime;
    AutoScript auto;
    TeleOpScript teleOp;
    public abstract void selectAutoAndTeleOp();
    public void initAndWaitForStart(){
        initAndWaitForStart(false);
    }
    public void initAndWaitForStart(boolean checkRings){
        handleInits();
        if (checkRings){

        } else {
            waitForStart();
        }
    }
    public void handleInits(){
        deltaTime = new ElapsedTime();
        DataHub.init(telemetry,hardwareMap,gamepad1,gamepad2); //this should go before Robot.init()
        Robot.init(auto.startX, auto.startY);
        auto.init();
        auto.robot = Robot.get();
        teleOp.init();
        auto.robot = Robot.get();
    }
    public void makeSureRobotDoesntMoveBetweenAutoAndTeleOp(){
        if(30 < deltaTime.seconds() && deltaTime.seconds() < 31){
            Robot.get().stopAllMotors();
        }
    }
    private void checkRingsLoop(){
        while(!isStarted() && !isStopRequested()){
            Robot.get().getRingsWithCamera(0.5,0.5,0.1);
        }
    }
    public void runStandardLoop(){
        telemetry.addData("elapsed time", deltaTime.seconds());
        if(deltaTime.seconds() < 30.0){
            telemetry.addLine("autonomous mode");
            auto.loop();
        }
        else{
            Robot.get().allowMovement();
            telemetry.addLine("teleOp mode");
            teleOp.loop();
        }
        makeSureRobotDoesntMoveBetweenAutoAndTeleOp();
        Robot.get().update(true);
        telemetry.update();
    }



}
