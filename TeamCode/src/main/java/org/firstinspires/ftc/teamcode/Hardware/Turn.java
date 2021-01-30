package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.OpModes.TeleOps.TeleOpScript;

public class Turn extends TeleOpScript {
    boolean autopilots = false;
    double turn;


    double squareInputWithSign(double input) {
        double output = input * input;
        if (input < 0) {
            output = output * -1;
        }
        return output;
    }//useful for controlling speeds

    @Override
    public void loop() {
        if (autopilots) {
            Robot.get().getHeading(AngleUnit.DEGREES);
            turn = 0.5;//turn; running through loop until greater than 90
            if (Robot.get().getHeading(AngleUnit.DEGREES) >= 90) {
                turn = 0;
                //when angle>=90, motors turn off
            }
        }

    }

    private void Turn() {
        boolean autopilots = false;
        double drive, turn, tgtPowerLeft, tgtPowerRight, driveScale;
        ElapsedTime deltaTime;
    }



    @Override
    public void init() {

    }
}