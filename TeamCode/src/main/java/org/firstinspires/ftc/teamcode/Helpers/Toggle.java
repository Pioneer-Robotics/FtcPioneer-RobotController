package org.firstinspires.ftc.teamcode.Helpers;

/**
 * This class is basically a wrapper class for booleans, but it allows you to negate the
 * the boolean by pressing a button. The intended use case is that there is some boolean
 * value used in an OpMode, and you want it to change every time you press a specific button
 * on the gamepad. Make that boolean a {@code Toggle} and call {@code toggle(boolean)}
 * every cycle using the desired button as the input to {@code toggle()}
 */
public class Toggle { //use this class when you want to have a boolean you toggle around
    private boolean state;
    private boolean buttonLast;
    //used to know if the state changed from false to true last cycle
    private boolean justChangedFlag;
    public Toggle(boolean startState){
        state = startState;
        buttonLast = false;
    }
    public void toggle(boolean button){
        justChangedFlag = false;
        if(button && !buttonLast){
            state = !state;
            justChangedFlag = true;
        }
        buttonLast = button;
    }

    /**
     * sets the value of {@code state} on this object
     * @param bool the value you want {@code state} to have
     */
    public void set(boolean bool){
        state = bool;
    }

    /**
     * gets the {@code state} value of this {@code toggle} object
     * @return {@code this.state}
     */
    public boolean getBool(){
        return state;
    }

    /**
     * used to know if the state changed from {@code false} to {@code true} last cycle
     * @return
     * <li> {@code true} if {@code state} changed from {@code false} to {@code true}
     * the last time {@code toggle()} was called on this object
     * <li>
     * {@code false} if {@code state} was already true the last time {@code toggle} was called
     * or if {@code state == false}
     */
    public boolean justChanged(){
        return justChangedFlag;
    }
}
