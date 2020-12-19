package org.firstinspires.ftc.teamcode.OpModes.Autos;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Hardware.Robot;

public class NullAuto extends AutoScript {
    ElapsedTime deltaTime = new ElapsedTime();
    double startX = 0.0;
    double startY = 0.0;

    @Override
    public void loop() {
        Robot.get().setDrivePowers(0, 0);
    }

}
