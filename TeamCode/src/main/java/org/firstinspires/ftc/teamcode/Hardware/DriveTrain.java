package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Helpers.Vector2;


 class DriveTrain {
    MotorPair leftMotors;
    MotorPair rightMotors;

    DriveTrain(){
        DcMotor leftTop = Robot.get().hardwareMap.get(DcMotor.class,Config.motorLT);
        DcMotor leftBottom = Robot.get().hardwareMap.get(DcMotor.class,Config.motorLB);
        leftMotors = new MotorPair(leftTop, leftBottom);

        DcMotor rightTop = Robot.get().hardwareMap.get(DcMotor.class,Config.motorRT);
        DcMotor rightBottom = Robot.get().hardwareMap.get(DcMotor.class,Config.motorRB);
        rightMotors = new MotorPair(rightTop, rightBottom);

        leftMotors.setDirection(DcMotor.Direction.FORWARD);
        rightMotors.setDirection(DcMotor.Direction.REVERSE);
        setDriveMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setDriveMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }
    void setMotorPowers(double leftPower, double rightPower){
        leftMotors.setPower(leftPower);
        rightMotors.setPower(rightPower);
    }
    void setDriveMode(DcMotor.RunMode mode) {
        leftMotors.setDriveMode(mode);
        rightMotors.setDriveMode(mode);
    }
    void setZeroPowerBehavior(DcMotor.ZeroPowerBehavior behavior){
        leftMotors.setZeroPowerBehavior(behavior);
        rightMotors.setZeroPowerBehavior(behavior);
    }

    // WIP, not commented or formatted
    void travelToPoint(Vector2 endPoint, double minSpeed, double maxSpeed, double turnSpeed, double terminationDistance) {
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

    }
    void rotatePID(double targetAngle){//TODO implement me!

    }
    public Vector2 getLocation() {
        return Robot.get().getLocation();
    }
    public double getRotation() {
        return Robot.get().getRotationRad();
    }
}
