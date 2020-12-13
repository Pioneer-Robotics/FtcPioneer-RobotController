package org.firstinspires.ftc.teamcode.OpModes.TeleOps.SingleControllers;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Helpers.Toggle;

public class DriverStandard extends DriverProgramScript{
    double drive, turn, tgtPowerLeft, tgtPowerRight, driveScale;
    ElapsedTime deltaTime = new ElapsedTime();
    Telemetry telemetry;
    Gamepad gamepad;
    Toggle goStraight = new Toggle(false);

    @Override
    public void loop() {
        // use left stick to move robot
        drive = gamepad.left_stick_y;
        turn = gamepad.left_stick_x;
        goStraight.toggle(gamepad.b);

        if(goStraight.getBool()){
            tgtPowerLeft = -gamepad.left_stick_y;
            tgtPowerRight = -gamepad.left_stick_y;
        }
        if(!goStraight.getBool()){
            // press both bumpers to get full power
            if (gamepad.right_bumper && gamepad.left_bumper)
                driveScale = 1.0;
                // press either bumper to get half power
            else if (gamepad.left_bumper || gamepad.right_bumper)
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
        Robot.get().setDrivePowers(tgtPowerLeft,tgtPowerRight);
    }
    public DriverStandard(Gamepad gamepad, Telemetry telemetry){
        this.gamepad = gamepad;
        this.telemetry = telemetry;
    }
}
