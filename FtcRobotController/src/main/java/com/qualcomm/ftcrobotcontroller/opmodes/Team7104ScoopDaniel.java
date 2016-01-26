package com.qualcomm.ftcrobotcontroller.opmodes;
import static java.lang.Math.*;

import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Daniel on 1/11/2016. Built off of TEAM7104MMAShoulderDaniel.java program.
 */
public class Team7104ScoopDaniel extends Team7104Hardware
{

    void ScoopPresets (float ScoopPresetTarget)
    {
        float DistanceBetweenTarget_Current = (Scoop_Motor.getCurrentPosition()-ScoopPresetTarget);
        if(abs(DistanceBetweenTarget_Current) > 80) //If the abs(EncoderReading-Target)>100....
        {
            if((DistanceBetweenTarget_Current) > 0) //If positive
            {
                Scoop_Motor.setPower(-.60);
            }

            else
            {
                Scoop_Motor.setPower(.60);
            }
        }
        else
        {
            if(abs(DistanceBetweenTarget_Current) < 25)
            {

                Scoop_Motor.setPower(0);    //If distance remaining is less than x, then stop the motor to help prevent motor burnout.
            }

            if((DistanceBetweenTarget_Current) > 0) //If positive
            {
                Scoop_Motor.setPower(DistanceBetweenTarget_Current);    //Set power to remaining distance, must be less than 80.
            }

            else
            {
                Scoop_Motor.setPower(DistanceBetweenTarget_Current);
            }
        }
    }


    public Team7104ScoopDaniel()
    {

    }

    @Override
    public void init()
    {
        super.init();

        if (Scoop_Motor != null)
        {
            Scoop_Motor.setMode (DcMotorController.RunMode.RESET_ENCODERS);
        }
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
    public void start()
    {
        super.start();
    }

    @Override
    public void loop() {
        //Begin Scoop Preset Function
        boolean joystick_button = true; //Define the individual commands once buttons are assigned

        //Change '1,2 & 3' based on actual testing
        if (gamepad2.a) {
            ScoopPresets(1);      //Preset for Collecting Debris
        }

        if (gamepad2.b) {
            ScoopPresets(2);      //Preset for Dumping Debris into Conveyor
        }

        if (gamepad2.y) {
            ScoopPresets(3);      //Preset for Storage
        }
        //END Preset Function

        //Begin Scoop Fine-Tuning
        // Set Scoop Motor power variable
        double Scoop_Motor_value = Range.clip(gamepad2.right_stick_y, -.96, .96);
        double Scoop_Motor_Power = pow(Scoop_Motor_value, 3);//Equivalent to x^3? Based on https://docs.oracle.com/javase/tutorial/java/data/beyondmath.html

        //if joystick 2, right joystick
        //Stop Motor
        if (gamepad2.right_stick_y < 0.05 && gamepad2.right_stick_y > -0.05) {
            Scoop_Motor.setPower(0);
        }

        if (gamepad2.right_stick_y > 0.05 || gamepad2.right_stick_y < -.05) {
            Scoop_Motor.setPower(Scoop_Motor_Power);
        }
        //END Scoop Fine-Tuning
    }

    @Override
    public void stop()
    {

    }
}
