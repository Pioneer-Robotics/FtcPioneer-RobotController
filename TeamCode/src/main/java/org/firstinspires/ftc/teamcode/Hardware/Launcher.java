package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Helpers.DataHub;

public class Launcher {
    MotorPair motors;
    DcMotor m1;

    Launcher(){
        m1 = DataHub.hardwareMap.get(DcMotor.class, Config.launcherMotor1);
        DcMotor m2 = DataHub.hardwareMap.get(DcMotor.class, Config.launcherMotor2);
        motors = new MotorPair(m1, m2);
        motors.setDriveMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motors.setDriveMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motors.setDirection(DcMotor.Direction.REVERSE);
    }

    void setPower(double power){
        motors.setPower(power);
    }
}
