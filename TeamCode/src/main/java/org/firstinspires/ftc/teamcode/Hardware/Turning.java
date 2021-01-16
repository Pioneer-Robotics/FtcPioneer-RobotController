package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Helpers.DataHub;

public class Turning{

    double anglesunit = 0.0;
    boolean autopilots = false;
    double drive, turn;
    Telemetry telemetry;
    Gamepad gamepad;

    public void loop(){
        if(!autopilots){
            drive = gamepad.right_trigger - gamepad.left_trigger;
            turn = gamepad.left_stick_x;

            if (gamepad.b){
                autopilots = true;
                turn = 0.5;
                anglesunit = Robot.get().getHeading(AngleUnit.DEGREES);
            }
        }
        if (anglesunit <= Robot.get().getHeading(AngleUnit.DEGREES)){
            turn = 0.0;
            autopilots = false;
        }
    }

    public Turning(){
        anglesunit = 0.0;
        telemetry = DataHub.telemetry;
        gamepad = DataHub.gamepad1;
        autopilots = false;
    }
}
