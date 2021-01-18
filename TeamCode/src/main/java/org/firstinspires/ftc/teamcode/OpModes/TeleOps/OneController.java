package org.firstinspires.ftc.teamcode.OpModes.TeleOps;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Helpers.*;

public class OneController extends TeleOpScript {
    double anglesunit = 0.0;
    boolean autopilots = false;
    boolean Turn = false;
    boolean autopilot = false;
    double targetAngle = 0.0;
    double drive, turn, tgtPowerLeft, tgtPowerRight, driveScale;
    double wobbleSpeed;
    ElapsedTime deltaTime;
    Telemetry telemetry;
    Gamepad gamepad;
    Toggle goStraight;
    WobbleMotors wobblemotors;

    double getWobbleSpeed(){
        wobbleSpeed = Robot.get().getWobblePower();
        return wobbleSpeed;
    }

    double squareInputWithSign(double input) {
        double output = input * input;
        if (input < 0) {
            output = output * -1;
        }
        return output;
    }

    @Override
    public void loop() {

        if (!autopilot && !autopilots) {
            drive = gamepad.right_trigger - gamepad.left_trigger;
            turn = gamepad.left_stick_x;

            if (gamepad.a) {
                autopilot = true;
                drive = gamepad.right_trigger - gamepad.left_trigger;
                targetAngle = Robot.get().getRotationDegrees();
            }
        }
        if (gamepad.y) {
            autopilot = false;
            autopilots = false;
        }
        if (autopilot) {
            if (Robot.get().getRotationDegrees() >= targetAngle + 3) {
                drive = (gamepad.right_trigger - gamepad.left_trigger);
                turn = -.5;
            } else if (Robot.get().getRotationDegrees() >= targetAngle - 3) {
                drive = (gamepad.right_trigger - gamepad.left_trigger);
                turn = .5;
            } else {
                drive = squareInputWithSign(gamepad.right_trigger - gamepad.left_trigger);
                turn = 0.0;
            }
        }
        if (goStraight.getBool()) {
            tgtPowerLeft = -gamepad.left_stick_y;
            tgtPowerRight = -gamepad.left_stick_y;
        }
        if (!goStraight.getBool()) {
            // press both bumpers to get full power
            if (gamepad.right_bumper && gamepad.left_bumper)
                driveScale = 1.0;
                // press either bumper to get half power
            else if (gamepad.left_bumper || gamepad.right_bumper)
                driveScale = 0.5;
                // press neither bumper to get quarter power
            else
                driveScale = 0.25;
        }


        tgtPowerLeft = driveScale * turn;
        tgtPowerRight = -driveScale * turn;
        tgtPowerLeft -= driveScale * drive;
        tgtPowerRight -= driveScale * drive;
        tgtPowerLeft = Range.clip(tgtPowerLeft, -1.0, 1.0);
        tgtPowerRight = Range.clip(tgtPowerRight, -1.0, 1.0);

        Robot.get().setDrivePowers(tgtPowerLeft, tgtPowerRight);

        telemetry.addData("wobble motor position", wobblemotors.wobblemotorticks);
        telemetry.addData("wobble motor speed", wobbleSpeed);
        telemetry.addData("xPos", Robot.get().getLocation().getX());
        telemetry.addData("yPos", Robot.get().getLocation().getY());
        telemetry.addData("rotation (deg)", Robot.get().getRotationDegrees());
        telemetry.addData("left odo", Robot.get().getLeftOdo());
        telemetry.addData("right odo", Robot.get().getRightOdo());
        telemetry.addData("mid odo", Robot.get().getMidOdo());
        telemetry.addData("go strait setting", goStraight.getBool());
        telemetry.addData("elapsed time", deltaTime.seconds());
        telemetry.addData("Is autopilot on", autopilot);
        telemetry.addData("Rotation(IMU)", Robot.get().getHeading(AngleUnit.DEGREES));
        telemetry.addData("Is second Autopilot on", autopilots);
        telemetry.addData("Target Angle", anglesunit);

        if (autopilots) {
            Robot.get().getHeading(AngleUnit.DEGREES);
            turn = 0.5;
            if (Robot.get().getHeading(AngleUnit.DEGREES) >= 90) {
                turn = 0;
            }
        }

        if ((turn > 0 || turn < 0) && !autopilots) {
            Turn = true;
        } else {
            Turn = false;
        }
        if (Turn && turn > 0) {
            turn = 0.35;
        } else if (Turn && turn < 0) {
            turn = -0.35;
        }

        if(gamepad.dpad_up){
            Robot.get().pointWobbleMotorUp();
        }
        if (gamepad.dpad_down){
            Robot.get().pointWobbleMotorDown();
        }
        if (gamepad.x){
            Robot.get().setWobbleMotorToStart();
        }


    }

    @Override
    public void init() {
        wobblemotors = new WobbleMotors();
        deltaTime = new ElapsedTime();
        goStraight = new Toggle(false);
        this.gamepad = DataHub.gamepad1;
        this.telemetry = DataHub.telemetry;

    }

    public OneController(){

    }
}