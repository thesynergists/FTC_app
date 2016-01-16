package com.qualcomm.ftcrobotcontroller.opmodes;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by thomasmatthews on 1/15/16.
 */
public class Drive_Train_Test extends Team7104Hardware
{
    public Drive_Train_Test()
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
        float stick = gamepad1.left_stick_y;
        stick = Range.clip(stick, -1, 1);
        if (stick >= .5 && stick <= 1)
        {
            motorLeft1.setPower(.75);
            motorLeft2.setPower(.75);

            motorRight1.setPower(-.75);
            motorRight2.setPower(-.75);
        }
        if (stick < .5)
        {
            motorLeft1.setPower(0);
            motorLeft2.setPower(0);
            motorRight1.setPower(0);
            motorRight2.setPower(0);
        }
    }

    @Override
    public void stop()
    {

    }
}
