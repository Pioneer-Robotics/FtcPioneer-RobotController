package org.firstinspires.ftc.teamcode.OpModes.MatchModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.OpModes.Autos.NullAuto;
import org.firstinspires.ftc.teamcode.OpModes.TeleOps.OneController;
import org.firstinspires.ftc.teamcode.OpModes.TeleOps.TeleOpStandard;

@TeleOp (name = "CalibrateOdometers", group = "test")
public class CalibrateOdometers extends GenericOpMode {
    int leftOdoTicks = 0;
    int rightOdoTicks = 0;
    int midOdoTicks = 0;

    @Override
    public void selectAutoAndTeleOp() {
        auto = new NullAuto();
        teleOp = new TeleOpStandard();
    }
    @Override
    public void runOpMode() throws InterruptedException {
        selectAutoAndTeleOp();
        initAndWaitForStart();
        while(opModeIsActive()) {
            teleOp.loop();
            Robot.get().update();
            updateOdoTicks();
            telemetry.addData("left ticks", leftOdoTicks);
            telemetry.addData("right ticks", rightOdoTicks);
            telemetry.addData("middle ticks", midOdoTicks);
            telemetry.update();
        }
    }
    void updateOdoTicks(){
        leftOdoTicks = Robot.get().getOdometerTicks().x;
        rightOdoTicks = Robot.get().getOdometerTicks().y;
        midOdoTicks = Robot.get().getOdometerTicks().z;
    }
}
