package org.firstinspires.ftc.teamcode.OpModes.TeleOps;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.OpModes.TeleOps.SingleControllers.Driver1ProgramExample;
import org.firstinspires.ftc.teamcode.OpModes.TeleOps.SingleControllers.Driver2ProgramExample;

public class ExampleTeleOp extends TeleOpScript {
    ElapsedTime deltaTime = new ElapsedTime();

    @Override
    public void loop() {
        driverProgram1.loop();
        driverProgram2.loop();
    }
    public ExampleTeleOp(){
        init();
    }

    //this is the only method you should need to change to make new TeleOps
    public void init(){
        driverProgram1 = new Driver1ProgramExample();
        driverProgram2 = new Driver2ProgramExample();
    }
}
