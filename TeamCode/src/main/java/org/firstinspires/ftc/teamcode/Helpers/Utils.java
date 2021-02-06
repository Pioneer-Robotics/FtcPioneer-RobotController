package org.firstinspires.ftc.teamcode.Helpers;

public class Utils {

    /**
     * sets all the elements of a boolean array to false
     * @param array the array you want set to false
     * @return the same array you inputed, but mutated to everything is false
     */
    public static boolean[] setBooleanArrayToFalse(boolean[] array){
        for (int i = 0; i < array.length; i++){
            array[i] = false;
        }
        return array;
    }
}