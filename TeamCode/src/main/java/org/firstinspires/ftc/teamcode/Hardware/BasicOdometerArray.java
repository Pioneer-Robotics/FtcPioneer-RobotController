package org.firstinspires.ftc.teamcode.Hardware;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Helpers.Vector2;

public class BasicOdometerArray {
    BasicOdometer[] population;
    Telemetry telemetry;
    BasicOdometerArray(int populationSize, double maxWaitTimeMS){
        population = new BasicOdometer[populationSize];
        for(int i = 0; i < population.length; i++){
            population[i] = new BasicOdometer(0,0,
                    (i+1) * maxWaitTimeMS / populationSize);
        }
        telemetry = BasicRobot.get().telemetry;
    }
    BasicOdometerArray(int populationSize, double maxWaitTimeMS, double startX, double startY){
        population = new BasicOdometer[populationSize];
        for(int i = 0; i < population.length; i++){
            population[i] = new BasicOdometer(startX, startY,
                    (i+1) * maxWaitTimeMS / populationSize);
        }
        telemetry = BasicRobot.get().telemetry;
    }
    void update(){
        for (int i = 0; i < population.length; i++) {
            population[i].update();
        }
    }
    void doTelemetryReadout(){
        for(int i = 0; i < population.length; i++){
            Vector2 location = population[i].getLocation();
            telemetry.addData("type1 " +
                    population[i].waitIntervalMS + " ms xPos", location.getX());
            telemetry.addData("type1 " +
                    population[i].waitIntervalMS + " ms yPos", location.getY());
        }
    }
}
