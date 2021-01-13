package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;


import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Helpers.DataHub;
import org.firstinspires.ftc.teamcode.Helpers.Toggle;
import org.firstinspires.ftc.teamcode.OpModes.TeleOps.TeleOpScript;

public class GoStraight extends TeleOpScript {
    boolean autopilot = false;
    double targetAngle = 0.0;
    double drive, turn;
    ElapsedTime deltaTime;
    Telemetry telemetry;
    Gamepad gamepad;
    Toggle goStraight;

    double squareInputWithSign(double input){
        double output = input * input;
        if (input < 0){
            output = output * -1;
        }
        return output;
    }
    @Override
    public void loop() {

        if (autopilot) {
            if (Robot.get().getRotationDegrees() >= targetAngle + 3) {
                drive = (gamepad.right_trigger - gamepad.left_trigger);
                turn = -.5;
                //when off to the right, robot corrects itself to the right
            } else if (Robot.get().getRotationDegrees() >= targetAngle - 3) {
                drive = (gamepad.right_trigger - gamepad.left_trigger);
                turn = .5;
                //when off to the left, robot corrects itself to the right
            } else {
                drive = squareInputWithSign(gamepad.right_trigger - gamepad.left_trigger);
                turn = 0.0;
            }
        }
    }
    GoStraight(){
        boolean autopilot = false;
        double targetAngle = 0.0;
        double drive, turn, tgtPowerLeft, tgtPowerRight, driveScale;
        ElapsedTime deltaTime;
    }

    @Override
    public void init() {

    }
}