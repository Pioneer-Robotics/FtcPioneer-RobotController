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
        initMotors();
        robot = Robot.get();
    }

    private void initMotors(){
        odoLeft = hardwareMap.get(DcMotorEx.class, Config.motorRT);
        odoRight = hardwareMap.get(DcMotorEx.class, Config.motorLT);
        odoMiddle = hardwareMap.get(DcMotorEx.class, Config.motorRB);
    }

    private void setAllHubsToManualBulkRead(){
        for (LynxModule module : allHubs) {
            module.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
        }
    }
    private void initHashMap(){
        encoders = new HashMap<DcMotorEx, Integer>(5);
        encoders.put(odoLeft, 0);
        encoders.put(odoRight, 0);
        encoders.put(odoMiddle, 0);
    }

    //Robot.update() should start by pulling data
    public void pullData(){
        encoders.put(odoLeft, odoLeft.getCurrentPosition());
        encoders.put(odoRight, odoRight.getCurrentPosition());
        encoders.put(odoMiddle, odoMiddle.getCurrentPosition());
    }

    //Robot.update() should end by clearing cache
    public void clearCache(){
        for (LynxModule module : allHubs) {
            module.clearBulkCache();
        }
    }


}
