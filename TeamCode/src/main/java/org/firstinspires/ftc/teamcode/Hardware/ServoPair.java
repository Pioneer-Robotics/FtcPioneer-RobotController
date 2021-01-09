package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.Servo;

class ServoPair {
    public Servo servo1, servo2;
    public boolean inverted = false;

    public ServoPair(Servo servo1, Servo servo2){
       new ServoPair(servo1, servo2, false);
    }

    public ServoPair(Servo servo1, Servo servo2, boolean inverted){
        this.servo1= servo1;
        this.servo2=servo2;
        this.inverted = inverted;
    }

    public ServoPair setServoPosition (float servoPosition){
        servo1.setPosition(servoPosition);
        servo2.setPosition(servoPosition * (inverted ? -1 : 1) );
        return this;
    }

}
