package org.firstinspires.ftc.teamcode.Helpers;

//this class is gonna deal with complex numbers, probably will make "Position" class obsolete
public class ComplexNum {
    public double real; //real component
    public double imag; //imaginary component

    public ComplexNum(){
        real = 0;
        imag = 0;
    }
    public ComplexNum(double real, double imag){
        this.real = real;
        this.imag = imag;
    }
    public static ComplexNum newComplexNumPolar(double magnitude, double direction){
        return new ComplexNum(magnitude*Math.cos(direction), magnitude*Math.sin(direction));
    }
    public static ComplexNum add(ComplexNum a, ComplexNum b){
        ComplexNum Ans = new ComplexNum();
        Ans.real = a.real + b.real;
        Ans.imag = a.imag + b.imag;
        return Ans;
    }
    public static ComplexNum subtract(ComplexNum a, ComplexNum b){ //do a - b
        ComplexNum Ans = new ComplexNum();
        Ans.real = a.real - b.real;
        Ans.imag = a.imag - b.imag;
        return Ans;
    }
    public static ComplexNum multiply(ComplexNum a, ComplexNum b){
        ComplexNum Ans = new ComplexNum();
        Ans.real = (a.real * b.real) - (a.imag * b.imag);
        Ans.imag = (a.imag * b.real) + (a.real * b.imag);
        return Ans;
    }
    public ComplexNum safeRotateAboutOrigin(double angle){
        ComplexNum Ans = this.clone();
        Ans.rotateAboutOrigin(angle);
        return Ans;
    }
    public static ComplexNum exp(ComplexNum input, int iterations){
        ComplexNum Ans = input.clone();
        Ans.plusEquals(1);
        ComplexNum N = input.clone();
        for(int i = 2; i <= iterations; i++){
            N.timesEquals(input).divideEquals(i);
            Ans.plusEquals(N);
        }
        return Ans;
    }
    public static ComplexNum exp(ComplexNum input){
        return exp(input, 10);
    }
    public static ComplexNum ln(ComplexNum input){
        double imag = bMath.acis(input);
        double vectorLengthSquared = input.real * input.real + input.imag * input.imag;
        double vectorLength = Math.sqrt(vectorLengthSquared);
        double real = Math.log(vectorLength);
        return new ComplexNum(real, imag);
    }
    public static ComplexNum power(ComplexNum base, ComplexNum exponent){
        return exp(multiply(ln(base), exponent));
    }
    public ComplexNum rotateAboutOrigin(double angle){
        this.timesEquals(bMath.cis(angle));
        return this;
    }
    public ComplexNum timesEquals(ComplexNum input){
        this.equals(multiply(this, input));
        return this;
    }
    public void timesEquals(double input){
        this.real *= input;
        this.imag *= input;
    }
    public ComplexNum divideEquals(double input){
        this.real /= input;
        this.imag /= input;
        return this;
    }
    public void plusEquals(ComplexNum input){
        this.equals(add(this,input));
    }
    public void plusEquals(double inputReal){
        this.real += inputReal;}
    public void minusEquals(ComplexNum input){
        this.equals(subtract(this,input));
    }
    public void minusEquals(double input){
        this.real -= input;}
    public ComplexNum squared() {
        this.timesEquals(this);
        return this;
    }
    //changes the ComplexNum object to have values equal to the input
    public ComplexNum equals(ComplexNum input){
        real = input.real;
        imag = input.imag;
        return this;
    }

    public ComplexNum clone(){
        return new ComplexNum(this.real, this.imag);
    }

}