package org.firstinspires.ftc.teamcode.OpModes.Autos;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Helpers.bMath;

@TeleOp (name = "wobble motor test")
public class TestWobbleMotor extends AutoScript{
    ElapsedTime deltaTime;
    double time;
    @Override
    public void loop() {
       time = deltaTime.seconds();
       time = bMath.modIntoRange(time, 0, 10);
       if(time < 5){
           Robot.get().closeWobbleServo();
           Robot.get().pointWobbleMotorUp();
       }
       else{
           Robot.get().openWobbleServo();
           Robot.get().pointWobbleMotorDown();
       }
    }

    @Override
    public void init() {
        deltaTime = new ElapsedTime();
        startX = 0;
        startY = 0;
        time = 0;
    }
}
