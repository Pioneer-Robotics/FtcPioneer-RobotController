package org.firstinspires.ftc.teamcode.OpModes.Autos;

import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.robotcore.external.Telemetry;


public class AutoMode {
    Telemetry telemetry;
    double drive = 0.0;
    Gamepad gamepad;
    enum automode {
        START,
        FORWARD,
        STOP,
        DROP,
        UP,
        END
    }

    public void loop(){

    }

    private automode setautomode(automode autoMode){

        switch (autoMode){
            case START:
                if(gamepad.x) {
                    autoMode = automode.FORWARD;
                }
                break;
            case FORWARD:
                drive = 0.5;
                autoMode = automode.STOP;
                break;
            case STOP:
                drive = 0.0;
                autoMode = automode.DROP;
                break;
            case DROP:

                autoMode = automode.UP;
                break;
            case UP:
                autoMode = automode.END;
                break;
            case END:
                autoMode = automode.START;
                break;
        }

        return autoMode;
    }

}