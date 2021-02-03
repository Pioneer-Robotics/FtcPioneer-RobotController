package org.firstinspires.ftc.teamcode.OpModes.Autos;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Helpers.bMath;

public class TestWobbleMotor extends AutoScript{
    ElapsedTime deltaTime;
    double time;
    @Override
    public void loop() {
       time = deltaTime.seconds();
       time = bMath.modIntoRange(time, 0, 10);
    }

    @Override
    public void init() {
        deltaTime = new ElapsedTime();
        startX = 0;
        startY = 0;
        time = 0;
    }
}
