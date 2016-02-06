package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by thomasmatthews on 1/29/16.
 */
public class Team7104LeverFlippers extends Team7104Telemetry
{
    public Team7104LeverFlippers()
    {

    }
    /*
    @Override
    public void init()
    {
        super.init();
    }
    */

    @Override
    public void loop()
    {
        if (gamepad1.left_trigger > 0)
        {
            Flipper_Servo_Left.setPosition(0);
        }

        if (gamepad1.right_trigger > 0)
        {
            Flipper_Servo_Right.setPosition(1);
        }

        if (gamepad1.left_trigger == 0)
        {
            Flipper_Servo_Left.setPosition(.45);
        }

        if (gamepad1.right_trigger == 0)
        {
            Flipper_Servo_Right.setPosition(.55);
        }
        telemetry.addData("Flipper_Servo_Left", Flipper_Servo_Left.getPosition());
        telemetry.addData("Flipper_Servo_Right", Flipper_Servo_Right.getPosition());
    }

    @Override
    public void stop()
    {

    }
}
