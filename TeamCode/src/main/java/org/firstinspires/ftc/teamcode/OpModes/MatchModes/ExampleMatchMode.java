package org.firstinspires.ftc.teamcode.OpModes.MatchModes;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.GenericOpMode;
import org.firstinspires.ftc.teamcode.OpModes.Autos.*;
import org.firstinspires.ftc.teamcode.OpModes.TeleOps.*;

public class ExampleMatchMode extends GenericOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        initAndWaitForStart();
        if(deltaTime.seconds() < 30){
            auto.main();
        }
        else{
            handleTeleOp();
        }
    }

    //this is the method you change when you make new MatchModes. Change "ExampleAuto" and
    //"ExampleTeleOp" to the specific autonomous and teleOp programs you want.
    public void chooseAutoAndTeleOp(){
        auto = new ExampleAuto();
        teleOp = new ExampleTeleOp();
    }

}
