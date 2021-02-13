package org.firstinspires.ftc.teamcode.OpModes.Autos;

import org.firstinspires.ftc.teamcode.Hardware.Robot;

public abstract class AutoScript {
    public boolean odos;
    public double startX, startY;
    public Robot robot;
    public abstract void loop();
    public abstract void init();
}
