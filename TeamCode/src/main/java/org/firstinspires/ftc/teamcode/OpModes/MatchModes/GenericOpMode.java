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
        handleInits();
        waitForStart();
    }
    public void handleInits(){
        deltaTime = new ElapsedTime();
        DataHub.init(telemetry,hardwareMap,gamepad1,gamepad2); //this should go before Robot.init()
        Robot.init(auto.startX, auto.startY);
        auto.init();
        teleOp.init();
    }
    public void makeSureRobotDoesntMoveBetweenAutoAndTeleOp(){
        if(30 < deltaTime.seconds() && deltaTime.seconds() < 31){
            Robot.get().stopAllMotors();
        }
    }
    public void runStandardLoop(){
        telemetry.addData("elapsed time", deltaTime.seconds());
        if(deltaTime.seconds() < 30.0){
            telemetry.addLine("autonomous mode");
            auto.loop();
        }
        else{
            telemetry.addLine("teleOp mode");
            teleOp.loop();
        }
        makeSureRobotDoesntMoveBetweenAutoAndTeleOp();
        Robot.get().update(false);
        telemetry.update();
    }

    void goStraight(double drive){
        double turn = 0;
        double targetAngle = Robot.get().getRotationDegrees();
        if(Robot.get().getRotationDegrees() >= targetAngle + 3){
            turn = -.5;
        }
        else if(Robot.get().getRotationDegrees() >= targetAngle - 3){
            turn = .5;
        }
        else {
            drive = bMath.squareInputWithSign(drive);
            turn = 0.0;
        }
        double tgtPowerLeft = turn;
        double tgtPowerRight = -turn;
        tgtPowerLeft -= drive;
        tgtPowerRight -= drive;
        tgtPowerLeft = Range.clip(tgtPowerLeft, -1.0,1.0);
        tgtPowerRight = Range.clip(tgtPowerRight, -1.0,1.0);
        Robot.get().setDrivePowers(tgtPowerLeft,tgtPowerRight);
    }
}
