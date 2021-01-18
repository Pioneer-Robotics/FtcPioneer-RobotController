package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Helpers.DataHub;

public class WobbleMotor {
    public DcMotor motor;
    public Servo servo;
    public int getTicks(){
        return motor.getCurrentPosition();
    }
    public WobbleMotor(){
      motor = DataHub.hardwareMap.get(DcMotor.class, Config.WobbleMotor);
      motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
      servo = DataHub.hardwareMap.get(Servo.class, Config.WobbleServo);
    }

    //motor
    void setWobblePower(double power) {
        motor.setPower(power);
    }
    double getWobblePower(){
        return motor.getPower();
    }

    //servo
    void setServoPosition(double position){
        servo.setPosition(position);
    }
    double getServoPosition(){
        return servo.getPosition();
    }

}