package com.qualcomm.ftcrobotcontroller.opmodes;
import static java.lang.Math.*;

/**
 * Created by Daniel on 1/11/2016. Built off of TEAM7104MMAShoulderDaniel.java program.
 */
public class Team7104BucketDaniel extends Team7104Hardware {
    public Team7104BucketDaniel()
    {

    }
    @Override
    public void init(){
        super.init();
    }
    //New Function Checking Remaining Distance from current encoder locating to location goal
    //If the abs(current encoder value-encoder goal value)>200, then 100 power
    //If the abs(current encoder value-encoder goal value)<200, then [abs(current encoder value-encoder goal value) * .4] power

    //New Function or if statement for normal joystick (y) power mode
    //if joystick > 0.01, then set power to (gamepad * 100)
    //(gamepad2.right_stick_y*100)
    //Or try ((gamepad2.right_stick_y ^3) *10)
    //Make a bigger slow zone for jittery fingers?
    @Override
    public void loop(){

        //Set Bucket Motor power variable
        float BucketMotorJoystick = (gamepad2.right_stick_y);
        float Bucket_Motor_Power = pow(BucketMotorJoystick, 3);//Equivalent to x^3? Based on https://docs.oracle.com/javase/tutorial/java/data/beyondmath.html

        //if joystick 2, right joystick
        //Stop Motor
        if(gamepad2.right_stick_y < 0.05 && gamepad2.right_stick_y > -0.05)
        {
            Bucket_Motor.setPower(0);
        }
        if(gamepad2.right_stick_y > 0.05)
        {
            Bucket_Motor.setPower(Bucket_Motor_Power);
        }
        if(gamepad2.right_stick_y < -0.05)
        {
            Bucket_Motor.setPower(-Bucket_Motor_Power);
        }
    }
    @Override
    public void stop(){}
}
