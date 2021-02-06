package org.firstinspires.ftc.teamcode.OpModes.Autos.DeliverWobbleGoal_ABC;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Helpers.DataHub;
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
    boolean[] moveAUTO = new boolean[100];
    int numberOfShots = 0;
    double standardPower;
    SquareMode codeMode;
    Telemetry telemetry;
    int numberOfRings;


    enum SquareMode {
        IDLE,
        driveToRings,
        measureRings,
        goToSquare,
        gracePeriod,
        goToRingShootPos,
        shootRings,
        park,
        DONE


    }

    @Override
    public void loop() {
        Robot.get().setDrivePowers(drive, drive); //set both motors to the same power
        telemetry.addData("current mode / stage", codeMode);
        telemetry.addData("Est distance travelled", Robot.get().avgRightAndLeftOdos());
        telemetry.addData("current power", drive);
        telemetry.addData("number of rings", numberOfRings);
        telemetry.addData("get heading", Robot.get().getHeading(AngleUnit.DEGREES));
        telemetry.addData("degrees", Robot.get().getRotationDegrees());

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
                    codeMode = SquareMode.goToSquare;
                }
                break;
                case goToSquare: {
                    if (numberOfRings== 0){
                        if (!moveAUTO[8]){
                            moveAUTO[8] =Robot.get().driveStraight(230);
                        }
                        if (moveAUTO[8]){
                            codeMode = SquareMode.goToRingShootPos;
                        }
                    } else if (numberOfRings == 1){
                        Robot.get().setDrivePowers(leftPowerSWITCH, rightPowerSWITCH);
                        if (Robot.get().getHeading(AngleUnit.DEGREES) > 30 && Robot.get().getLeftOdo() > 75){
                            codeMode = SquareMode.goToRingShootPos;
                        }
                    } else if (numberOfRings == 4){
                        if (!moveAUTO[9]){
                            moveAUTO[9] = Robot.get().driveStraight(55);
                        }
                        if (moveAUTO[9]){
                            codeMode = SquareMode.goToRingShootPos;
                        }
                    }
                }
                break;
                case goToRingShootPos:{
                    if (numberOfRings == 0) {
                        if (!moveAUTO[0]) {
                            moveAUTO[0] = Robot.get().driveStraight(-150);
                        }
                        if (moveAUTO[0]) {
                            Robot.get().setDrivePowers(0.4, -0.4);
                            if (Math.abs(Robot.get().getHeading(AngleUnit.DEGREES)) > 87) {
                                Robot.get().setDrivePowers(0, 0);
                                if (!moveAUTO[1]) {
                                    moveAUTO[1] = Robot.get().driveStraight(55);
                                }
                                if (moveAUTO[1]) {
                                    Robot.get().setDrivePowers(0.35, -0.35);
                                    if (Math.abs(Robot.get().getRotationDegrees()) > 178.25) {
                                        Robot.get().setDrivePowers(0, 0);
                                        codeMode = SquareMode.shootRings;
                                    }

                                }
                            }
                        }
                    } else if (numberOfRings == 1){
                        if (!moveAUTO[3]){
                            moveAUTO[3] = Robot.get().driveStraight(-47);
                        }
                        if (moveAUTO[3]){
                            Robot.get().setDrivePowers(-0.15,0.15);
                            if ((Robot.get().getRotationDegrees() > 10)){
                                Robot.get().setDrivePowers(0,0);
                            }
                            codeMode = SquareMode.shootRings;
                        }

                    } else if (numberOfRings == 4){
                        if (!moveAUTO[4]){
                            moveAUTO[4] = Robot.get().driveStraight(-10);
                        }
                        if (moveAUTO[4]){
                            Robot.get().setDrivePowers(0.3,-0.3);
                            if (Robot.get().getRotationDegrees() > 87){
                                Robot.get().setDrivePowers(0,0);
                            }
                            if (!moveAUTO[5]){
                                moveAUTO[5] = Robot.get().driveStraight(25);
                            }
                            if (moveAUTO[5]){
                                Robot.get().setDrivePowers(-0.3,-0.3);
                                if (Robot.get().getRotationDegrees() < 0){
                                    Robot.get().setDrivePowers(0,0);
                                }
                                codeMode = SquareMode.shootRings;
                            }
                        }
                    }
                }
                break;
                case shootRings:{
                    Robot.get().setLauncherPower(1750);
                    Robot.get().spool();
                    if (Robot.get().getLauncherVelocity() == 1750){//NEED TO DO 3 TIMES
                        //in order to stay in the launch limit; lower velocity to 1750
                        //Robot.get().setContinousFire(true);
                        numberOfShots ++;
                        Robot.get().fire();

                    }
                    if (numberOfShots == 3) {
                        Robot.get().setLauncherPower(0);
                        codeMode = SquareMode.park;
                    }
                }
                break;
                case park:{
                    if (!moveAUTO[6]){
                        moveAUTO[6]=Robot.get().driveStraight(-30);
                    }
                    if (moveAUTO[6]){
                        codeMode =  SquareMode.DONE;
                    }
                }
                case DONE:{
                    Robot.get().stopAllMotors();

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
    }
}



