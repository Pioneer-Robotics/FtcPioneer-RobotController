package org.firstinspires.ftc.teamcode.OpModes.MatchModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.Hardware.BasicRobot;
import org.firstinspires.ftc.teamcode.Helpers.Toggle;

@TeleOp(name = "Basic2", group = "Linear Opmode")
public class Basic2 extends BasicGenericOpMode{
    private double driveScale = 0.5;
    double tgtPowerRight, tgtPowerLeft, drive, turn;
    Toggle goStraight = new Toggle(false);

    @Override
    public void runOpMode() throws InterruptedException {
        initAndWaitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
// use left stick to move robot
            drive = gamepad1.left_stick_y;
            turn = gamepad1.left_stick_x;
            goStraight.toggle(gamepad1.b);

            if(goStraight.getBool()){
                tgtPowerLeft = -gamepad1.left_stick_y;
                tgtPowerRight = -gamepad1.left_stick_y;
            }
            if(!goStraight.getBool()){
                // press both bumpers to get full power
                if (gamepad1.right_bumper && gamepad1.left_bumper)
                    driveScale = 1.0;
                    // press either bumper to get half power
                else if (gamepad1.left_bumper || gamepad1.right_bumper)
                    driveScale = 0.5;
                    // press neither bumper to get quarter power
                else
                    driveScale = 0.25;

                tgtPowerLeft = driveScale * turn;
                tgtPowerRight = -driveScale * turn;
                tgtPowerLeft -= driveScale * drive;
                tgtPowerRight -= driveScale * drive;
                tgtPowerLeft = Range.clip(tgtPowerLeft, -1.0,1.0);
                tgtPowerRight = Range.clip(tgtPowerRight, -1.0,1.0);
            }
            BasicRobot.get().setDrivePowers(tgtPowerLeft, tgtPowerRight);
            BasicRobot.get().update();

            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + deltaTime.toString());
            telemetry.addData("Motors", "left (%.2f), right (%.2f)", tgtPowerLeft, tgtPowerRight);
            BasicRobot.get().doOdometerTelemetry();
            telemetry.update();
        }
    }
}