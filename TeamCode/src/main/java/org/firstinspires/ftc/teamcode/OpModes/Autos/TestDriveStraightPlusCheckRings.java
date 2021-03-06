package org.firstinspires.ftc.teamcode.OpModes.Autos;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
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


    ArrayList<Double> highs;
    ArrayList<Double> lows;

    double highMeasure;
    double lowMeasure;

    double meanHigh;
    double meanLow;

    double MADhigh;
    double MADlow;

    enum State{
        START,
        MOVE_FORWARD,
        MOVE_BACK,
        STOP
    }
    @Override
    public void loop() {
        telemetry.addData("state (move or not)", state);
        measure();
        stats();
        checkRingsDelayed(1000);

        switch(state){
            case START:
                state = State.MOVE_FORWARD;
                break;
            case MOVE_FORWARD:
                helper = robot.driveStraight(60.0, 0.23);
                //reachedTarget.toggle(helper);
                if(helper){
                    state = State.STOP;
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
//        telemetry.addData("helper value", helper);
//        telemetry.addData("check value (should be false)", check);
//        telemetry.addData("distance travelled", Robot.get().avgRightAndLeftOdos());
//        telemetry.addData("state", state);
//        telemetry.addData("forward distance value", Robot.get().autoPilot.targetDistance);

        telemetry.addLine("======================================================");
        telemetry.addData("number of rings", numberOfRings);
        telemetry.addData("high", robot.getLaserHigh());
        telemetry.addData("low", robot.getLaserLow());
        telemetry.addData("high mean", meanHigh);
        telemetry.addData("milliseconds", milliseconds);

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

        highs =  new ArrayList<Double>(16);
        lows =  new ArrayList<Double>(16);

        deltaTime = new ElapsedTime();
        milliseconds = 0;
        millisecondsLast = 0;
    }


    void measure(){
        millisecondsLast = milliseconds;
        milliseconds = deltaTime.milliseconds();
        double deltaMilliseconds = milliseconds - millisecondsLast;

        highMeasure = robot.getLaserHigh();
        lowMeasure = robot.getLaserLow();

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
