package org.firstinspires.ftc.teamcode.OpModes.TeleOps.SingleControllers;


import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Hardware.Robot;

//this class is to manage what the Robot is supposed to do based on one controller at a time
public abstract class DriverProgramScript {
    public abstract void loop();
    void basicTankControlls(Gamepad gamepad, whichStick stick){
        double drive = 0;
        double turn = 0;
        if(stick == whichStick.LEFT){
            drive = -gamepad.left_stick_y;
            turn = gamepad.left_stick_x;
        }
        if(stick == whichStick.RIGHT){
            drive = -gamepad.right_stick_y;
            turn = gamepad.right_stick_x;
        }
        Robot.get().setDrivePowers(drive + turn, drive - turn);
    }
    enum whichStick{
        LEFT,
        RIGHT
    }
}
