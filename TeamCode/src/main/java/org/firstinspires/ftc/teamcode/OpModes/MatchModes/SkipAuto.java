package org.firstinspires.ftc.teamcode.OpModes.MatchModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Helpers.bMath;
import org.firstinspires.ftc.teamcode.OpModes.Autos.ExampleAuto;
import org.firstinspires.ftc.teamcode.OpModes.Autos.NullAuto;
import org.firstinspires.ftc.teamcode.OpModes.TeleOps.OneController;

@TeleOp (name = "SkipAuto", group = "example")
public class SkipAuto extends GenericOpMode {
    double left, right, middle, D;
    @Override
    public void selectAutoAndTeleOp(){
        auto = new NullAuto(); //you need this even if you don't use it
        teleOp = new OneController();
    }
    @Override
    public void runOpMode() throws InterruptedException {
        selectAutoAndTeleOp();
        initAndWaitForStart();
        while(opModeIsActive()) {
            teleOp.loop();
            Robot.get().update();
            left = Robot.get().getLeftOdo();
            right = Robot.get().getRightOdo();
            middle = Robot.get().getMidOdo();
            telemetry.addData("left odo", left);
            telemetry.addData("right odo", right);
            telemetry.addData("mid odo", middle);
            D = (right - left) / bMath.pi2;
            telemetry.addData("D", D);
            telemetry.addData("left ticks", Robot.get().getOdometerTicks().x);
            telemetry.addData("right ticks", Robot.get().getOdometerTicks().y);
            telemetry.addData("middle ticks", Robot.get().getOdometerTicks().z);
            telemetry.update();
        }
    }
}
