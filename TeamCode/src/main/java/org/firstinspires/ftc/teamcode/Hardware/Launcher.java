package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Helpers.DataHub;
import org.firstinspires.ftc.teamcode.Helpers.Utils;

public class Launcher {
    static final double SERVO_OUT = Config.launcherServoOut, SERVO_IN = Config.launcherServoIn;
    MotorPairEX motors;
    DcMotorEx m1;
    DcMotorEx m2;
    Servo flicker;

    double flickerTargetPos = SERVO_OUT;

    private double targetVelocity = Config.defaultTargetLauncherSpeed;
    private double currentVelocity;
    private boolean launchRequested = false;
    private boolean continuousFire = false;
    private boolean waitForServo = true;
    private boolean launchOverride = true;

    ElapsedTime launchTimer;
    LaunchMode launchMode;
    public enum LaunchMode {
        IDLE,
        SPOOL,
        PUSH,
        RETRACT
    }

    private double[] velocityLog;
    private double[] velocityLogTime;
    private int velocityLogPointer;
    ElapsedTime velocityPointTime;



    Launcher(){
        m1 = DataHub.hardwareMap.get(DcMotorEx.class, Config.launcherMotor1);
        m2 = DataHub.hardwareMap.get(DcMotorEx.class, Config.launcherMotor2);
        motors = new MotorPairEX(m1, m2);
        motors.setDriveMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER)
                .setDriveMode(DcMotorEx.RunMode.RUN_USING_ENCODER)
                .setDirection(DcMotorEx.Direction.REVERSE)
                .setVelocityPIDfCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, Config.launcherPIDF);
        ;
        flicker = DataHub.hardwareMap.get(Servo.class, Config.launcherServo);
        launchMode = LaunchMode.IDLE;
        launchTimer = new ElapsedTime();

        velocityLogPointer = 0;
        velocityLog = new double[Config.VELOCITY_LOG_SIZE];
        velocityLogTime = new double[Config.VELOCITY_LOG_SIZE];
        velocityPointTime = new ElapsedTime();
        Utils.fillArray(velocityLog,0);
        Utils.fillArray(velocityLogTime,0);
    }
@Deprecated
    Launcher setPower(double power){
        motors.setPower(power);
        return this;
    }

    Launcher setLaunchMode(LaunchMode launchMode){
        this.launchMode = launchMode;
        return this;
    }

    Launcher updateLauncher (){
        //Writes current velocity to the velocityLog
        recordVelocity(motors.getAverageVelocity());
        //reads average velocity from log
        currentVelocity = getRollingAverage(Config.VELOCITY_AVERAGE_TIME_MS);
        switch (launchMode){
            default:
            case IDLE:
                motors.disable();
                if (launchRequested) {launchMode = LaunchMode.SPOOL;}
                break;
            case SPOOL:
                motors.enable();
                motors.setVelocity(targetVelocity);

                //Checks if the servo is in the right position and instructs the program to wait until it is, otherwise
                waitForServo = flickerTargetPos != SERVO_IN;
                if (waitForServo){ launchTimer.reset(); }
                flickerTargetPos = SERVO_IN;


                //Checks if all conditions are met and actuates the launcher if they are
                if ( launchRequested &&
                        (launchOverride ||
                        (
                                Math.abs(targetVelocity - currentVelocity) <= Config.launchVelocityThreshold //Velocity good
                            && (!waitForServo || launchTimer.milliseconds() >= Config.launcherServoTime) //Servo has had enough time to move
                        )
                        )

                ){
                    flickerTargetPos = SERVO_OUT;
                    launchTimer.reset();
                    launchMode = LaunchMode.PUSH;
                    launchRequested = continuousFire; // Keep launch requested if launcher is in continuous fire mode
                }
                break;
            case PUSH:
                if (launchTimer.milliseconds()>=Config.launcherServoTime){
//                    if (launchRequested){
//                        launchMode = LaunchMode.SPOOL;
//                    } else {
//                        launchMode = LaunchMode.IDLE;
//                    }
                    launchMode = LaunchMode.SPOOL;
                    launchTimer.reset();
                }
        }

        flicker.setPosition(flickerTargetPos);

        return this;
    }

    public void requestLaunch (){
        launchRequested = true;
    }

    public void setLaunchOverride (boolean launchOverride){
        this.launchOverride = launchOverride;
    }

    public void setContinuousFire (boolean continuousFire) {
        this.continuousFire = continuousFire;
    }

    public void fire (){
        if (launchMode  == LaunchMode.IDLE){
            launchMode =  LaunchMode.SPOOL;
        }
        launchRequested = true;
    }

     public void requestSpool(){
        if (launchMode == LaunchMode.IDLE){
            launchMode = LaunchMode.SPOOL;
        }
        launchRequested = false;
     }

     public void emergencyStop(){
        if (launchMode == LaunchMode.SPOOL){
            launchMode = LaunchMode.IDLE;
        }
        launchRequested = false;
     }

     public LaunchMode getLaunchMode() {return launchMode;}

     public double getLaunchVelocity() {return motors.getAverageVelocity();}
     public void setTargetVelocity(double targetVelocity) {this.targetVelocity = targetVelocity;}

     public int getLaunchPos() {return motors.getCurrentPositionM1();}


     private double getRollingAverage(double duration){
        double sum = 0;
        double time = 0;
        int num = 0;
        int i = velocityLogPointer;
        do{
            sum += velocityLog[i];
            time += velocityLogTime[i];
            i--;
            num++;
            if(i < 0){i = Config.VELOCITY_LOG_SIZE-1;}
        } while (time < duration);

        return sum/num;
     }

     private void recordVelocity(double velocity){
        velocityLogPointer++;
        if(velocityLogPointer >= Config.VELOCITY_LOG_SIZE){velocityLogPointer = 0;}
        velocityLog[velocityLogPointer] = velocity;
        velocityLogTime[velocityLogPointer] = velocityPointTime.milliseconds();
        velocityPointTime.reset();
     }

     public double getCurrentVelocity(){ return currentVelocity; }
     public double getCurrentVelocityDiff(){return Math.abs(targetVelocity - currentVelocity);}

     public void updatePIDF () {motors.setVelocityPIDfCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, Config.launcherPIDF);}

}


