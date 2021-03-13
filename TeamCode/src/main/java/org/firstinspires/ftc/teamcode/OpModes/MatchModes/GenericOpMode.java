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
    private int numberOfRings = -1;
    public abstract void selectAutoAndTeleOp();
    public void initAndWaitForStart(){
        initAndWaitForStart(false, 0, 0, 0);
    }
    public void initAndWaitForStart(boolean checkRings, double scanX, double scanY, double scanH){
        handleInits();
        if (checkRings){
            checkRingsLoop(scanX,scanY,scanH);
            telemetry.addLine("Ring Loop Entered");
            telemetry.update();
        } else {
            telemetry.addLine("Wait Loop Entered");
            telemetry.update();
            waitForStart();
        }
    }
    public void handleInits(){
        deltaTime = new ElapsedTime();
        DataHub.init(telemetry,hardwareMap,gamepad1,gamepad2); //this should go before Robot.init()
        Robot.init(auto.startX, auto.startY);

        //this block relies on going after Robot.init()
        auto.init();
        auto.robot = Robot.get();
        teleOp.init();
        teleOp.robot = Robot.get();
    }
    public void makeSureRobotDoesntMoveBetweenAutoAndTeleOp(){
        if(30 < deltaTime.seconds() && deltaTime.seconds() < 31){
            Robot.get().stopAllMotors();
        }
    }
    private void checkRingsLoop(double scanX, double scanY, double scanH){
        while(!isStarted() && !isStopRequested()){
            numberOfRings = Robot.get().getRingsWithCamera(scanX, scanY, scanH);
            telemetry.addData("NumberOfRings: ",numberOfRings);
            telemetry.update();
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
    public int getNumberOfRings(){
        return numberOfRings;
    }



}
