package org.firstinspires.ftc.teamcode.OpModes.MatchModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.OpModes.Autos.AutoScript;
import org.firstinspires.ftc.teamcode.OpModes.TeleOps.TeleOpScript;

public abstract class GenericOpMode extends LinearOpMode {
    ElapsedTime deltaTime;
    AutoScript auto;
    TeleOpScript teleOp;
    public abstract void selectAutoAndTeleOp();
    public void initAndWaitForStart(){
        handelInits();
        waitForStart();
    }
    public void handelInits(){
        deltaTime = new ElapsedTime();
        Robot.init(hardwareMap, telemetry, auto.startX, auto.startY);
    }
    public void makeSureRobotDoesntMoveBetweenAutoAndTeleOp(){
        if(30 < deltaTime.seconds() && deltaTime.seconds() < 31){
            Robot.get().stopAllMotors();
        }
    }
}
