package org.firstinspires.ftc.teamcode.OpModes.Autos;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Hardware.Robot;

public class ExampleAuto extends AutoScript {
    ElapsedTime deltaTime = new ElapsedTime();
    double someValue = 4.6587912;

    @Override
    public void loop() {
        Robot.get().setDrivePowers(12, 12);
        doTheThing();
        doTheOtherThing();
    }

    private void doTheThing() {
        //some code
    }
    private void doTheOtherThing() {
        //different code
    }

}
