package org.firstinspires.ftc.teamcode.Helpers;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * in case you need to calibrate some value on the robot, take the "value" field of this
 * and set it as the value your trying to calibtrate. Up on the dpad will increase the value & down
 * will decrease it.
 *
 * There is a field called "incrementLevel", which is the amount added/subtracted to the value when
 * you press up or down. Increase/decrease that with right/left
 */
public class ValueCalibratorHelper {
    double value;
    double incrementLevel;

    Gamepad gamepad;

    Toggle left;
    Toggle right;

    Toggle up;
    Toggle down;

    Telemetry telemetry;

    public ValueCalibratorHelper(double startValue){
        value = startValue;
        incrementLevel = 0.05;

        gamepad = DataHub.gamepad1;

        left = new Toggle(false);
        right = new Toggle(false);

        up = new Toggle(false);
        down = new Toggle(false);

        telemetry = DataHub.telemetry;
    }

    public double calibrationLoop(){
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
            value += incrementLevel;
        }
        if(down.justChanged()){
            value -= incrementLevel;
        }

        telemetry.addData("targetServoPos", value);
        telemetry.addData("increment level", incrementLevel);

        return value;
    }
}
