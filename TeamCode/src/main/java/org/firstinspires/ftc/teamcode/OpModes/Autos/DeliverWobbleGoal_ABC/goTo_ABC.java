package org.firstinspires.ftc.teamcode.OpModes.Autos.DeliverWobbleGoal_ABC;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Hardware.Robot;

import org.firstinspires.ftc.teamcode.Helpers.*;
import org.firstinspires.ftc.teamcode.OpModes.Autos.AutoScript;

/**
 * this program is meant to drive forward to the "C" square, drop the wobble goal, and park
 */

public class goTo_ABC extends AutoScript {
    double drive = 0;
    //only useful in switch statement
    double leftPowerSWITCH = 0.3;
    double rightPowerSWITCH = 0.2;
    /**
     * the magnitude of power we set the motors to when we want to move
     */
    boolean[] moveAUTO = new boolean[15]; //needed a lot of booleans
    int numberOfShots;
    double standardPower;
    SquareMode codeMode;
    Telemetry telemetry;
    int numberOfRings;

    GoToSquare goToSquare;
    ElapsedTime deltaTime;
    double startTimeFiring;

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
        //Robot.get().setDrivePowers(drive, drive); //set both motors to the same power
        telemetry.addData("elapsed time", deltaTime.seconds());
        telemetry.addData("current mode / stage", codeMode);
        telemetry.addData("Est distance travelled", Robot.get().avgRightAndLeftOdos());
        telemetry.addData("current power", drive);
        telemetry.addData("number of rings", numberOfRings);
        telemetry.addData("current heading from IMU", Robot.get().getHeading(AngleUnit.DEGREES));
        telemetry.addData("degrees from Robot", Robot.get().getRotationDegrees());
        telemetry.addData("start time firing", startTimeFiring);

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
                        codeMode = SquareMode.measureRings;
                    }
                }
                break;
                case measureRings:{
                    numberOfRings = Robot.get().amountOfRings();

                    //set the square we want to go to
                    if(numberOfRings == 0){
                        goToSquare = new GoToA();
                    }
                    else if(numberOfRings == 1){
                        goToSquare = new GoToB();
                    }
                    else{
                        goToSquare = new GoToC();
                    }

                    goToSquare = new GoToC(); //TODO remove this line, it is only here for testing

                    //move to the next state
                    codeMode = SquareMode.goToSquareThenLineUpForShooting;
                }
                break;
                case goToSquareThenLineUpForShooting:
                    if(goToSquare.done){
                        codeMode = SquareMode.resetTimer;
                    }
                    else{
                        goToSquare.goToSquareAndThenToShootPos();
                    }
                break;
                case resetTimer:
                    deltaTime.reset();
                    codeMode = SquareMode.shootRings;
                    break;
                case shootRings:{
                    telemetry.addLine("firing");
                    if(deltaTime.seconds() < 5) {//don't let it fire for > 3 secs
                        Robot.get().fire();
                        Robot.get().requestLaunch();
                    }
                    else{
                        Robot.get().emergencyStop();
                        codeMode = SquareMode.park;
                    }
//                    if (Robot.get().justShot()){//NEED TO DO 3 TIMES
//                        //in order to stay in the launch limit; lower velocity to 1750
//                        //Robot.get().setContinousFire(true);
//                        numberOfShots++;
//                    }
//                    if (numberOfShots == 3) {
//                        Robot.get().emergencyStop();
//                        codeMode = SquareMode.park;
//                    }
                }
                break;
                case park:{
                    if (!moveAUTO[6]){
                        moveAUTO[6] = Robot.get().driveStraight(-30, 0.3, 3);
                    }
                    if (moveAUTO[6]){
                        if(helper.justChanged()) {
                            codeMode = SquareMode.DONE;
                        }
                    }
                }
                case DONE:{
                    if(helper.justChanged()){
                        codeMode = SquareMode.shootRings;
                        Robot.get().allowMovement();
                        startTimeFiring = 0;
                    }
                    else {
                        Robot.get().stopAllMotors();
                    }

                }
            }


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
        startTimeFiring = 0;
        helper = new Toggle(false);
        gamepad = DataHub.gamepad1;
    }
}



