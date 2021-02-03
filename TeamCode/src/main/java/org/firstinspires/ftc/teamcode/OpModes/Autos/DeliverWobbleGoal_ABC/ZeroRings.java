package org.firstinspires.ftc.teamcode.OpModes.Autos.DeliverWobbleGoal_ABC;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Helpers.DataHub;
import org.firstinspires.ftc.teamcode.OpModes.Autos.AutoScript;

/**
 * this program is meant to drive forward to the "C" square, drop the wobble goal, and park
 */
public class ZeroRings extends AutoScript {
    double drive = 0.0;
    /**
     * the magnitude of power we set the motors to when we want to move
     */
    double standardPower;
    SquareMode codeMode;
    Telemetry telemetry;

    enum SquareMode {
        IDLE,
        DRIVE2POSITION,
        STOP_IN_POSITION,
        BACKWARD,
        PARK,
        DONE

    }
    @Override
    public void loop() {
        Switch();
        Robot.get().setDrivePowers(drive, drive); //set both motors to the same power
        telemetry.addData("current mode / stage", codeMode);
        telemetry.addData("Est distance travelled", Robot.get().avgRightAndLeftOdos());
        telemetry.addData("current power", drive);
    }

    @Override
    public void init() {
        codeMode = SquareMode.IDLE;
        drive = 0;
        telemetry = DataHub.telemetry;
        startX = 0;
        startY = 0;
        standardPower = 0.2; //TODO raise this speed when we feel it's safe
        //standard power needs to be greater than 0.1 or the robot won't move
    }


    void Switch(){
        switch (codeMode){
            case IDLE:{ //this will instantly move on, we might delete it later
                this.codeMode = SquareMode.DRIVE2POSITION;
            }
            break;

            case DRIVE2POSITION:{ //
                drive = standardPower;
                codeMode = SquareMode.STOP_IN_POSITION;
                //move forward
                //once finished, proceed to STOP_IN_POSITION
            }
            break;

            case STOP_IN_POSITION:{
                if (Robot.get().avgRightAndLeftOdos() >= 165) {
                    drive = 0;
                    //TODO show Seth why this need to be in the if statement
                    codeMode = SquareMode.BACKWARD;
                }
                else{
                    drive = standardPower;
                }
            }
            break;

            case BACKWARD:{
                drive = -standardPower;

                codeMode = SquareMode.PARK;
                //move backward
                //back into IDLE
            }
            break;

            case PARK:{
                if (Robot.get().avgRightAndLeftOdos() <= 100){
                    drive = 0;
                    codeMode = SquareMode.DONE;
                }
                else{
                    drive = -standardPower;
                }

            }
            break;

            case DONE:{
                drive = 0;
            }
        }

    }
}
