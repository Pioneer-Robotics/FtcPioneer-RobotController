package org.firstinspires.ftc.teamcode.OpModes.TeleOps;

import com.qualcomm.robotcore.hardware.Gamepad;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;

public abstract class TeleOpScript {
    public Gamepad controller1;
    public Gamepad controller2;
    public abstract void main();
    public void initControllers(){
        //TODO figure out how to actually do this
    }

}
