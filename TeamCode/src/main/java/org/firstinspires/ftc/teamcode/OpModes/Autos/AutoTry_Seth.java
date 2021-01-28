package org.firstinspires.ftc.teamcode.OpModes.Autos;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.internal.android.dex.Code;
import org.firstinspires.ftc.teamcode.Helpers.DataHub;
import org.firstinspires.ftc.teamcode.Hardware.Robot;

public class AutoTry_Seth extends AutoScript {
    Gamepad gamepad1;
    double drive = 0.0;

    enum SquareMode {
        IDLE,
        DRIVE2POSITION,
        STOP_IN_POSITION,
        BACKWARD,
        PARK

    }
    @Override
    public void loop() {

    }

    @Override
    public void init() {}


    AutoTry_Seth(SquareMode CodeMode){
        switch (CodeMode){
            case IDLE:{
                if (gamepad1.y){
                    CodeMode = SquareMode.DRIVE2POSITION;

                }
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
        }

    }
}
