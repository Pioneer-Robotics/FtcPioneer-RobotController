package org.firstinspires.ftc.teamcode.OpModes.MatchModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Hardware.BasicRobot;
import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.OpModes.Autos.AutoScript;
import org.firstinspires.ftc.teamcode.OpModes.TeleOps.TeleOpScript;

public abstract class BasicGenericOpMode extends LinearOpMode {
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
        BasicRobot.init(hardwareMap, telemetry);
    }

}
