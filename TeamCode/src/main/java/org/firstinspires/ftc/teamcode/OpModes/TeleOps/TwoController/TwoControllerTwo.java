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
    //DEBUG CONFIG:
    private static final boolean DEBUG = false; //Enable Debug
    private static final double DEBUG_CHANGE_BIG = 0.5; //large debug change
    private static final double DEBUG_CHANGE_SMALL = 0.05; //small debug change
    private double debugValUp = 0; //initial value for debug Up/down
    private double debugValLeft = 65.00; //initial value for debug Left/Right

    private boolean doDebugOptions;
    private Toggle debugValUpChanged;
    private Toggle debugValLeftChanged;



    private static final float TRIGGER_DEADZONE = 0.1f;
    private static final float STICK_DEADZONE = 0.1f;

    private Robot robot;

    double drive, turn, tgtPowerLeft, tgtPowerRight, driveScale, turnScale;
    ElapsedTime deltaTime;
    ElapsedTime loopTime;
    Telemetry telemetry;
    Gamepad gamepad1;
    Gamepad gamepad2;
    Toggle collectorRetracted;
    Toggle increaseLaunchSpeedToggle;
    Toggle decreaseLaunchSpeedToggle;


    double launcherSpeedFraction = 0.70;
    String collectorState = "null";
    Toggle collecting;


    @Override
    public void loop() {
        /*============
        Debugging and Tuning
         ============*/
        doDebugOptions = DEBUG && gamepad2.right_bumper;
        if (doDebugOptions){
            double debug_change = !gamepad2.left_bumper ? DEBUG_CHANGE_BIG : DEBUG_CHANGE_SMALL;
            //Whatever you are debugging goes here
            debugValUpChanged.toggle(gamepad2.dpad_up || gamepad2.dpad_down);
            if (debugValUpChanged.justChanged()) {
                if (gamepad2.dpad_up) {debugValUp += debug_change;} else {debugValUp -= debug_change;}
                robot.updateSettings();
            }
            debugValLeftChanged.toggle(gamepad2.dpad_left || gamepad2.dpad_right);
            if (debugValLeftChanged.justChanged()) {
                if (gamepad2.dpad_left) {debugValLeft += debug_change;} else {debugValLeft -= debug_change;}
                robot.updateSettings();
            }


            Config.launcherPIDF.d = debugValUp;
            Config.launcherPIDF.p = debugValLeft;
            //Config.launcherPIDF.f = 12.95;


            telemetry.addLine("--CONTROLLER DEBUG MODE--");
            telemetry.addData("UpDown: ",debugValUp);
            telemetry.addData("LeftRight: ",debugValLeft);
        }

        /*============
        Launcher Control
         ============*/

        //overrides the launch conditions to initiate the launch asap; should be used if there is
            // an issue and a launch cannot be completed automatically
        robot.launchOverride(gamepad2.a);

        //spools without initiating a launch
        if (gamepad1.y || gamepad2.y){
            robot.spool();
        }

        //sets the launchmode to IDLE; should be used to cancel a launch
        if (gamepad1.dpad_down || (gamepad2.dpad_down && !doDebugOptions)){
            robot.emergencyStop();
        }

        //fires
        if (gamepad1.a) {
            robot.fire();
        }

        decreaseLaunchSpeedToggle.toggle(gamepad2.dpad_left && !doDebugOptions);
        increaseLaunchSpeedToggle.toggle(gamepad2.dpad_right && !doDebugOptions);
        if (decreaseLaunchSpeedToggle.justChanged()){
            launcherSpeedFraction += 0.025;
        } else if (increaseLaunchSpeedToggle.justChanged()){
            launcherSpeedFraction -= 0.025;
        }
        launcherSpeedFraction = Range.clip(launcherSpeedFraction,0.1,1);
        robot.setLauncherTargetVelocity(Config.maxLauncherSpeed*launcherSpeedFraction);

        /*============
        Collector Control
         ============*/

        collecting.toggle(gamepad1.x);
        collectorRetracted.toggle(gamepad1.b);
        if (gamepad1.right_stick_y >= STICK_DEADZONE){
            collecting.set(false);
        }

        if (collectorRetracted.getBool()){
            robot.retractCollector();
            collectorState = "retracted";
        } else if(collecting.getBool()){
            robot.startCollecting();
            robot.setCollectorSpeed(0.6f);
            collectorState = "collecting";
        } else if(gamepad1.right_stick_y >= STICK_DEADZONE){
            robot.startCollecting();
            robot.setCollectorSpeed(-gamepad1.right_stick_y * Config.COLLECTOR_MAX_SPEED_FRACTION);
            collectorState = "backdriving";
        } else {
            robot.stopCollecting();
            collectorState = "stopped";
        }

        /*============
        Wobble Goal
         ============*/


        if (Math.abs(gamepad2.left_stick_y)>=STICK_DEADZONE ){
            robot.setWobbleMotorPower(gamepad2.left_stick_y*0.75);
        } else {
            robot.setWobbleMotorPower(0);
        }

        robot.setWobbleServoPosition(gamepad2.x);



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
            driveScale = 0.75;
            turnScale = 0.75;
        } else {
            driveScale = 0.4;
            turnScale = 0.55;
        }


        tgtPowerLeft = turnScale * turn;
        tgtPowerRight = -turnScale * turn;
        tgtPowerLeft -= driveScale * drive;
        tgtPowerRight -= driveScale * drive;
        tgtPowerLeft = Range.clip(tgtPowerLeft, -1.0, 1.0);
        tgtPowerRight = Range.clip(tgtPowerRight, -1.0, 1.0);
        robot.setDrivePowers(tgtPowerLeft, tgtPowerRight);



        /*============
        Telemetry
         ============*/
