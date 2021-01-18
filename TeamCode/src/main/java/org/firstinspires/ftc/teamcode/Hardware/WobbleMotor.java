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

      while(motor.getCurrentPosition() != 0){ //TODO get rid of this (after it always starts at 0)
          //this whole while loop is here to check that the motor starts at 0 ticks
          DataHub.telemetry.addLine("wobble motor is being uncooperative (Joe knows what this means)");
          DataHub.telemetry.addData("wobble motor ticks", motor.getCurrentPosition());
//          motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
          if(DataHub.gamepad1.x){ //this is here to escape in case it simply won't
              break;
          }
      }

      motor.setTargetPosition(0);
      motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
      servo = DataHub.hardwareMap.get(Servo.class, Config.WobbleServo);
    }
    //motor
    void setWobblePower(double power) {
        motor.setPower(power);
    }
    double getWobblePower(){
        return motor.getPower();
    }
    void setMotorPosition(int ticks){
        motor.setTargetPosition(ticks);
    }

    //servo
    void setServoPosition(double position){
        servo.setPosition(position);
    }
    double getServoPosition(){
        return servo.getPosition();
    }

    /**
     * sets motor power, motor position, and servo position to the values specified in motorData
     */
    void update(){
        setWobblePower(Robot.get().motorData.wobbleMotorPower);
        setMotorPosition(Robot.get().motorData.tgtWobbleMotorPos);
        setServoPosition(Robot.get().motorData.tgtWobbleServoPos);
    }
}