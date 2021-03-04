package org.firstinspires.ftc.teamcode.OpModes.Autos.DeliverWobbleGoal_ABC;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
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

    GoToSquare goToSquare;

    Toggle helper;
    Gamepad gamepad;

    //Dumb Launch:

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

    byte timesFired = 0;
    //



    enum SquareMode {
        start,
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
                case start:{
                    numberOfRings = 0; //for some reason it likes to start by thinking there are 4 rings
                    codeMode = SquareMode.driveToRings;
                break;
                }
                case driveToRings:{
                    checkRings();
                    if (!moveAUTO[0]){
                        moveAUTO[0] = Robot.get().driveStraight(65);
                    }
                    //reachedTarget.toggle(helper);
                    if(moveAUTO[0]){
                        codeMode = SquareMode.measureRings;
                        robot.setDrivePowers(0,0);
                    }
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
                        codeMode = SquareMode.resetTimer; //TODO change this to reset times once launcher works
                    }
                    else{
                        goToSquare.goToSquareAndThenToShootPos();
                    }
                break;
                case resetTimer:
                    deltaTime.reset();
                    codeMode = SquareMode.shootRings; //TODO change this to shootRings once launcher works
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
                            if (launchTimer.seconds() >= 2) {
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
                            if (launchTimer.seconds() >= 0.3) {
                                flicker.setPosition(Config.launcherServoOut);
                                launchMode = DumbLaunch.PUSHING;
                                launchTimer.reset();
                                timesFired++;
                            }
                            break;

                    }
                    if (timesFired >= 3 ) {
                        codeMode = SquareMode.park;
                        Robot.get().emergencyStop();
                    }
                    Robot.get().launchOverride(true);
                }
                break;
                case park:{
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
                    robot.stopAllMotors();
                    checkRings();
                    robot.setDrivePowers(0,0);
                }
                break;
            }
        }



    @Override
    public void init() {
        codeMode = SquareMode.driveToRings;

        standardInit();

        standardPower = 0.2; //TODO raise this speed when we feel it's safe
        //standard power needs to be greater than 0.1 or the robot won't move

        //for loop makes sure all the booleans start as false
        Utils.setBooleanArrayToFalse(moveAUTO);

        numberOfShots = 0;
        helper = new Toggle(false);
        gamepad = DataHub.gamepad1;


        Utils.setBooleanArrayToFalse(moveAUTO);


        //DumbLaunchControl Stuff
        launchTimer = new ElapsedTime();
        launcherQuit = new ElapsedTime();
        flicker = DataHub.hardwareMap.get(Servo.class, Config.launcherServo);

    }
}



