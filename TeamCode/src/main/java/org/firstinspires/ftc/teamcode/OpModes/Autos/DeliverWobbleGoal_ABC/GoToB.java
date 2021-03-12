package org.firstinspires.ftc.teamcode.OpModes.Autos.DeliverWobbleGoal_ABC;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Hardware.Robot;

class GoToB extends GoToSquare {
    ElapsedTime deltaTime;

    AUTOB autoB;

    boolean check = false;
    boolean timer = false;
    boolean Scan = false;
    boolean Case = false;

    double begDis;
    double targetDistance;
    double distance;

    double angle;

    enum AUTOB {
        START,
        WAIT,
        BACKWARD,
        TURN,
        FORWARD,
        TURN2,
        BACKWARD2,
        DONE
    }

    public GoToB(){
        done = false;
        deltaTime = new ElapsedTime();
        autoB = AUTOB.START;
    }

    void goToSquareAndThenToShootPos() {
        angle = Robot.angle();
        switch (autoB){
            case START:
                setDistances(.95, .05,98, true);
                if(Case){
                    Case = false;
                    autoB = AUTOB.WAIT;
                }
                break;
            //TODO delete if time does not work out with limit
            case WAIT:
                if(!timer) {
                    deltaTime.reset();
                    timer = true;
                }
                if(deltaTime.seconds() > 1){
                    autoB = AUTOB.BACKWARD;
                }
                break;
            case BACKWARD:
                setDistances(.35,.35,-30,true);
                if(Case){
                    Case = false;
                    autoB = AUTOB.TURN;
                }
                break;
            case TURN:
                turn(.35,-85, "right", true);
                if (Case){
                    autoB = AUTOB.FORWARD;
                    Case = false;
                }
                break;
            case FORWARD:
                setDistances(.25, .25,30, true);
                if(Case){
                    Case = false;
                    autoB = AUTOB.TURN2;
                }
                break;
            case TURN2:
                turn(.35, -175, "right", true);
                if(Case){
                    autoB = AUTOB.BACKWARD2;
                    Case = false;
                }
                break;
            case BACKWARD2:
                setDistances(.25,.25,45, true);
                if(Case){
                    Case = false;
                    autoB = AUTOB.DONE;
                }
                break;
            //finishes GoToABC
            case DONE:
                done = true;
                break;
        }
    }

    public void setDistances(double left, double right, double distance, boolean nextCase){
        //need this to determine where we are
        this.distance = Robot.get().getRightOdo();
        //creating this just once instead of thousands of times
        //also this.distance is main file distance called each loop, distance is chosen distance
        if(!Scan) {
            begDis = this.distance;
            targetDistance = this.distance + distance;
            Scan = true;
        }
        if(distance > 0){ //if you wanna go forward
            Robot.get().setDrivePowers(left, right);
        }
        else if(distance < 0){ //if you wanna go backward
            Robot.get().setDrivePowers(-left, -right);
        }
        //checks where we are going and if we have gotten there depending which direction
        if(this.distance >= targetDistance && targetDistance > begDis){
            Robot.get().setDrivePowers(0,0);
            check = true;
        }
        else if(this.distance <= targetDistance && targetDistance < begDis){
            Robot.get().setDrivePowers(0,0);
            check = true;
        }
        /*REMEMBER TO SET CASE = FALSE AFTER USE OR ELSE IT WILL NOT RUN AGAIN**/
        if(nextCase && check){
            Case = true;
            check = false;
            Scan = false;
        }
    }

    //Simple turn method, I believe Joe should be working on a more accurate one but this works for the time being.
    public void turn(double power, double turn, String direction, boolean nextCase){
        angle = Robot.angle();
        if(direction.equals("right")) {
            Robot.get().setDrivePowers(power, -power);
        }
        else if(direction.equals("left")){
            Robot.get().setDrivePowers(-power, power);
        }
        if(angle >= turn && turn > 0){
            Robot.get().setDrivePowers(0,0);
            this.check = true;
        }
        else if (angle <= turn && turn < 0){
            Robot.get().setDrivePowers(0,0);
            this.check = true;
        }
        if(nextCase && this.check){
            Case = true;
            this.check = false;
        }
    }
}