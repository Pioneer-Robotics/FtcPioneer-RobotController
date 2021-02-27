package org.firstinspires.ftc.teamcode.OpModes.Autos;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Helpers.DataHub;

public abstract class AutoScript {
    public boolean odos;
    public double startX, startY;
    public Robot robot;
    Telemetry telemetry;
    public abstract void loop();
    public abstract void init();

    /**
     * almost always the thing to call when you want to init an Auto. You might need to add
     * more stuff, but this is the basic
     */
    public void standardInit(){
        startX = 0;
        startY = 0;
        odos = true;
        robot = Robot.get();
        telemetry = DataHub.telemetry;
    }
}
