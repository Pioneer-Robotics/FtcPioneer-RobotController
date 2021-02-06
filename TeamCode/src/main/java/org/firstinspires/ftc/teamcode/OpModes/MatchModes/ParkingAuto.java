//package org.firstinspires.ftc.teamcode.OpModes.MatchModes;
//
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//
//import org.firstinspires.ftc.teamcode.Hardware.Robot;
//import org.firstinspires.ftc.teamcode.OpModes.Autos.AutoTry_Seth;
//import org.firstinspires.ftc.teamcode.OpModes.TeleOps.TeleOpStandard;
//
//@TeleOp(name = "test auto1", group = "test")
//public class ParkingAuto extends GenericOpMode{
//    @Override
//    public void selectAutoAndTeleOp() {
//        auto = new AutoTry_Seth();
//        teleOp = new TeleOpStandard(); //not sure if this is right teleOp, good enough for now
//    }
//
//    @Override
//    public void runOpMode() throws InterruptedException {
//        selectAutoAndTeleOp();
//        initAndWaitForStart();
//        while(opModeIsActive()) {
//            runStandardLoop();
//        }
//        Robot.get().stopAllMotors();
//    }
//}
