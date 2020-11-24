package org.firstinspires.ftc.teamcode.OpModes.MatchModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
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
        init();
        //TODO make sure the code actually runs like this, it might not (hardwareMap stuff)
        Robot.init(hardwareMap, auto.startX, auto.startY);
        deltaTime = new ElapsedTime();
    }
    public void makeSureRobotDoesntMoveBetweenAutoAndTeleOp(){
        if(30 < deltaTime.seconds() && deltaTime.seconds() < 31){
            Robot.get().stopAllMotors();
        }
    }


}
