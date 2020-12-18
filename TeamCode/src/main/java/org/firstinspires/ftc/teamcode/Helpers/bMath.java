package org.firstinspires.ftc.teamcode.Helpers;

import org.firstinspires.ftc.teamcode.Hardware.Config;

public class bMath {
    public static double pi = 3.14159265359;
    public static double pi2 = 2 * pi;
    public static double sq2 = 1.41421356237309;


    //converts odometry ticks to cm moved
    public static double odoTicksToCm(int ticks){
        return ticks * Config.odoTicksToCm;
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
    public static double acis(ComplexNum input){
        if(input.imag == 0){ //this means it's on the real number line
            switch ((int)sign(input.real)){
                case 1: return 0;
                case -1: return pi;

            }
        }
        double Ans = acot(input.imag / input.real);
        Ans *= sign(input.imag);
        return Ans;
    }
    public static double acot(double input){
        double Ans = Math.atan(input);
        if(Ans < 0){
            Ans = pi + Ans;
        }
        return Ans;
    }
    public static double sign(double input){
        if(input == 0){
            return 0.0;
        }
        return (input > 0 ? 1.0: -1.0);
    }
    public static double toDeg(double angleRad){
        return angleRad / pi * 180.0;
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
