package com.qualcomm.ftcrobotcontroller.opmodes;
import static java.lang.Math.*;

/**
 * Created by Daniel on 1/11/2016. Built off of TEAM7104MMAShoulderDaniel.java program.
 */
public class Team7104BucketDaniel extends Team7104Hardware
{

    void BucketPresets (float BucketPresetTarget)
    {
        float DistanceBetweenTarget_Current = (Bucket_Motor.getCurrentPosition()-BucketPresetTarget);
        if(abs(DistanceBetweenTarget_Current) > 80) //If the abs(EncoderReading-Target)>100....
        {
            if((DistanceBetweenTarget_Current) > 0) //If positive
            {Bucket_Motor.setPower(-80);}
            else
            {Bucket_Motor.setPower(80);}
        }
        else {
            if(abs(DistanceBetweenTarget_Current) < 25){ //If distance remaining is less than x, then stop the motor to help prevent motor burnout
                Bucket_Motor.setPower(0);
            }
            if((DistanceBetweenTarget_Current) > 0) //If positive
            {Bucket_Motor.setPower(abs(DistanceBetweenTarget_Current));} //Set power to remaining distance, must be less than 80
            else
            {Bucket_Motor.setPower(-abs(DistanceBetweenTarget_Current));}
        } //QUESTION: Is there a way to read the value (Bucket_Motor.getCurrentPosition()-BucketPresetTarget) and determine if it is
        //positive or negative?
    }


    public Team7104BucketDaniel()
    {

    }

    @Override
    public void init()
    {
        super.init();
    }
    //New Function Checking Remaining Distance from current encoder locating to location goal
    //If the abs(current encoder value-encoder goal value)>200, then 80 power
    //If the abs(current encoder value-encoder goal value)<200, then [abs(current encoder value-encoder goal value) * .4] power

    //New Function or if statement for normal joystick (y) power mode
    //if joystick > 0.01, then set power to (gamepad * 100)
    //(gamepad2.right_stick_y*100)
    //Or try ((gamepad2.right_stick_y ^3) *10)
    //Make a bigger slow zone for jittery fingers?

    @Override
    public void loop()
    {
        //Begin Bucket Preset Function
        boolean joystick_button = true; //Define the individual commands once buttons are assigned

        //Change '1,2 & 3' based on actual testing
        if(joystick_button)
        {
            BucketPresets (1);      //Preset for Collecting Debris
        }

        if(joystick_button)
        {
            BucketPresets (2);      //Preset for Dumping Debris into Conveyor
        }

        if(joystick_button)
        {
            BucketPresets (3);      //Preset for Storage
        }
        //END Preset Function

        //Begin Bucket Fine-Tuning
        // Set Bucket Motor power variable
        float BucketMotorJoystick = (gamepad2.right_stick_y);
        double Bucket_Motor_Power = pow(BucketMotorJoystick, 3);//Equivalent to x^3? Based on https://docs.oracle.com/javase/tutorial/java/data/beyondmath.html

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
        //END Bucket Fine-Tuning
    }

    @Override
    public void stop()
    {

    }
}
