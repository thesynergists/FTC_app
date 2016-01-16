package com.qualcomm.ftcrobotcontroller.opmodes;
import static java.lang.Math.*;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Daniel on 1/15/2016.
 */
public class Team7104PullUpDaniel extends Team7104Hardware
{
    public Team7104PullUpDaniel()
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
        //If button a is pressed
        boolean was_pressed = false;

        if (gamepad2.right_bumper)
        {
            was_pressed = true;
        }

        while (was_pressed && PullUp_Motor.getCurrentPosition() < 200)    //200 is going to be determined as set distance for PullUp_Motor to extend.
        {
            PullUp_Motor.setPower(.80);

            if (gamepad2.right_bumper)
            {
                was_pressed = false;
            }
            if (gamepad2.right_trigger > 0)
            {
                was_pressed = false;
            }
        }

        if(!was_pressed || PullUp_Motor.getCurrentPosition() >= 200)
        {
            PullUp_Motor.setPower(0);
        }

        float bring_back = Range.clip(gamepad2.right_trigger, 0, .8F);

        if (bring_back > 0)
        {
            PullUp_Motor.setPower(-bring_back);
        }
    }

    @Override
    public void stop()
    {

    }
}
