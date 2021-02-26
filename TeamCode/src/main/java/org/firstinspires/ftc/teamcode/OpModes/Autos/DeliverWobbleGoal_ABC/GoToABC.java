package org.firstinspires.ftc.teamcode.OpModes.Autos.DeliverWobbleGoal_ABC;

import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Hardware.Robot;

import org.firstinspires.ftc.teamcode.Helpers.*;
import org.firstinspires.ftc.teamcode.OpModes.Autos.AutoScript;

/**
 * this program is meant to drive forward to the "C" square, drop the wobble goal, and park
 */

public class GoToABC extends AutoScript {
    double drive = 0;
    //only useful in switch statement
    //double leftPowerSWITCH = 0.3;
    //double rightPowerSWITCH = 0.2;
    /**
     * the magnitude of power we set the motors to when we want to move
     */
    boolean[] moveAUTO = new boolean[15]; //needed a lot of booleans
    int numberOfShots;
    double standardPower;
    SquareMode codeMode;
    Telemetry telemetry;
    int numberOfRings;

    DistanceSensor laserLow;
    DistanceSensor laserHigh;

    GoToSquare goToSquare;
    ElapsedTime deltaTime;

    Toggle helper;
    Gamepad gamepad;


    enum SquareMode {
        IDLE,
        driveToRings,
        chooseCorrectSquare,
        goToSquareThenLineUpForShooting,
        resetTimer,
        shootRings,
        park,
        DONE


    }

    @Override
    public void loop() {
        //Robot.get().setDrivePowers(drive, drive); //set both motors to the same power
        telemetry.addData("elapsed time", deltaTime.seconds());
        telemetry.addData("current mode / stage", codeMode);
        telemetry.addData("Est distance travelled", Robot.get().avgRightAndLeftOdos());
        telemetry.addData("current power", drive);
        telemetry.addData("number of rings", numberOfRings);
        telemetry.addData("current heading from IMU", Robot.get().getHeading(AngleUnit.DEGREES));
        telemetry.addData("degrees from Robot", Robot.get().getRotationDegrees());

        helper.toggle(gamepad.a);

            switch (codeMode){
                case IDLE:{
                    drive = 0;
                    this.codeMode = SquareMode.driveToRings;
                }
                break;
                case driveToRings:{
                    //in order to combat return statement on driveStraight, we use a boolean
                    if(!moveAUTO[7]){
                        moveAUTO[7] = Robot.get().driveStraight(65);
                    }
                    if (moveAUTO[7]){
                        checkRings();
                        codeMode = SquareMode.chooseCorrectSquare;
                    }
                    checkRings();

                }
                break;
                case chooseCorrectSquare:{
                    //set the square we want to go to
                    if(numberOfRings == 0){
                        goToSquare = new GoToA();
                        codeMode = SquareMode.DONE;
                    }
                    else if(numberOfRings == 1){
                        goToSquare = new GoToB();
                        codeMode = SquareMode.DONE;
                    }
                    else{
                        goToSquare = new GoToC();
                        codeMode = SquareMode.DONE;
                    }

                    //goToSquare = new GoToA(); //TODO remove this line, it is only here for testing

                    //move to the next state
                    codeMode = SquareMode.DONE ;
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
                        if(helper.justChanged()) {
                            codeMode = SquareMode.DONE;
                        }
                    }
                }
                case DONE:{
                    checkRings();
                    if(helper.justChanged()){
                        codeMode = SquareMode.resetTimer;
                        Robot.get().allowMovement();
                    }
                    else {
                        Robot.get().stopAllMotors();
                    }

                }
            }
            robot.update(odos);
        }



    @Override
    public void init() {
        codeMode = SquareMode. IDLE;
        drive = 0;
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
        numberOfRings = 0;

        laserLow = Robot.laserLow;
        laserHigh = Robot.laserHigh;
    }

    void checkRings(){
        if(laserHigh.getDistance(DistanceUnit.CM) <= 50){
            numberOfRings = 4;
        }
        else if(numberOfRings != 4){
            if(laserLow.getDistance(DistanceUnit.CM) <= 50){
                numberOfRings = 1;
            }
        }
        else if(numberOfRings != 4 && numberOfRings != 1){//this is useless, makes seth feel better
            numberOfRings = 0;
        }
    }
}



