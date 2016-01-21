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
    public Team7104BaconTeleOp()
    {


    }

    @Override
    public void init ()
    {
        super.init();
        conveyor_servo.setPosition(.5);
        increment_total = .5;
    }

    @Override
    public void loop()
    {
        float joystick_value = Range.clip(gamepad2.left_stick_y, -1, 1);
        double increment_add;

        double deadzone = .1;       //Adjust the deadzone.

        if (joystick_value < deadzone && joystick_value > -deadzone)
        {
            increment_add = 0;
            increment_total = increment_total + increment_add;

            telemetry.addData ("Servo value: ", increment_total);

            Bacon_servo.setPosition(increment_total);
        }
        if (joystick_value > deadzone || joystick_value < -deadzone)
        {
            increment_add = (joystick_value)/5;
            increment_total = increment_total + increment_add;

            telemetry.addData ("Servo value ", increment_total);

            Bacon_servo.setPosition(increment_total);
        }
    }

    @Override
    public void stop()
    {

    }
}