//        telemetry.addData("xPos", robot.getLocation().getX());
//        telemetry.addData("yPos", robot.getLocation().getY());
//        telemetry.addData("rotation (deg)", robot.getRotationDegrees());
//        telemetry.addData("left odo", robot.getLeftOdo());
//        telemetry.addData("right odo", robot.getRightOdo());
//        telemetry.addData("mid odo", robot.getMidOdo());
        telemetry.addData("Amount Of Rings", Robot.get().amountOfRings());
        telemetry.addData("Target Velocity", Config.maxLauncherSpeed*launcherSpeedFraction);
        telemetry.addLine("========\uD83D\uDD28===========\uD83D\uDD28=====");
        telemetry.addLine("MR.SWANKHAMMER CONTROL PANEL");
        telemetry.addLine("============================");
        telemetry.addData("Mission Time: T+", deltaTime.seconds());
        telemetry.addData("Loop Time:", loopTime.milliseconds());


        telemetry.addLine("====LAUNCHER====");
        telemetry.addData("Mode", robot.getLaunchMode() );
        telemetry.addData("Current Velocity", robot.getLauncherVelocity());

        telemetry.addLine("=====DRIVE =====");
        telemetry.addData("TurnScale", turnScale);
        telemetry.addData("DriveScale", driveScale);
        telemetry.addLine("===COLLECTOR ===");
        telemetry.addData("collector", collectorState);

        loopTime.reset();



        robot.update(false);
    }

    @Override
    public void init() {
        robot = Robot.get();

        deltaTime = new ElapsedTime();
        loopTime = new ElapsedTime();
        this.gamepad1 = DataHub.gamepad1;
        this.gamepad2 = DataHub.gamepad2;
        this.telemetry = DataHub.telemetry;
        driveScale = 0.5;
        turnScale = 0.5;

        collectorRetracted = new Toggle(true);
        increaseLaunchSpeedToggle = new Toggle(false);
        decreaseLaunchSpeedToggle = new Toggle(false);
        collecting = new Toggle(false);

        doDebugOptions = false;
        debugValLeftChanged = new Toggle(false);
        debugValUpChanged = new Toggle(false);

    }
    public TwoControllerTwo(){

    }
}