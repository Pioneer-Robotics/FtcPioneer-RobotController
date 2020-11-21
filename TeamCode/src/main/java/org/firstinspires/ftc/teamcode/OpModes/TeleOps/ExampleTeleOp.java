package org.firstinspires.ftc.teamcode.OpModes.TeleOps;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.OpModes.TeleOps.SingleControllers.SingleController;
import org.firstinspires.ftc.teamcode.OpModes.TeleOps.SingleControllers.SingleController1Example;
import org.firstinspires.ftc.teamcode.OpModes.TeleOps.SingleControllers.SingleController2Example;

public class ExampleTeleOp extends TeleOpScript {
    ElapsedTime deltaTime = new ElapsedTime();
    SingleController controller1;
    SingleController controller2;

    @Override
    public void main() {
        init();
        controller1.main();
        controller2.main();
    }
    private void init(){
        controller1 = new SingleController1Example();
        controller1 = new SingleController2Example();
    }
}
