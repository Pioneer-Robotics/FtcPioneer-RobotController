package org.firstinspires.ftc.teamcode.OpModes.TeleOps;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Helpers.DataHub;
import org.firstinspires.ftc.teamcode.OpModes.TeleOps.SingleControllers.DriverProgramScript;
import org.firstinspires.ftc.teamcode.Hardware.Robot;

public abstract class TeleOpScript{
    DriverProgramScript driverProgram1;
    DriverProgramScript driverProgram2;
    public abstract void loop();
    public abstract void init();
    public Robot robot;
    public Telemetry telemetry;

    public void standardInit(){
        robot = Robot.get();
        telemetry = DataHub.telemetry;
    }
}
