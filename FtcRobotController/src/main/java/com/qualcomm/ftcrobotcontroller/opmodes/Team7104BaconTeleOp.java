package com.qualcomm.ftcrobotcontroller.opmodes;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.Hardware;
import com.qualcomm.robotcore.util.Range;

//import com.qualcomm.robotcore.util.Range;
/**
 * TeleOp Mode
 * <p>
 * Enables control of the robot via the gamepad
 */
public class Team7104BaconTeleOp extends Team7104Telemetry
{

    double increment_total;
    boolean has_gone_to_zero;

    public Team7104BaconTeleOp()
    {


    }

    @Override
    public void init ()
    {
        super.init();
        increment_total = .5;
        Conveyor_servo.setPosition(.64);
        has_gone_to_zero = false;
    }

    @Override
    public void loop()
    {
             //For some reason, conveyor likes moving when Bacon is told to... Someone help!

        float joystick_value = Range.clip(gamepad2.left_stick_y, -1, 1);
        double increment_add;

        double deadzone = .5;   //Adjust the deadzone.
        boolean was_pressed = false;

        if (joystick_value == 0)
        {
            has_gone_to_zero = true;
        }

        if (has_gone_to_zero)
        {
            if (joystick_value > deadzone || joystick_value < -deadzone)
            {
                was_pressed = true;

            }
        }

        if (joystick_value < deadzone && joystick_value > -deadzone)
        {
            increment_add = 0;
            increment_total = increment_total + increment_add;

            telemetry.addData ("Servo value ", increment_total);

        }
        if (joystick_value > deadzone || joystick_value < -deadzone)
        {
            if(was_pressed)
            {
                for (int i = 0; i < 1; i++)
                {
                    increment_add = (joystick_value) / 5;
                    increment_total = increment_total + increment_add;
                }

                was_pressed = false;
                has_gone_to_zero = false;
            }

            telemetry.addData("Servo value ", increment_total);
            Bacon_servo.setPosition(increment_total);
        }
    }

    @Override
    public void stop()
    {

    }
}