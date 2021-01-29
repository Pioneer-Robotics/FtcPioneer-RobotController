package org.firstinspires.ftc.teamcode.OpModes.Autos;

import org.firstinspires.ftc.teamcode.Hardware.Robot;

public class AutoTry_Seth extends AutoScript {
    double drive = 0.0;
    SquareMode CodeMode;

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
    }

    @Override
    public void init() {
        CodeMode = SquareMode.IDLE;
        drive = 0;
    }


    void Switch(){
        switch (CodeMode){
            case IDLE:{
                this.CodeMode = SquareMode.DRIVE2POSITION;
            }
            break;

            case DRIVE2POSITION:{
                drive= 0.5;
                CodeMode = SquareMode.STOP_IN_POSITION;
                //move forward
                //once finished, proceed to STOP_IN_POSITION
            }
            break;

            case STOP_IN_POSITION:{
                if (Robot.get().getLeftOdo() >= 165) {
                    drive = 0;
                }
                CodeMode = SquareMode.BACKWARD;
                //Stop in position
                //check telemetry
            }
            break;

            case BACKWARD:{
                    drive = -0.5;

                CodeMode = SquareMode.PARK;
                //move backward
                //back into IDLE
            }
            break;

            case PARK:{
                if (Robot.get().getLeftOdo() <= 750){
                    drive = 0;
                }
                CodeMode = SquareMode.IDLE;

            }
            break;

            case DONE:{
                drive = 0;
            }
        }

    }
}
