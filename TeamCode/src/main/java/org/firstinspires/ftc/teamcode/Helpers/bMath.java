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

    public static double sqd(double value) {
        return (value * value);
    }

    double squareInputWithSign(double input){
        double output = input * input;
        if (input < 0){
            output = output * -1;
        }
        return output;
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
    public static double acis(ComplexNum input){ //works, but inelegant
        double Ans = 0;
        if(input.imag == 0){//on real axis
            if(input.real > 0) return 0;
            if(input.real < 0) return pi;
            if(input.real == 0) return 0; //arbitrary choice, obviously there is no good Ans
        }
        if(input.real == 0){//on imaginary axis
            Ans = pi / 2 * sign(input.imag);
        }
        if(input.real > 0){ //the point is in quadrants 1 or 4
            Ans = Math.atan(input.imag / input.real);
        }
        if(input.real < 0){ //in quadrants 2 or 3
            Ans = Math.atan(input.imag / input.real) + Math.PI * sign(input.imag);
        }
        return Ans;
    }
    public static double acot(double input){ //verified
        return pi / 2 - Math.atan(input);
    }
    public static int sign(double input){
        if(input == 0){
            return 0;
        }
        return (input > 0 ? 1: -1);
    }

    /**
     * puts an angle into the range -π to π
     * @param angleRadians the angle you want "regularized" in radians
     * @return the corresponding angle in the range -π to π
     */
    public static double regularizeAngleRad(double angleRadians){
        ComplexNum num = cis(angleRadians);
        double ans = acis(num);
        return ans;
    }

    /**
     * puts an angle into the range -180° to 180°
     * @param angleDegrees the angle you want "regularized" in degrees
     * @return the corresponding angle in the range -180° to 180°
     */
    public static double regularizeAngleDeg(double angleDegrees){
        double angleRad = Math.toRadians(angleDegrees);
        double ans = regularizeAngleRad(angleRad);
        ans = Math.toDegrees(ans);
        return ans;
    }

    /**
     * meant to allow help you find the shortest angle seperating the angle you're at from the angle
     * you want
     * @param angle1 the angle you want the robot to be at
     * @param angle2 the current angle of the robot
     * @return an angle between -pi and pi that is the amount you should turn
     */
    public static double subtractAnglesRad(double angle1, double angle2){
        angle1 = regularizeAngleRad(angle1);
        angle2 = regularizeAngleRad(angle2);
        double ans = angle1 - angle2;
        ans = regularizeAngleRad(ans);
        return ans;
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
