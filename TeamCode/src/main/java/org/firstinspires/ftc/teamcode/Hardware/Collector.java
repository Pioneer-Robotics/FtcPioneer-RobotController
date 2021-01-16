package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Helpers.Toggle;

public class Collector{


    ServoPair servos;
    DcMotor collectorMotor;
    Toggle collectorON;
    boolean collecting;
    float collectorSpeed;

    enum CollectorModes {
        START,
        SERVO_DOWN,
        MOTORS_ON,
        MOTORS_OFF,
        SERVO_UP,
    }

    Collector(){
        servos = new ServoPair(Robot.get().hardwareMap.get(Servo.class,Config.collectorServoLeft),
                 Robot.get().hardwareMap.get(Servo.class,Config.collectorServoRight),
                true);
        collectorMotor = Robot.get().hardwareMap.get(DcMotor.class,Config.collectorMotor);
        collectorMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        collectorSpeed = 1;
        collecting=false;
    }

    public Collector setCollectorSpeed(float collectorSpeed){
        this.collectorSpeed = collectorSpeed;
        return this;
    }

    public Collector startCollecting(){
        servos.setServoPosition(Config.SERVO_DOWN_POS);
        collectorMotor.setPower(collectorSpeed);
        collecting=true;
        return this;
    }

    public Collector stopCollecting(){
        servos.setServoPosition(Config.SERVO_UP_POS);
        collectorMotor.setPower(0);
        collecting=false;
        return this;
    }



    public boolean isCollecting(){
        return collecting;
    }

//   private CollectorModes setCollectorMode(CollectorModes collectorMode) {
//
//        for (int i=0; i<3; i++) {
//            switch (collectorMode) {
//                case START: {
//                    collectorMode = CollectorModes.SERVO_DOWN;
//                }
//                break;
//                case SERVO_DOWN:
//                    if (gamepad.x) {
//                        collectorMode = CollectorModes.START;
//                    } else {
//                        if (gamepad.dpad_right) {
//                            servo1.setPosition(0.1);//TO DO
//                            servo2.setPosition(-0.1);//TO DO
//                            collectorMode = CollectorModes.MOTORS_ON;
//                            collecetorMotor.setDrivePowers(0.5);
//                        }
//                    }
//                    break;
//                case MOTORS_ON: {
//                    if (gamepad.x) {
//                        collectorMode = CollectorModes.START;
//                    } else {
//                        collectorMode = CollectorModes.MOTORS_OFF;
//                        collectorMotor.getPostion();
//                        if (collectorMotor.getPosition() > 0) {
//                            collectorMotor.setDrivePowers(0);
//                        }
//                        collectorMode = CollectorModes.SERVO_UP;
//                    }
//                }
//                break;
//                case SERVO_UP: {
//                    if (gamepad.x) {
//                        collectorMode = CollectorModes.START;
//                    } else {
//                        servo1.setPosition(-0.1);
//                        servo2.setPosition(0.1);
//                    }
//                }
//            }
//        }
//        return collectorMode;
//    }
    }


