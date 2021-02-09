package org.firstinspires.ftc.teamcode.OpModes.TeleOps;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Hardware.Config;
import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Helpers.*;

import com.qualcomm.hardware.bosch.BNO055IMU;

public class FindAccurateDistanceBetweenOdos extends TeleOpScript{
    Gamepad gamepad;
    Telemetry telemetry;
    Robot robot;
    double changeScale;
    BNO055IMU imu;
    Toggle up, down, left, right;

    @Override
    public void loop() {
        up.toggle(gamepad.dpad_up);
        down.toggle(gamepad.dpad_down);
        left.toggle(gamepad.dpad_left);
        right.toggle(gamepad.dpad_right);

        handleChangeScale();

        telemetry.addData("est distance between left and right odo",
                Config.distanceBetweenLeftAndRightOdometersCm);
        telemetry.addData("change scale", changeScale);
        telemetry.addData("est current angle (degrees)", robot.getRotationDegreesNonRegularized());
        double turn = gamepad.left_stick_x;
        robot.setDrivePowers(-turn, turn);
    }
    void handleChangeScale(){
        if(left.getBool()){
            changeScale *= 0.5;
        }
        else if(right.getBool()){
            changeScale *= 2;
        }
    }
    void adjustEstimate(){
        if(up.getBool()){
            Config.distanceFromLeftOdoToMiddleOdoCm += changeScale;
        }
        else if(down.getBool()){
            Config.distanceBetweenLeftAndRightOdometersCm -= changeScale;
        }
    }

    @Override
    public void init() {
        gamepad = DataHub.gamepad1;
        telemetry = DataHub.telemetry;
        robot = Robot.get();
        changeScale = 1;

        up = new Toggle(false);
        down = new Toggle(false);
        left = new Toggle(false);
        right = new Toggle(false);
    }
}
