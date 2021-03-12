package org.firstinspires.ftc.teamcode.Hardware;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Helpers.DataHub;

public class WobbleArm {
    private final DcMotorEx wobbleMotor;
    private final Servo wobbleServo;

    public WobbleArm(){
        wobbleMotor = DataHub.hardwareMap.get(DcMotorEx.class, Config.WobbleMotor);
        wobbleMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        wobbleServo = DataHub.hardwareMap.get(Servo.class, Config.WobbleServo);
        wobbleServo.setPosition(Config.Wobble_Servo_Closed_Pos);
    }

    public void setWobbleMotorPower(double power){
        wobbleMotor.setPower(power);
    }

    public void setWobbleServoPosition(boolean open){
        wobbleServo.setPosition( open ? Config.Wobble_Servo_Open_Pos : Config.Wobble_Servo_Closed_Pos);

    }
    public void WobbleGoTo90(){
        wobbleMotor.setTargetPosition(Config.WOBBLE_DOWN_POS);
        wobbleMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public void WobbleChillPos(){
        wobbleMotor.setTargetPosition(Config.WOBBLE_UP_POS);
        wobbleMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    void setRunMode(DcMotor.RunMode runMode){
        wobbleMotor.setMode(runMode);
    }


}
