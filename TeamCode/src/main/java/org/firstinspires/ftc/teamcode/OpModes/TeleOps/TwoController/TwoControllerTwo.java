package org.firstinspires.ftc.teamcode.OpModes.TeleOps.TwoController;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Hardware.Config;
import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Helpers.DataHub;
import org.firstinspires.ftc.teamcode.Helpers.Toggle;
import org.firstinspires.ftc.teamcode.OpModes.TeleOps.TeleOpScript;
//comment
public class TwoControllerTwo extends TeleOpScript {
    private static float TRIGGER_DEADZONE = 0.1f;

    double drive, turn, tgtPowerLeft, tgtPowerRight, driveScale, turnScale;
    ElapsedTime deltaTime;
    ElapsedTime loopTime;
    Telemetry telemetry;
    Gamepad gamepad1;
    Gamepad gamepad2;
    Toggle collecting;
    Toggle stopCollecting;
    Toggle increaseLaunchSpeedToggle;
    Toggle decreaseLaunchSpeedToggle;


    double launcherSpeedFraction = 0.8;



    @Override
    public void loop() {


        /*============
        Launcher Control
         ============*/
        //overrides the launch conditions to initiate the launch asap
        Robot.get().launchOverride(gamepad2.a);

        //spools without initiating a launch
//        if (gamepad1.y || gamepad2.y){
//            Robot.get().spool();
//        }

        //sets the launchmode to IDLE; should be used if there is an issue and a launch cannot be completed
        if (gamepad1.dpad_down || gamepad2.dpad_down){
            Robot.get().emergencyStop();
        }

        //fires
        if (gamepad1.a) {
            Robot.get().fire();
        }

        decreaseLaunchSpeedToggle.toggle(gamepad2.dpad_left);
        increaseLaunchSpeedToggle.toggle(gamepad2.dpad_right);
        if (decreaseLaunchSpeedToggle.justChanged()){
            launcherSpeedFraction += 0.1;
        } else if (increaseLaunchSpeedToggle.justChanged()){
            launcherSpeedFraction -= 0.1;
        }
        launcherSpeedFraction = Range.clip(launcherSpeedFraction,0.1,1);
        Robot.get().setLauncherTargetVelocity(Config.maxLauncherSpeed*launcherSpeedFraction);

        /*============
        Collector Control
         ============*/

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

        /*============
        Drive Control
         ============*/
        if (gamepad1.right_trigger >= TRIGGER_DEADZONE || gamepad1.left_trigger >= TRIGGER_DEADZONE) {
            drive = gamepad1.left_trigger-gamepad1.right_trigger;
        } else {
            drive = 0;
        }

        turn = gamepad1.left_stick_x;

        if (gamepad1.right_bumper && gamepad1.left_bumper){
            driveScale = 1;
            turnScale = 1;
        } else if (gamepad1.left_bumper || gamepad1.right_bumper){
            driveScale = 0.7;
            turnScale = 0.7;
        } else {
            driveScale = 0.4;
        }



        tgtPowerLeft = turnScale * turn;
        tgtPowerRight = -turnScale * turn;
        tgtPowerLeft -= driveScale * drive;
        tgtPowerRight -= driveScale * drive;
        tgtPowerLeft = Range.clip(tgtPowerLeft, -1.0, 1.0);
        tgtPowerRight = Range.clip(tgtPowerRight, -1.0, 1.0);
        Robot.get().setDrivePowers(tgtPowerLeft, tgtPowerRight);
        Robot.get().update();

        /*============
        Telemetry
         ============*/
//        telemetry.addData("xPos", Robot.get().getLocation().getX());
//        telemetry.addData("yPos", Robot.get().getLocation().getY());
//        telemetry.addData("rotation (deg)", Robot.get().getRotationDegrees());
//        telemetry.addData("left odo", Robot.get().getLeftOdo());
//        telemetry.addData("right odo", Robot.get().getRightOdo());
//        telemetry.addData("mid odo", Robot.get().getMidOdo());
        telemetry.addLine("========\uD83D\uDD28===========\uD83D\uDD28=====");
        telemetry.addLine("MR.SWANKHAMMER CONTROL PANEL");
        telemetry.addLine("============================");
        telemetry.addData("Mission Time: T+", deltaTime.seconds());
        telemetry.addData("Loop Time:", loopTime.milliseconds());
//        telemetry.addData("Rotation(IMU)", Robot.get().getHeading(AngleUnit.DEGREES));
        //telemetry.addData("Go straight on/off", goStraight.getBool());

        telemetry.addLine("====LAUNCHER====");
        telemetry.addData("Mode", Robot.get().getLaunchMode() );
        telemetry.addData("Current Velocity", Robot.get().getLauncherVelocity());
        telemetry.addData("Target Velocity", Config.maxLauncherSpeed*launcherSpeedFraction);
        telemetry.addLine("====DRIVE====");

        telemetry.addLine("====COLLECTOR====");
        telemetry.addData("collector on/off", collecting.getBool());

        //telemetry.addData("LaunchVelocityDirectRef", ((DcMotorEx)DataHub.hardwareMap.get(DcMotor.class, Config.launcherMotor1)).getCurrentPosition() );
        loopTime.reset();
    }

    @Override
    public void init() {
        deltaTime = new ElapsedTime();
        loopTime = new ElapsedTime();
        this.gamepad1 = DataHub.gamepad1;
        this.gamepad2 = DataHub.gamepad2;
        this.telemetry = DataHub.telemetry;
        driveScale = 0.5;
        turnScale = 0.5;

        collecting = new Toggle(false);
        stopCollecting = new Toggle(false);
        increaseLaunchSpeedToggle = new Toggle(false);
        decreaseLaunchSpeedToggle = new Toggle(false);

    }
    public TwoControllerTwo(){

    }
}