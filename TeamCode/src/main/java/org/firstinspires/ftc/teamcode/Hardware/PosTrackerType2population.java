package org.firstinspires.ftc.teamcode.Hardware;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Helpers.Vector2;

public class PosTrackerType2population {
    PositionTracker2[] population;
    Telemetry telemetry;
    PosTrackerType2population(int populationSize, double maxWaitTimeMS){
        population = new PositionTracker2[populationSize];
        for(int i = 0; i < population.length; i++){
            population[i] = new PositionTracker2(0,0,
                    (i+1) * maxWaitTimeMS / populationSize);
        }
        telemetry = Robot.get().telemetry;
    }
    PosTrackerType2population(int populationSize, double maxWaitTimeMS, double startX, double startY){
        population = new PositionTracker2[populationSize];
        for(int i = 0; i < population.length; i++){
            population[i] = new PositionTracker2(startX, startY,
                    (i+1) * maxWaitTimeMS / populationSize);
        }
        telemetry = Robot.get().telemetry;
    }
    void update(){
        for (int i = 0; i < population.length; i++) {
            population[i].update();
        }
    }
    void doTelemetryReadout(){
        for(int i = 0; i < population.length; i++){
            Vector2 location = population[i].getLocation();
            telemetry.addData("type2 " +
                    population[i].waitIntervalMS + " ms xPos", location.getX());
            telemetry.addData("type2 " +
                    population[i].waitIntervalMS + " ms yPos", location.getY());
        }
    }
}
