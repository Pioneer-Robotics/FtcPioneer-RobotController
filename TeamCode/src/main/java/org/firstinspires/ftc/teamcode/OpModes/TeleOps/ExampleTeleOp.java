package org.firstinspires.ftc.teamcode.OpModes.TeleOps;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.OpModes.TeleOps.SingleControllers.DriverProgramScript;
import org.firstinspires.ftc.teamcode.OpModes.TeleOps.SingleControllers.Driver1ProgramExample;
import org.firstinspires.ftc.teamcode.OpModes.TeleOps.SingleControllers.Driver2ProgramExample;

public class ExampleTeleOp extends TeleOpScript {
    ElapsedTime deltaTime = new ElapsedTime();
    DriverProgramScript controller1;
    DriverProgramScript controller2;

    @Override
    public void loop() {
        controller1.loop();
        controller2.loop();
    }
    public ExampleTeleOp(){
        init();
    }

    //this is the only method you should need to change to make new TeleOps
    private void init(){
        controller1 = new Driver1ProgramExample();
        controller2 = new Driver2ProgramExample();
    }
}
