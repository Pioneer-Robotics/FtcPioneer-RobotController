package org.firstinspires.ftc.teamcode.OpModes.Autos;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Hardware.Robot;

public class ExampleAuto extends AutoScript {
    ElapsedTime deltaTime = new ElapsedTime();
    double someValue = 4.6587912;

    @Override
    public void main() {
        Robot.get();
        Robot.driveForward(12);
        if(deltaTime.milliseconds() < someValue){
            doTheThing();
        }
        else {
            doTheOtherThing();
        }
    }

    private void doTheThing() {
        //some code
    }
    private void doTheOtherThing() {
        //different code
    }

}
