package org.firstinspires.ftc.teamcode.OpModes.MatchModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.OpModes.Autos.AutoScript;
import org.firstinspires.ftc.teamcode.OpModes.TeleOps.TeleOpScript;

public abstract class GenericOpMode extends LinearOpMode {
    public ElapsedTime deltaTime;
    public boolean shouldTakeDriverInput = false;
    public AutoScript auto;
    public TeleOpScript teleOp;
    public abstract void selectAutoAndTeleOp();
    public void initAndWaitForStart(){
        handelInits();
        selectAutoAndTeleOp();
        waitForStart();
    }
    public void handelInits(){
        init();
        Robot.init(hardwareMap); //TODO make sure the code actually runs like this, it might not
        shouldTakeDriverInput = false;
        deltaTime = new ElapsedTime();
        shouldTakeDriverInput = false;
    }
    public void handleShouldTakeDriverInput(){
        if(gamepad1.right_trigger > 0.5 && gamepad1.left_trigger > 0.5){
            shouldTakeDriverInput = true;
        }
        if (deltaTime.seconds() < 30){
            shouldTakeDriverInput = false;
        }
    }
    @Deprecated
    public void handleTeleOp(){
        handleShouldTakeDriverInput();
        if(shouldTakeDriverInput){
            teleOp.loop();
        }
    }
}
