package org.firstinspires.ftc.teamcode.Helpers;

public class Toggle { //use this class when you want to have a boolean you toggle around
    private boolean state;
    private boolean buttonLast;
    public Toggle(boolean startState){
        state = startState;
        buttonLast = false;
    }
    public void toggle(boolean button){
        if(button && !buttonLast){
            state = !state;
        }
        buttonLast = button;
    }
    public void set(boolean bool){
        state = bool;
    }
    public boolean getBool(){
        return state;
    }
}
