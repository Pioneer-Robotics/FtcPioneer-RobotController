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

    public int numberOfRings;
    double thresholdForRingSighting;

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

        numberOfRings = 0;
        thresholdForRingSighting = 40;
    }

    public void checkRings() {
        double highMeasure = robot.getLaserHigh();
        double lowMeasure = robot.getLaserLow();

        if(numberOfRings == 0){
            if(highMeasure < thresholdForRingSighting){
                numberOfRings = 4;
            }
            else if(lowMeasure < thresholdForRingSighting){
                numberOfRings = 1;
            }
        }
        else if(numberOfRings == 1){
            if(highMeasure < thresholdForRingSighting){
                numberOfRings = 4;
            }
        }
        else{
            //this means number of rings = 4, leave it like that
            numberOfRings = 4;
        }
    }
}
