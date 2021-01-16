package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;


import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.OpModes.TeleOps.TeleOpScript;

public class GoStraight extends TeleOpScript {
    boolean autopilot = false;
    double targetAngle = 0.0;
    double drive, turn;
    ElapsedTime deltaTime;
    Telemetry telemetry;
    Gamepad gamepad;

    double squareInputWithSign(double input){
        double output = input * input;
        if (input < 0){
            output = output * -1;
        }
        return output;
    }
    @Override
    public void loop() {


    }
    GoStraight(){
        boolean autopilot = false;
        double targetAngle = 0.0;
        double drive, turn, tgtPowerLeft, tgtPowerRight, driveScale;
    }
    public GoStraight StraightStraight(){
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
        return this;
    }
    /*
    //if (!autopilot && !autopilots) {
        //drive = gamepad1.right_trigger - gamepad1.left_trigger;
        //turn = gamepad1.left_stick_x;

        //if (gamepad1.x) {
            //autopilot = true;
            //drive = gamepad1.right_trigger - gamepad1.left_trigger;
            //targetAngle = Robot.get().getRotationDegrees();
        //}
    //}
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
     */

    @Override
    public void init() {

    }
}