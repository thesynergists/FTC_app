package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by thomasmatthews on 1/22/16.
 */
public class Bacon_stupid_Teleop extends Team7104Telemetry
{

    public void Bacon_stupid_Teleop()
    {

    }

    @Override
    public void init ()
    {
        super.init();
    }

    @Override
    public void loop()
    {
        telemetry.addData("Joystick", gamepad2.left_stick_y);

        if (gamepad2.left_stick_y > 0)
        {
            Bacon_servo.setPosition(1);
        }

        if (gamepad2.left_stick_y < 0)
        {
            Bacon_servo.setPosition(0);
        }

        if (gamepad2.left_stick_y == 0)
        {
            //Bacon_servo.setPosition(.5);
        }
    }

    @Override
    public void stop()
    {

    }
}
