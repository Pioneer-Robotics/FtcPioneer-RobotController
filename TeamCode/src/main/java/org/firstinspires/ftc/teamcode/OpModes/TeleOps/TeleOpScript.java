package org.firstinspires.ftc.teamcode.OpModes.TeleOps;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.OpModes.TeleOps.SingleControllers.DriverProgramScript;
import org.firstinspires.ftc.teamcode.Hardware.Robot;

public abstract class TeleOpScript{
    DriverProgramScript driverProgram1;
    DriverProgramScript driverProgram2;
    public abstract void loop();
    public abstract void init();
    public Robot robot;
}
