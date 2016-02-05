package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by thomasmatthews on 1/29/16.
 */
public class Team7104LeverFlippers extends Team7104Hardware
{
    public Team7104LeverFlippers()
    {

    }
    
    @Override
    public void init()
    {
        super.init();
    }

    @Override
    public void loop()
    {
        if (gamepad1.left_trigger > 0)
        {
            Flipper_Servo_Left.setPosition(.75);
        }

        if (gamepad1.right_trigger > 0)
        {
            Flipper_Servo_Right.setPosition(.25);
        }

        if (gamepad1.left_trigger == 0)
        {
            Flipper_Servo_Left.setPosition(.5);
        }

        if (gamepad1.right_trigger == 0)
        {
            Flipper_Servo_Right.setPosition(.5);
        }
    }

    @Override
    public void stop()
    {

    }
}
