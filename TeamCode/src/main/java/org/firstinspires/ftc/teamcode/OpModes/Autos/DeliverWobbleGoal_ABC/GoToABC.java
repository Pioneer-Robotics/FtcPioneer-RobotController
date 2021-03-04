package org.firstinspires.ftc.teamcode.OpModes.Autos.DeliverWobbleGoal_ABC;

import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Hardware.Config;
import org.firstinspires.ftc.teamcode.Hardware.Robot;

import org.firstinspires.ftc.teamcode.Helpers.*;
import org.firstinspires.ftc.teamcode.OpModes.Autos.AutoScript;


/**
 * this program is meant to drive forward to the "C" square, drop the wobble goal, and park
 */

public class GoToABC extends AutoScript {

    boolean[] moveAUTO = new boolean[15]; //need a lot of booleans
    int numberOfShots;
    double standardPower;
    SquareMode codeMode;
    Telemetry telemetry;
    int numberOfRings;

    GoToSquare goToSquare;
    ElapsedTime deltaTime;

    Toggle helper;
    Gamepad gamepad;

    //TODO REMOVE CRAP:

    enum DumbLaunch{
        IDLE,
        SPOOLING,
        PUSHING,
        RETRACTING,
    }
    DumbLaunch launchMode = DumbLaunch.IDLE;
    ElapsedTime launchTimer;
    ElapsedTime launcherQuit;

    Servo flicker;

    //



    enum SquareMode {
        driveToRings,
        measureRings,
        goToSquareThenLineUpForShooting,
        resetTimer,
        shootRings,
        park,
        DONE


    }

    @Override
    public void loop() {
        telemetry.addData("elapsed time", deltaTime.seconds());
        telemetry.addData("current mode / stage", codeMode);

        telemetry.addData("number of rings", numberOfRings);

        telemetry.addData("degrees from Robot", Robot.get().getRotationDegrees());



        helper.toggle(gamepad.a);
            switch (codeMode){
                case driveToRings:{
                    checkRings();
                    if (!moveAUTO[0]){
                        moveAUTO[0] = Robot.get().driveStraight(65);
                    }
                    //reachedTarget.toggle(helper);
                    if(moveAUTO[0]){
                        codeMode = SquareMode.DONE;
                        robot.setDrivePowers(0,0);
                    }
//                    //in order to combat return statement on driveStraight, we use a boolean
//                    if(!moveAUTO[7]){
//                        moveAUTO[7] = Robot.get().driveStriaght(65,.235);
//                    }
//                    if (moveAUTO[7]){
//
//                        codeMode = SquareMode.measureRings;
//                        //TODO fix why robot goes jit-jit
//                        //TODO test if measureRings case is started too early
//                    }
//                    checkRings();
               }
                break;
                case measureRings:{
                    checkRings();
                    //set the square we want to go to
                    if(numberOfRings == 4){
                        goToSquare = new GoToC();
                    }
                    else if(numberOfRings == 1){
                        goToSquare = new GoToB();
                    }
                    else{
                        goToSquare = new GoToA();
                    }

                    goToSquare = new GoToC(); //TODO remove this line, it is only here for testing

                    //move to the next state
                    codeMode = SquareMode.goToSquareThenLineUpForShooting;
                }
                break;
                case goToSquareThenLineUpForShooting:
                    robot.setDrivePowers(0,0);
                    if(goToSquare.done){
                        codeMode = SquareMode.park; //TODO change this to reset times once launcher works
                    }
                    else{
                        goToSquare.goToSquareAndThenToShootPos();
                    }
                break;
                case resetTimer:
                    deltaTime.reset();
                    codeMode = SquareMode.park; //TODO change this to shootRings once launcher works
                    break;
                case shootRings:{ //this is skipped, never runs
                    telemetry.addLine("firing");
                    switch (launchMode) {
                        case IDLE:
                            launchTimer.reset();
                            launchMode = DumbLaunch.SPOOLING;
                            Robot.get().spool();
                            flicker.setPosition(Config.launcherServoIn);
                            launcherQuit.reset();
                            break;
                        case SPOOLING:
                            if (launchTimer.seconds() >= 3) {
                                flicker.setPosition(Config.launcherServoOut);
                                launchMode = DumbLaunch.PUSHING;
                                launchTimer.reset();
                            }
                            break;
                        case PUSHING:
                            if (launchTimer.seconds() >= 1) {
                                flicker.setPosition(Config.launcherServoIn);
                                launchMode = DumbLaunch.RETRACTING;
                                launchTimer.reset();
                            }
                            break;
                        case RETRACTING:
                            if (launchTimer.seconds() >= 1) {
                                flicker.setPosition(Config.launcherServoOut);
                                launchMode = DumbLaunch.PUSHING;
                                launchTimer.reset();
                            }
                            break;

                    }
                    if (launcherQuit.seconds() < 10) {
                    } else {
                        codeMode = SquareMode.park;
                        Robot.get().emergencyStop();
                    }
                    Robot.get().launchOverride(true);
                }
                odos = false;
                break;
                case park:{
                    odos = true;
                    if (!moveAUTO[6]){
                        moveAUTO[6] = Robot.get().driveStraight(-60, 0.3, 3);
                    }
                    if (moveAUTO[6]){
                        {
                            codeMode = SquareMode.DONE;
                        }
                    }
                }
                break;
                case DONE:{
                    checkRings();
                        robot.setDrivePowers(0,0);

                }
                break;
            }
            robot.update(odos);

        }


    public int checkRings(){
        double high = robot.getLaserHigh();
        double low = robot.getLaserLow();

        if (high <50){
            numberOfRings = 4;
        } else if (numberOfRings!= 4){
            if (low < 50){
                numberOfRings = 1;
            }
            else if (numberOfRings !=4 && numberOfRings != 1){//code good
                numberOfRings = 0;
            }
        }
        return numberOfRings;
    }



    @Override
    public void init() {
        codeMode = SquareMode.driveToRings;
        telemetry = DataHub.telemetry;
        startX = 0;
        startY = 0;
        standardPower = 0.2; //TODO raise this speed when we feel it's safe
        //standard power needs to be greater than 0.1 or the robot won't move

        //for loop makes sure all the booleans start as false
        Utils.setBooleanArrayToFalse(moveAUTO);
        deltaTime = new ElapsedTime();
        numberOfShots = 0;
        helper = new Toggle(false);
        gamepad = DataHub.gamepad1;
        odos = true;

        Utils.setBooleanArrayToFalse(moveAUTO);


        //DumbLaunchControl Stuff
        launchTimer = new ElapsedTime();
        launcherQuit = new ElapsedTime();
        flicker = DataHub.hardwareMap.get(Servo.class, Config.launcherServo);

    }
}



