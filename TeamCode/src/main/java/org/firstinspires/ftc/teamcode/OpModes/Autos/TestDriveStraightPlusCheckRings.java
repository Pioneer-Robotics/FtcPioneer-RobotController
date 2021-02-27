package org.firstinspires.ftc.teamcode.OpModes.Autos;

import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Helpers.DataHub;
import org.firstinspires.ftc.teamcode.Helpers.Toggle;
import org.firstinspires.ftc.teamcode.Helpers.Utils;

import java.util.ArrayList;

public class TestDriveStraightPlusCheckRings extends AutoScript{
    ElapsedTime deltaTime;
    double milliseconds;
    double millisecondsLast;

    Telemetry telemetry;
    Gamepad gamepad;
    Toggle reachedTarget; //used to make sure the autopilot stops when it should
    State state;
    boolean check;
    boolean helper = false;

    int numberOfRings;
    DistanceSensor laserHigh;
    DistanceSensor laserLow;

    double highMeasure;
    double lowMeasure;

    ArrayList<Double> highs;
    ArrayList<Double> lows;

    double meanHigh;
    double meanLow;

    double MADhigh;
    double MADlow;

    enum State{
        MOVE_FORWARD,
        MOVE_BACK,
        STOP
    }
    @Override
    public void loop() {
        telemetry.addData("state (move or not)", state);
        measure();
        checkRings();
        stats();

        switch(state){
            case MOVE_FORWARD:
                helper = Robot.get().driveStraight(100);
                //reachedTarget.toggle(helper);
                if(helper){
                    state = State.MOVE_BACK;
                    check = true;
                }
                break;
            case MOVE_BACK:
                Robot.get().setDrivePowers(0,0);
                if(gamepad.left_stick_y > 0.5){
                    if(Robot.get().driveStraight(-60)){
                        state = State.STOP;
                    }
                }
                break;
            case STOP:
                Robot.get().setDrivePowers(0,0);
                break;
        }
        telemetry.addData("helper value", helper);
        telemetry.addData("check value (should be false)", check);
        telemetry.addData("distance travelled", Robot.get().avgRightAndLeftOdos());
        telemetry.addData("state", state);
        telemetry.addData("forward distance value", Robot.get().autoPilot.targetDistance);

        telemetry.addLine("======================================================");
        telemetry.addData("number of rings", numberOfRings);
        telemetry.addData("high mean", meanHigh);
        telemetry.addData("low mean", meanLow);
        telemetry.addData("high MAD", MADhigh);
        telemetry.addData("low MAD", MADlow);
        telemetry.addData("milliseconds", milliseconds);
        telemetry.addData("high length", highs.size());

    }

    @Override
    public void init() {
        startX = 0;
        startY = 0;
        telemetry = DataHub.telemetry;
        gamepad = DataHub.gamepad1;
        reachedTarget = new Toggle(false);
        state = State.MOVE_FORWARD;
        check = false;
        helper = false;

        numberOfRings = 0;
        laserHigh = Robot.laserHigh;
        laserLow = Robot.laserLow;

        highs =  new ArrayList<Double>(16);
        lows =  new ArrayList<Double>(16);

        deltaTime = new ElapsedTime();
        milliseconds = 0;
        millisecondsLast = 0;
    }
    int checkRings(){
        if (numberOfRings==4){
            //pass
        }
        else if(numberOfRings == 1){
            if(highMeasure < 50){
                numberOfRings = 4;
            }
            else{
                //pass
            }
        }
        else{
            if(highMeasure < 50){
                numberOfRings = 4;
            }
            else if(lowMeasure < 50){
                numberOfRings = 1;
            }
        }
        return numberOfRings;
    }

    void measure(){
        millisecondsLast = milliseconds;
        milliseconds = deltaTime.milliseconds();
        double deltaMilliseconds = milliseconds - millisecondsLast;

        highMeasure = laserHigh.getDistance(DistanceUnit.CM);
        lowMeasure = laserLow.getDistance(DistanceUnit.CM);

        highs.add(highs.size(), deltaMilliseconds); //fills the Arraylist w/ data from the how long it was between things
        lows.add(lows.size(), deltaMilliseconds);
    }

    void stats(){
        meanHigh = (double)highs.size() / milliseconds;
        meanLow = (double)lows.size() / milliseconds;

        MADhigh = Utils.getMAD(highs);
        MADlow = Utils.getMAD(lows);
    }

}
