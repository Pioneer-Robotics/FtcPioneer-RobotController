package org.firstinspires.ftc.teamcode.OpModes.Autos;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Hardware.Robot;

@Deprecated
public class ExampleAuto extends AutoScript {
    ElapsedTime deltaTime;

    @Override
    public void loop() {
        Robot.get().setDrivePowers(0, 0);
        doTheThing();
        doTheOtherThing();
    }

    @Override
    public void init() {
        deltaTime = new ElapsedTime();
        startX = 0.0;
        startY = 0.0;
    }

    private void doTheThing() {
        //some code
    }
    private void doTheOtherThing() {
        //different code
    }

}
