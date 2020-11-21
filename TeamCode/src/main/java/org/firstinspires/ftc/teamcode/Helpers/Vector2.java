package org.firstinspires.ftc.teamcode.Helpers;

import org.firstinspires.ftc.robotcore.external.matrices.VectorF;

// Two dimensional double vector
public class Vector2 {
    protected double x;
    protected double y;

    // Creates the vector (0,0)
    public Vector2() {
        x = 0;
        y = 0;
    }

    public Vector2(double xValue, double yValue) {
        x = xValue;
        y = yValue;
    }

    // Adds the x and y values of vector A and B and returns the resulting vector
    public static Vector2 add(Vector2 a, Vector2 b) {
        return new Vector2(a.x + b.x, a.y + b.y);
    }

    // Subtracts the x and y values of vector A and B and returns the resulting vector
    public static Vector2 subtract(Vector2 a, Vector2 b) {
        return new Vector2(a.x - b.x, a.y - b.y);
    }

    // Returns the dot produce of a and b, between -1 and 1
    public static double dot(Vector2 a, Vector2 b) {
        return (a.x * b.x) + (a.y * b.y);
    }

    // Returns the magnitude of this vector
    public static double magnitude(Vector2 value) {
        return Math.sqrt((value.x * value.x) + (value.y * value.y));
    }

    // Returns the squared magnitude of this vector, negligibly faster
    public static double magnitudeSq(Vector2 value) {
        return (value.x * value.x) + (value.y * value.y);
    }

    // Returns a heading vector from a radian angle measure
    public static Vector2 fromRadians(double angle) {
       return new Vector2(Math.cos(angle),Math.sin(angle));
    }

}
