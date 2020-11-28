package org.firstinspires.ftc.teamcode.OpModes.TeleOps;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.OpModes.TeleOps.SingleControllers.DriverProgramScript;
import org.firstinspires.ftc.teamcode.OpModes.TeleOps.SingleControllers.Driver1ProgramExample;
import org.firstinspires.ftc.teamcode.OpModes.TeleOps.SingleControllers.Driver2ProgramExample;

public class OneController extends TeleOpScript {
    ElapsedTime deltaTime = new ElapsedTime();
    Telemetry telemetry;

    @Override
    public void loop() {
        controller1.loop();
        telemetry.addData("xPos", Robot.get().getLocation().getX());
        telemetry.addData("yPos", Robot.get().getLocation().getY());
        telemetry.addData("rotation", Robot.get().getRotation());
    }
    public OneController(Telemetry telemetry){
        init();
        this.telemetry = telemetry;
    }

    //this is the only method you should need to change to make new TeleOps
    private void init(){
        controller1 = new Driver1ProgramExample();
    }
}