package org.firstinspires.ftc.teamcode.OpModes.TeleOps.SingleControllers;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Hardware.Config;
import org.firstinspires.ftc.teamcode.Helpers.DataHub;
import org.firstinspires.ftc.teamcode.Helpers.Toggle;
import org.firstinspires.ftc.teamcode.OpModes.TeleOps.TeleOpScript;

public class ServoCalibrator extends TeleOpScript {
    Servo servo;
    double targetServoPos;
    double incrementLevel;
    Gamepad gamepad;

    Toggle left;
    Toggle right;

    Toggle up;
    Toggle down;
    @Override
    public void loop() {
        left.toggle(gamepad.dpad_left);
        right.toggle(gamepad.dpad_right);

        up.toggle(gamepad.dpad_up);
        down.toggle(gamepad.dpad_down);

        if(left.justChanged()){
            incrementLevel *= 0.5;
        }
        if(right.justChanged()){
            incrementLevel *= 2;
        }
        if(up.justChanged()){
            targetServoPos += incrementLevel;
        }
        if(down.justChanged()){
            targetServoPos -= incrementLevel;
        }

        telemetry.addData("targetServoPos", targetServoPos);
        telemetry.addData("increment level", incrementLevel);

        servo.setPosition(targetServoPos);
    }

    @Override
    public void init() {
        standardInit();
        servo = DataHub.hardwareMap.get(Servo.class, Config.WobbleServo);
        gamepad = DataHub.gamepad1;
        targetServoPos = 0;
        incrementLevel = 0.0005;

        left = new Toggle(false);
        right = new Toggle(false);

        up = new Toggle(false);
        down = new Toggle(false);
    }
}
