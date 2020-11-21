package org.firstinspires.ftc.teamcode.Hardware;

import org.firstinspires.ftc.teamcode.Helpers.Vector2;

public class DriveTrain {

    // WIP, not commented or formatted
    public void travelToPoint(Vector2 endPoint, double minSpeed, double maxSpeed, double turnSpeed, double terminationDistance) {
        //TODO - using PID, rotate quickly towards our end point. Does not need to be accurate, (within +- 60 degrees of our endPoint)

        Vector2 directionToEndPoint = Vector2.zero;
        double startingDistanceToEndPoint = 1000;
        double currentDistanceToEndPoint = 1000;

        double powerRight = 0;
        double powerLeft = 0;

        double powerCoefficient = 0;

        startingDistanceToEndPoint = Vector2.magnitude(Vector2.subtract(getLocation(), endPoint));

        while (currentDistanceToEndPoint * currentDistanceToEndPoint > terminationDistance) {
            directionToEndPoint = Vector2.normalOf(Vector2.subtract(getLocation(), endPoint));
            currentDistanceToEndPoint = Vector2.magnitude(Vector2.subtract(getLocation(), endPoint));

            powerCoefficient = (currentDistanceToEndPoint / startingDistanceToEndPoint) * Math.PI;
            powerCoefficient = Math.sin(powerCoefficient);

            powerRight = (maxSpeed * powerCoefficient) + minSpeed;
            powerLeft = (maxSpeed * powerCoefficient) + minSpeed;
        }

        //TODO - stopWheelMotors
    }

    // TODO - implement me!
    public Vector2 getLocation() {
        return Vector2.zero;
    }

    // TODO - implement me!
    public double getRotation() {
        return 0;
    }
}
