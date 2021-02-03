package org.firstinspires.ftc.teamcode.OpModes.TeleOps.TwoController;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Hardware.GoStraight;
import org.firstinspires.ftc.teamcode.Hardware.Launcher;
import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Helpers.DataHub;
import org.firstinspires.ftc.teamcode.Helpers.Toggle;
import org.firstinspires.ftc.teamcode.Helpers.bMath;
import org.firstinspires.ftc.teamcode.OpModes.TeleOps.TeleOpScript;

//@TeleOp(name="Two_Controlller", group = "example")
public class TwoController extends OpMode {
    double anglesunit = 0.0;
    boolean Turn = false;
    boolean autopilot = false;
    double targetAngle = 0.0;
    double drive, turn, tgtPowerLeft, tgtPowerRight, driveScale;
    ElapsedTime deltaTime;
    Telemetry telemetry;
    Gamepad gamepad1;
    Gamepad gamepad2;
    DataHub DH;
    Toggle moveStraightFast;
    Toggle collecting;
    Toggle Rturn90;
    Toggle goStraight;
    Toggle Lturn90;
    Toggle Launcher;

    @Override
    public void loop() {
        Launcher.toggle(this.gamepad1.y);
        if (Launcher.getBool()){
            Robot.get().setLaunchMode(org.firstinspires.ftc.teamcode.Hardware.Launcher.LaunchMode.SPOOL);
            Robot.get().setLauncherPower(0.5);
            Robot.get().updateLauncher();
        }

        collecting.toggle(this.gamepad1.a);
        //Uses A to toggle on/off the collector
        if (collecting.getBool()){
            Robot.get().startCollecting();
        } else {
            Robot.get().stopCollecting();
        }

        goStraight.toggle(this.gamepad1.left_stick_button);
        //goStraight is useful during autonomous and driving straight
        if (goStraight.getBool()) {
        }

        Rturn90.toggle(this.gamepad1.dpad_right);
        //turn 90 is called when making a 90º right turn
        if (Rturn90.getBool()) {
            Robot.get().getHeading(AngleUnit.DEGREES);
            turn = 0.5;
            if (Robot.get().getHeading(AngleUnit.DEGREES) >= 90) {
                turn = 0;
            }
        }

        Lturn90.toggle(this.gamepad1.dpad_left);
        //Lturn 90 is called when making a 90º left turn
        if (Lturn90.getBool()){
            Robot.get().getHeading(AngleUnit.DEGREES);
            turn = -0.5;
            if (Robot.get().getHeading(AngleUnit.DEGREES) <= 270);
            turn = 0;
        }

        if (moveStraightFast.getBool()) {
            tgtPowerLeft = -gamepad1.left_stick_y;
            tgtPowerRight = -gamepad1.left_stick_y;
        }
        if (!moveStraightFast.getBool()) {
            // press both bumpers to get full power
            if (gamepad1.right_bumper && gamepad1.left_bumper)
                driveScale = 1.0;
                // press either bumper to get half power
            else if (gamepad1.left_bumper || gamepad1.right_bumper)
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

        telemetry.addData("xPos", Robot.get().getLocation().getX());
        telemetry.addData("yPos", Robot.get().getLocation().getY());
        telemetry.addData("rotation (deg)", Robot.get().getRotationDegrees());
        telemetry.addData("left odo", Robot.get().getLeftOdo());
        telemetry.addData("right odo", Robot.get().getRightOdo());
        telemetry.addData("mid odo", Robot.get().getMidOdo());
        telemetry.addData("FastSetting On/Off", moveStraightFast.getBool());
        telemetry.addData("elapsed time", deltaTime.seconds());
        telemetry.addData("Rotation(IMU)", Robot.get().getHeading(AngleUnit.DEGREES));
        telemetry.addData("Go straight on/off", goStraight);
        telemetry.addData("Target Angle", anglesunit);
        telemetry.addData("collector on/off", collecting);
    }

    @Override
    public void init() {
        DH = new DataHub();
        //DH.init(this.telemetry, this.hardwareMap,this.gamepad1, this.gamepad2);
        deltaTime = new ElapsedTime();

        moveStraightFast = new Toggle(false);
        goStraight = new Toggle(false);
        collecting = new Toggle(false);
        Rturn90 = new Toggle(false);
        Lturn90 = new Toggle(false);
        Launcher = new Toggle(false);
        //toggles for functions

        goStraight = new Toggle(false);
        gamepad1 = DataHub.gamepad1;
        gamepad2 = DataHub.gamepad2;
        telemetry = DataHub.telemetry;
        Robot.get().init(0, 0);

        Robot.get().setLaunchMode(org.firstinspires.ftc.teamcode.Hardware.Launcher.LaunchMode.IDLE);

    }
    public TwoController(){

    }
}