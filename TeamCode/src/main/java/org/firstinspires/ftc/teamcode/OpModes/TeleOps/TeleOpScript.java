package org.firstinspires.ftc.teamcode.OpModes.TeleOps;

import org.firstinspires.ftc.teamcode.OpModes.TeleOps.SingleControllers.DriverProgramScript;

public abstract class TeleOpScript {
    DriverProgramScript controller1;
    DriverProgramScript controller2;
    public abstract void loop();
}
