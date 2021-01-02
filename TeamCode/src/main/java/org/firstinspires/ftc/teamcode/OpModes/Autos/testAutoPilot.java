package org.firstinspires.ftc.teamcode.OpModes.Autos;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Helpers.DataHub;
import org.firstinspires.ftc.teamcode.Helpers.bMath;

public class testAutoPilot extends AutoScript{
    ElapsedTime deltaTime;
    Telemetry telemetry;
    int stateNumber;

    /**
     *  startX and startY need to be set in the constructor instead of the init() method
     *  because Robot needs to pull them in order to properly init, but this auto can't init
     *  until Robot.init() is already called. It's a real chicken and egg problem. The solution
     *  is to set startX and startY in the constructor, construct the object, init Robot,
     *  then init this.
     */
    public testAutoPilot(){
        startX = 0.0;
        startY = 0.0;
    }

    @Override
    public void init() {
        deltaTime = new ElapsedTime();
        telemetry = DataHub.telemetry;
        stateNumber = 0;
    }

    @Override
    public void loop() {
        switch (stateNumber){
            case 0:
                Robot.get().allowMovement();
                state0_rotationMode();
                break;
            case 1:
                Robot.get().allowMovement();
                state1_driveMode();
                break;
            default: Robot.get().setDrivePowers(-0.25, 0.25);
        }
        doTelemetry();
    }

    private void state0_rotationMode() {
        double angleOffSet = bMath.regularizeAngleDeg(Robot.get().getRotationDegrees() - 30);
        if( Math.abs(angleOffSet) > 5 ){
            Robot.get().setDrivePowers(-0.25, 0.25);
        }
        else{
            stateNumber++;
        }
    }

    private void state1_driveMode() {
        if(Robot.get().getY() < 30){
            Robot.get().setDrivePowers(0.3,0.3);
        }
        else{
            stateNumber++;
        }
    }

    private void doTelemetry(){
        telemetry.addData("current x", Robot.get().getX());
        telemetry.addData("current y", Robot.get().getY());
        telemetry.addData("current angle", Robot.get().getRotationDegrees());
        telemetry.addData("state number", stateNumber);
    }
}
