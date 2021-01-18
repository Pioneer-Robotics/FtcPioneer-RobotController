package org.firstinspires.ftc.teamcode.OpModes.TeleOps;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Helpers.DataHub;

public class WobbleMotors extends TeleOpScript {
    Gamepad gamepad;
    Telemetry telemetry;
    double wobblemotorticks;
    double servoPosition;
    double wobblePower;

    @Override
    public void loop() {
        if(gamepad.a){
            wobblePower = 0.05;
        }
        else if (gamepad.x){
            wobblePower = 0;
        }
        else if(gamepad.y){
            wobblePower = -0.05;
        }

        wobblemotorticks = Robot.get().wobblemotor.getTicks();

        telemetry.addData("Ticks", wobblemotorticks);

        if(gamepad.left_trigger > 0.5){
            servoPosition += 0.05;
        }

        if(gamepad.right_trigger > 0.5){
            servoPosition -= 0.05;
        }
        Robot.get().setServoPosition(servoPosition);
        Robot.get().setWobblePower(wobblePower);
        wobblemotorticks = Robot.get().getWobbleTicks();
        telemetry.addData("servo voltage", servoPosition);
        telemetry.addData("wobble ticks", wobblemotorticks);

    }

    public void init(){
        this.telemetry = DataHub.telemetry;
        this.gamepad = DataHub.gamepad1;
        wobblemotorticks = 0;
        wobblePower = 0;
        servoPosition = Robot.get().getWobbleServoPosition();
    }
}
