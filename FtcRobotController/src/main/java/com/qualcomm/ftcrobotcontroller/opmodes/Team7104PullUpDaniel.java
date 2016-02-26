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

    boolean was_pressed_pull = false;         //Will help control the while loop and other commands.
    boolean bypass_pull = false;             //This will act as a control to determine when the right bumper has been pressed for a second time.
    boolean the_stop_button_pull = false;

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

        //Temporary stuff.
        if (gamepad2.left_bumper)
        {
            PullUp_Motors_SetPower(.8);
        }
        if(!gamepad2.left_bumper && gamepad2.left_trigger == 0)
        {
            PullUp_Motors_SetPower(0);
        }

        if (gamepad2.left_trigger > 0)
        {
            PullUp_Motors_SetPower(-.8);
        }

        if (gamepad2.dpad_right)
        {
            PullUp_Motor_Tape.setPower(.8);
        }

        if (gamepad2.dpad_left)
        {
            PullUp_Motor_Tape.setPower(-.8);
        }

        if (!gamepad2.dpad_left && !gamepad2.dpad_right && !gamepad2.left_bumper && gamepad2.left_trigger == 0)
        {
            PullUp_Motor_Tape.setPower(0);
        }

        if (gamepad2.dpad_up)
        {
            PullUp_Motor_String.setPower(-.6);
        }

        if (gamepad2.dpad_down)
        {
            PullUp_Motor_String.setPower(.6);
        }

        if (!gamepad2.dpad_down && !gamepad2.dpad_up && !gamepad2.left_bumper && gamepad2.left_trigger == 0)
        {
            PullUp_Motor_String.setPower(0);
        }



        if (the_stop_button_pull)
        {
            if (!gamepad2.right_bumper && gamepad2.right_trigger == 0)
            {
                the_stop_button_pull = false;
            }
        }

        if (!the_stop_button_pull)
        {
            if (gamepad2.right_bumper)
            {
                was_pressed_pull = true;
            }
        }

        if (was_pressed_pull)
        {
            if (PullUp_Motor_String.getCurrentPosition() < 5000)
            {
                PullUp_Motors_SetPower(.80);
            }

            if (!gamepad2.right_bumper)
            {
                bypass_pull = true;
            }

            if (bypass_pull)
            {
                if (gamepad2.right_bumper || gamepad2.right_trigger > 0)
                {
                    was_pressed_pull = false;
                    bypass_pull = false;
                    the_stop_button_pull = true;
                }
            }
        }

        /*
        while (was_pressed && PullUp_Motor.getCurrentPosition() < 200)    //200 is going to be determined as set distance for PullUp_Motor to extend.
        {
            PullUp_Motor.setPower(.80);     //As long as motor has not reached preset limit and
                                        // is still manually allowed to move, arm will extend.

            if (!gamepad2.right_bumper)     //Once right bumper has been released once, robot will be ready to respond if it is pressed again.
            {
                bypass = true;
            }

            if (gamepad2.right_trigger > 0) //If the right trigger is pressed, while loop will not loop again, acting as an alternate stop method.
            {
                was_pressed = false;
            }
            if (bypass)                     //If right bumper has been pressed and released once, the second press will cause the loop to stop.
            {
                if (gamepad2.right_bumper)
                {
                    was_pressed = false;
                }
            }
        }



        if (!was_pressed_pull || PullUp_Motor_String.getCurrentPosition() >= 5000)    //After one of the loop conditions has become false, set motor to stop.
        {
            PullUp_Motors_SetPower(0);
        }
        */

        float bring_back = Range.clip(gamepad2.right_trigger, 0, .8F);  //At any time (as long as robot is not moving motor forward with while loop),
        //the right trigger may be used to cause the arm to retract.

        if (bring_back > 0)  //At any time (as long as robot is not moving motor forward with while loop),
        //the right trigger may be used to cause the arm to retract.
        {
            PullUp_Motors_SetPower(-bring_back);
        }
        //telemetry.addData("PullUpEncoder_String", PullUp_Motor_String.getCurrentPosition());
        //telemetry.addData("PullUpPower_String", PullUp_Motor_String.getPower());

        //With DT
        //STUFF FOR DRIVE TRAIN!!!
        float left = gamepad1.left_stick_y;
        float right = gamepad1.right_stick_y;

        // clip the right/left values so that the values never exceed +/- 1 (changed range to -.8 to .8 to prevent motor overdrive.
        right = Range.clip(right, -.80F, .80F);
        left = Range.clip(left, -.80F, .80F);

        // write the values to the motors
        setPowerLeftMotor(left);
        setPowerRightMotor(right);
    }

    @Override
    public void stop()
    {

    }
}
