package org.firstinspires.ftc.teamcode.OpModes.Autos.DeliverWobbleGoal_ABC;

import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Hardware.Robot;

import org.firstinspires.ftc.teamcode.Helpers.*;
import org.firstinspires.ftc.teamcode.OpModes.Autos.AutoScript;


/**
 * this program is meant to drive forward to the "C" square, drop the wobble goal, and park
 */

public class GoToABC extends AutoScript {
    //only useful in switch statement
    /**
     * the magnitude of power we set the motors to when we want to move
     */
    boolean[] moveAUTO = new boolean[15]; //needed a lot of booleans
    int numberOfShots;
    double standardPower;
    SquareMode codeMode;
    Telemetry telemetry;
    int numberOfRings;

    boolean didLowSeeAnything;

    GoToSquare goToSquare;
    ElapsedTime deltaTime;

    Toggle helper;
    Gamepad gamepad;


    enum SquareMode {
        IDLE,
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
        telemetry.addData("low saw something", didLowSeeAnything);

        telemetry.addData("degrees from Robot", Robot.get().getRotationDegrees());



        helper.toggle(gamepad.a);
            switch (codeMode){
                case IDLE:{
                    this.codeMode = SquareMode.driveToRings;
                }
                break;
                case driveToRings:{
                    checkRings();
                    moveAUTO[0] = Robot.get().driveStraight(65);
                    //reachedTarget.toggle(helper);
                    if(moveAUTO[0]){
                        codeMode = SquareMode.measureRings;
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
                    if(deltaTime.seconds() < 5) {//don't let it fire for > 5 secs
                        Robot.get().fire();
                    }
                    else{
                        Robot.get().emergencyStop();
                        codeMode = SquareMode.park;
                    }
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
                        Robot.get().stopAllMotors();
                        robot.stopAllMotors();

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

        if(low < 50){
            didLowSeeAnything = true;
        }
        return numberOfRings;
    }



    @Override
    public void init() {
        codeMode = SquareMode.IDLE;
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

        didLowSeeAnything = false;
    }
}



