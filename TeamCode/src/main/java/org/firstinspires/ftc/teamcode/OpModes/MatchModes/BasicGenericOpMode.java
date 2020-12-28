package org.firstinspires.ftc.teamcode.OpModes.MatchModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Hardware.BasicRobot;

public abstract class BasicGenericOpMode extends LinearOpMode {
    ElapsedTime deltaTime;

    public void initAndWaitForStart(){
        handleInits();
        waitForStart();
        deltaTime.reset();
    }
    public void handleInits(){
        BasicRobot.init(hardwareMap, telemetry, 0, 0);
        deltaTime = new ElapsedTime();
        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }
    public void makeSureRobotDoesntMoveBetweenAutoAndTeleOp(){
        if(30 < deltaTime.seconds() && deltaTime.seconds() < 31){
            BasicRobot.get().stopAllMotors();
        }
    }
}
