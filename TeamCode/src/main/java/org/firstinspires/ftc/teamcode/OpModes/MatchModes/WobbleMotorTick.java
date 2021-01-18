package org.firstinspires.ftc.teamcode.OpModes.MatchModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.OpModes.Autos.NullAuto;
import org.firstinspires.ftc.teamcode.OpModes.TeleOps.TestValuesRelatedToWobbleMotor;

@TeleOp(name = "wobble tick measure")
public class WobbleMotorTick extends GenericOpMode{

    @Override
    public void selectAutoAndTeleOp() {
        teleOp = new TestValuesRelatedToWobbleMotor();
        auto = new NullAuto();
    }

    @Override
    public void runOpMode() throws InterruptedException {
        selectAutoAndTeleOp();
        initAndWaitForStart();
        while(opModeIsActive()){
            teleOp.loop();
            Robot.get().update();
            telemetry.update();
        }
    }
}
