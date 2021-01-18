package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Helpers.DataHub;

public class Launcher {
    static final double SERVO_OUT = Config.launcherServoOut, SERVO_IN = Config.launcherServoIn;

    MotorPairEX motors;
    DcMotorEx m1;
    DcMotorEx m2;
    Servo flicker;

    double flickerTargetPos = SERVO_OUT;

    double targetVelocity = Config.defaultTargetLauncherSpeed;
    boolean launchRequested = false;
    boolean continuousFire = false;
    boolean waitForServo = true;
    boolean launchOverride = true;

    ElapsedTime launchTimer = new ElapsedTime();
    LaunchMode launchMode;
    public enum LaunchMode {
        IDLE,
        SPOOL,
        PUSH,
        RETRACT
    }




    Launcher(){
        m1 = DataHub.hardwareMap.get(DcMotorEx.class, Config.launcherMotor1);
        m2 = DataHub.hardwareMap.get(DcMotorEx.class, Config.launcherMotor2);
        motors = new MotorPairEX(m1, m2);
        motors.setDriveMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER)
                .setDriveMode(DcMotorEx.RunMode.RUN_USING_ENCODER)
                .setDirection(DcMotorEx.Direction.REVERSE)
        ;
        flicker = DataHub.hardwareMap.get(Servo.class, Config.launcherServo);
        launchMode = LaunchMode.IDLE;
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
                        launchOverride ||
                        (
                                Math.abs(targetVelocity - motors.getAverageVelocity()) <= Config.launchVelocityThreshold //Velocity good
                            && (!waitForServo || launchTimer.milliseconds() >= Config.launcherServoTime) //Servo has had enough time to move
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
                    if (launchRequested){
                        launchMode = LaunchMode.SPOOL;
                    } else {
                        launchMode = LaunchMode.IDLE;
                    }
                    launchTimer.reset();
                }
        }

        flicker.setPosition(flickerTargetPos);

        return this;
    }

    public void requestLaunch (){
        launchRequested = true;
    }
@Deprecated
    public void cancelLaunch (){
        launchRequested = false;

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

}


