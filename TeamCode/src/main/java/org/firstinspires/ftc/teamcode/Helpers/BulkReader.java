package org.firstinspires.ftc.teamcode.Helpers;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Hardware.Config;
import org.firstinspires.ftc.teamcode.Hardware.Robot;

import java.util.HashMap;
import java.util.List;

public class BulkReader {
    List<LynxModule> allHubs;
    Robot robot;
    HardwareMap hardwareMap;
    public DcMotorEx odoLeft, odoRight, odoMiddle, launcherRight, launcherLeft;
    public HashMap<DcMotorEx, Integer> encoders;


    public BulkReader(){
        hardwareMap = DataHub.hardwareMap;
        allHubs = hardwareMap.getAll(LynxModule.class);
        setAllHubsToManualBulkRead();
        robot = Robot.get();
    }

    private void setAllHubsToManualBulkRead(){
        for (LynxModule module : allHubs) {
            module.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
        }
    }

    //Robot.update() should end by clearing cache
    public void clearCache(){
        for (LynxModule module : allHubs) {
            module.clearBulkCache();
        }
    }


}
