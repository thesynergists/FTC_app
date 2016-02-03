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
        boolean left = gamepad1.left_bumper;
        boolean right = gamepad1.right_bumper;

        Conveyor_Belt_Control(left, right);

        boolean left1 = gamepad2.left_bumper;
        boolean right1 = gamepad2.right_bumper;

        //Sweep_Control(left1, right1);
    }

    @Override
    public void stop()
    {

    }
}
