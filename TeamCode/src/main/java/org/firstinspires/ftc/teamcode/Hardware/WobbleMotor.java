package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Helpers.DataHub;

public class WobbleMotor {
    public DcMotor wobblemotor;
    public Servo wobbleservo;
    public int getTicks(){
        return wobblemotor.getCurrentPosition();
    }
    public WobbleMotor(){
      wobblemotor = wobblemotor = DataHub.hardwareMap.get(DcMotor.class, Config.WobbleMotor);
       wobblemotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
       wobblemotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public double getWobblePower(){
        return wobblemotor.getPower();
    }
    void setWobblePower(double power) {
        wobblemotor.setPower(power);
    }
   void servoPosition(double position){
    wobbleservo.setPosition(position);
   }
}