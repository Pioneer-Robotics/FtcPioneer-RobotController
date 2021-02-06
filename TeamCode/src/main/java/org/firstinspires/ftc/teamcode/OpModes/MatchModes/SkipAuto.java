package org.firstinspires.ftc.teamcode.OpModes.MatchModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.OpModes.Autos.DeliverWobbleGoal_ABC.GoToB;
import org.firstinspires.ftc.teamcode.OpModes.TeleOps.TwoController.TwoControllerTwo;

@TeleOp (name = "SkipAuto", group = "example")
public class SkipAuto extends GenericOpMode {
    double left, right, middle, D;
    @Override
    public void selectAutoAndTeleOp(){
        auto = new GoToB(); //you need this even if you don't use it
        teleOp = new TwoControllerTwo();
    }
    @Override
    public void runOpMode() throws InterruptedException {
        selectAutoAndTeleOp();
        initAndWaitForStart();
        while(opModeIsActive()) {
            auto.loop();
//            left = Robot.get().getLeftOdo();
//            right = Robot.get().getRightOdo();
//            middle = Robot.get().getMidOdo();
//            telemetry.addData("left odo", left);
//            telemetry.addData("right odo", right);
//            telemetry.addData("mid odo", middle);
//            D = (right - left) / bMath.pi2;
//            telemetry.addData("D", D);
            Robot.get().update(true);
            telemetry.update();
        }
    }
}
