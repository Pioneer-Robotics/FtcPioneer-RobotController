package org.firstinspires.ftc.teamcode.OpModes.Autos.DeliverWobbleGoal_ABC;

import org.firstinspires.ftc.teamcode.Hardware.Robot;

class GoToB extends GoToSquare {
    void goToSquareAndThenToShootPos() {

    }
    enum GOTOB{
        FORWARD,
        TURN,
        BACKWARD
    }
    GOTOB gotob;
    boolean[] moveAUTO = new boolean[15];
    double startingPos = 0;
    double DriveDistance = 0;

    public void loop() {
        switch(gotob){
            case FORWARD:
                moveAUTO[1] = false;
                driveDistance(150, .5, .15);
                if(moveAUTO[1]){
                    gotob = GOTOB.TURN;
                }
                break;
            case TURN:

                break;
        }
    }
    void driveDistance(double distance, double leftPower, double rightPower){
        if(startingPos == 0)
            startingPos = Robot.get().getLeftOdo();
        DriveDistance = startingPos + distance;
        moveAUTO[1] = false;
        Robot.get().setDrivePowers(leftPower, rightPower);
        if(Robot.get().getLeftOdo() >= DriveDistance){
            Robot.get().setDrivePowers(0,0);
            moveAUTO[1] = true;
            startingPos = 0;
        }
    }

    public void init() {
        gotob = GOTOB.FORWARD;
    }
}
