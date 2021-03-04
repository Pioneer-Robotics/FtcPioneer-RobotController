package org.firstinspires.ftc.teamcode.OpModes.Autos;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Helpers.DataHub;

public abstract class AutoScript {
    public boolean useOdos;
    public boolean useDumbLaunch;

    public double startX, startY;
    public Robot robot;
    public Telemetry telemetry;
    public abstract void loop();
    public abstract void init();

    public ElapsedTime deltaTime;

    public int numberOfRings;
    double thresholdForRingSighting;

    /**
     * almost always the thing to call when you want to init an Auto. You might need to add
     * more stuff, but this is the basic
     */
    public void standardInit(){
        startX = 0;
        startY = 0;

        useOdos = true;
        useDumbLaunch = true;

        robot = Robot.get();
        telemetry = DataHub.telemetry;
        deltaTime = new ElapsedTime();

        numberOfRings = 0;
        thresholdForRingSighting = 40;
    }

    void checkRingsReal() {
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
    public void checkRings(){
        if(deltaTime.milliseconds() > 1000){
            checkRingsReal();
        }
        else {
            numberOfRings = 0;
        }
    }
}
