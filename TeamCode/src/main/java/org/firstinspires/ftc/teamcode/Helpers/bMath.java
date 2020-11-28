package org.firstinspires.ftc.teamcode.Helpers;

import org.firstinspires.ftc.teamcode.Hardware.Config;

public class bMath {

    //converts odometry ticks to cm moved
    public static double odoTicksToCm(int ticks){
        return ticks * Config.odoTicksToCm;
    }
    public static double pi() {
        return 3.14159265359;
    }
    public static double pi2() {
        return pi() * 2;
    }
    public static double sq2() {
        return 1.41421356237309;
    }
    public static double squared(double value) {
        return (value * value);
    }

    //Clamp methods are to constrain values into a range
    public static double Clamp(double value, double min, double max) {
        double Ans = value;
        if (value >= max) {
            Ans = max;
        }
        if (value <= min) {
            Ans = min;
        }
        return Ans;
    }
    public static double Clamp(double value) { //Clamps between 1 and 0
        double Ans = value;
        if (value >= 1) {
            Ans = 1;
        }
        if (value <= 0) {
            Ans = 0;
        }
        return Ans;
    }
    public static long Clamp(long value, long min, long max) {
        long Ans = value;
        if (value >= max) {
            Ans = max;
        }
        if (value <= min) {
            Ans = min;
        }
        return Ans;
    }
    //End of clamp methods

    @Deprecated
    public static double mod(double value, double modulus) { //another way to do mod
        //mod(a,b) == a % b
        return value - Math.floor(value / modulus) * modulus;
    }
    //cis(theta) = cos(theta) + ( i * sin(theta) )
    public static ComplexNum cis(double angle){
        ComplexNum Ans = new ComplexNum();
        Ans.real = Math.cos(angle);
        Ans.imag = Math.sin(angle);
        return Ans;
    }
    public static double sign(double input){
        if(input == 0){
            return 0.0;
        }
        return (input > 0 ? 1.0: -1.0);
    }

    //toRadians and toDegrees are both in the standard Math class, so why did we make them again?
    @Deprecated
    public static double toRadians(double degrees) {
        return degrees * 0.0174;
    }
    @Deprecated
    public static double toDegrees(double radians) {
        return radians * 57.2957;
    }
}
