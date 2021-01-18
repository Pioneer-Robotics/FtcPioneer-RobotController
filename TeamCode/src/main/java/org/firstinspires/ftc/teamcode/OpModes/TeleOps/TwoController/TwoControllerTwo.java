package org.firstinspires.ftc.teamcode.OpModes.TeleOps.TwoController;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Hardware.Config;
import org.firstinspires.ftc.teamcode.Hardware.Launcher;
import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Helpers.DataHub;
import org.firstinspires.ftc.teamcode.Helpers.Toggle;
import org.firstinspires.ftc.teamcode.OpModes.TeleOps.TeleOpScript;
//comment
public class TwoControllerTwo extends TeleOpScript {
    double drive, turn, tgtPowerLeft, tgtPowerRight, driveScale;
    ElapsedTime deltaTime;
    Telemetry telemetry;
    Gamepad gamepad1;
    Gamepad gamepad2;
    Toggle moveStraightFast;
    Toggle collecting;
    Toggle launcherOff;
    Toggle spool;
    Toggle continuousFire;
    Toggle fire;
    Toggle launchOverride;
    Toggle stopCollecting;



    @Override
    public void loop() {
        drive = gamepad1.left_stick_y;
        turn = gamepad1.right_stick_x;

        launcherOff.toggle(gamepad1.x|| gamepad2.x);
        if (launcherOff.justChanged()){
            Robot.get().cancelLaunch();
        }

        Robot.get().launchOverride(gamepad2.right_trigger != 0);

        if (gamepad1.y){
            Robot.get().spool();
        }
        if (gamepad1.dpad_down || gamepad2.dpad_down){
            Robot.get().cancelSpool();
        }

        if (gamepad1.right_bumper) {
            Robot.get().fire();
        }

        collecting.toggle(gamepad1.left_bumper);
        if (collecting.getBool()){
            Robot.get().startCollecting();
        } else {
            Robot.get().stopCollecting();
        }

        stopCollecting.toggle(gamepad2.y);
        if (stopCollecting.justChanged()){
            Robot.get().stopCollecting();
        }

        if (moveStraightFast.getBool()) {
            tgtPowerLeft = -gamepad1.left_stick_y;
            tgtPowerRight = -gamepad1.left_stick_y;
        }

        if (!moveStraightFast.getBool()) {
            // press both bumpers to get full power
            if (gamepad1.b)
                driveScale = 0.65;
                // press neither bumper to get quarter power
            else
                driveScale = 0.35;
        }

        tgtPowerLeft = driveScale * turn;
        tgtPowerRight = -driveScale * turn;
        tgtPowerLeft -= driveScale * drive;
        tgtPowerRight -= driveScale * drive;
        tgtPowerLeft = Range.clip(tgtPowerLeft, -1.0, 1.0);
        tgtPowerRight = Range.clip(tgtPowerRight, -1.0, 1.0);
        Robot.get().setDrivePowers(tgtPowerLeft, tgtPowerRight);
        Robot.get().update();
//        telemetry.addData("xPos", Robot.get().getLocation().getX());
//        telemetry.addData("yPos", Robot.get().getLocation().getY());
//        telemetry.addData("rotation (deg)", Robot.get().getRotationDegrees());
//        telemetry.addData("left odo", Robot.get().getLeftOdo());
//        telemetry.addData("right odo", Robot.get().getRightOdo());
//        telemetry.addData("mid odo", Robot.get().getMidOdo());
        telemetry.addData("FastSetting On/Off", moveStraightFast.getBool());
        telemetry.addData("elapsed time: T+", deltaTime.seconds());
//        telemetry.addData("Rotation(IMU)", Robot.get().getHeading(AngleUnit.DEGREES));
        //telemetry.addData("Go straight on/off", goStraight.getBool());
        telemetry.addData("collector on/off", collecting.getBool());
        telemetry.addData("LaunchMode", Robot.get().getLaunchMode() );
        telemetry.addData("LaunchVelocity", Robot.get().getLauncherVelocity());
        telemetry.addData("ElapsedTime:", deltaTime.milliseconds());
        //telemetry.addData("LaunchVelocityDirectRef", ((DcMotorEx)DataHub.hardwareMap.get(DcMotor.class, Config.launcherMotor1)).getCurrentPosition() );
        deltaTime.reset();
    }

    @Override
    public void init() {
        deltaTime = new ElapsedTime();
        this.gamepad1 = DataHub.gamepad1;
        this.gamepad2 = DataHub.gamepad2;
        this.telemetry = DataHub.telemetry;

        moveStraightFast = new Toggle(false);
        collecting = new Toggle(false);
        launcherOff = new Toggle(false);
        spool = new Toggle(false);
        continuousFire = new Toggle(false);
        fire = new Toggle(false);
        launchOverride = new Toggle(false);
        stopCollecting = new Toggle(false);

    }
    public TwoControllerTwo(){

    }
}