/* Copyright (c) 2014 Qualcomm Technologies Inc

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Qualcomm Technologies Inc nor the names of its contributors
may be used to endorse or promote products derived from this software without
specific prior written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. */

package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.util.Range;

import static java.lang.Math.pow;

/**
 * TeleOp Mode
 * <p>
 * Enables control of the robot via the gamepad
 */
public class Team7104TeleOp extends Team7104Hardware
{

    /**
     * Constructor
     */
    public Team7104TeleOp()
    {

    }

    /*
     * Code to run when the op mode is first enabled goes here
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#start()
     */
    boolean was_pressed_y = false;
    boolean was_pressed_a = false;
    boolean bypass_y = false;
    boolean bypass_a = false;
    boolean the_stop_button_y = false;
    boolean the_stop_button_a = false;

    @Override
    public void init()
    {
		/*
		 * Use the hardwareMap to get the dc motors and servos by name. Note
		 * that the names of the devices must match the names used when you
		 * configured your robot and created the configuration file.
		 */
		
		/*
		 * For the demo Tetrix K9 bot we assume the following,
		 *   There are two motors "motor_1" and "motor_2"
		 *   "motor_1" is on the right side of the bot.
		 *   "motor_2" is on the left side of the bot and reversed.
		 *   
		 * We also assume that there are two servos "servo_1" and "servo_6"
		 *    "servo_1" controls the arm joint of the manipulator.
		 *    "servo_6" controls the claw joint of the manipulator.
		 */

        super.init();
    }

    /*
     * This method will be called repeatedly in a loop
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#run()
     */

    @Override
    public void loop()
    {

		/*
		 * Gamepad 1
		 * 
		 * Gamepad 1 controls the motors via the left stick, and it controls the
		 * wrist/claw via the a,b, x, y buttons
		 */

        // throttle: left_stick_y ranges from -1 to 1, where -1 is full up, and
        // 1 is full down
        // direction: left_stick_x ranges from -1 to 1, where -1 is full left
        // and 1 is full right
        float scoop = gamepad2.right_stick_y;

        Scoop_Motor.setPower(scoop);


        //STUFF FOR DRIVE TRAIN!!!
        float left = gamepad1.left_stick_y;
        float right = gamepad1.right_stick_y;

        // clip the right/left values so that the values never exceed +/- 1 (changed range to -.8 to .8 to prevent motor overdrive.
        right = Range.clip(right, -.80F, .80F);
        left = Range.clip(left, -.80F, .80F);

         // write the values to the motors
        setPowerLeftMotor(left);
        setPowerRightMotor(right);




        //THE CONVEYOR BELT!
        boolean conveyor_left = gamepad1.left_bumper;
        boolean conveyor_right = gamepad1.right_bumper;

        Conveyor_Belt_Control(conveyor_left, conveyor_right);



        //LEVER FLIPPER!
        float lever_left = gamepad1.left_trigger;
        float lever_right = gamepad1.right_trigger;
        if()





        //Scoop!!!
        //Begin Scoop Fine-Tuning
        //Set Scoop Motor power variable
        double Scoop_Motor_value = Range.clip(gamepad2.right_stick_y, -.84, .84);
        //double Scoop_Motor_Power = pow(Scoop_Motor_value, 3);//Equivalent to x^3? Based on https://docs.oracle.com/javase/tutorial/java/data/beyondmath.htm
        double Scoop_Motor_Power = Scoop_Motor_value;
        //if joystick 2, right joystick
        //Stop Motor
        if (gamepad2.right_stick_y < 0.05 && gamepad2.right_stick_y > -0.05)
        {
            Scoop_Motor.setPower(0);
            telemetry.addData("Scoop motor value", Scoop_Motor.getPower());
        }

        if (gamepad2.right_stick_y > 0.05 || gamepad2.right_stick_y < -.05)
        {
            Scoop_Motor.setPower(Scoop_Motor_Power);
            telemetry.addData("Scoop motor value", Scoop_Motor.getPower());
        }
        //END Scoop Fine-Tuning

        telemetry.addData("Encoder value", Scoop_Motor.getCurrentPosition());




        //We still need preset encoder values and buttons. The above is a temporary manual workaround.








        //Sweeper!!!


        boolean locked_controls = false;
        if (gamepad1.a && gamepad1.y)       //Pressing A and Y buttons at same time will reset Sweeper.
        {
            locked_controls = true;
            Sweep_servo.setPosition(.5);
        }




        if (the_stop_button_a)
        {
            if (!gamepad1.a)
            {
                the_stop_button_a = false;
            }
        }

        if (!the_stop_button_a)
        {
            if (gamepad1.a)
            {
                was_pressed_a = true;
            }
        }


        if (was_pressed_a)
        {
            if (!locked_controls)
            {
                Sweep_servo.setPosition(.1);        //FORWARD SWEEPER!!!
            }

            if (was_pressed_y)
            {
                was_pressed_y = false;
                bypass_y = false;
            }

            if (!gamepad1.a)
            {
                bypass_a = true;
            }

            if (bypass_a)
            {
                if(gamepad1.a)
                {
                    was_pressed_a = false;
                    bypass_a = false;
                    the_stop_button_a = true;
                }
            }
        }

        if (!was_pressed_a && !was_pressed_y)
        {
            Sweep_servo.setPosition(.5);
        }

        if (gamepad1.a && gamepad1.y)
        {
            locked_controls = true;
            Sweep_servo.setPosition(.5);
        }





        if (the_stop_button_y)
        {
            if (!gamepad1.y)
            {
                the_stop_button_y = false;
            }
        }

        if (!the_stop_button_y)
        {
            if (gamepad1.y)
            {
                was_pressed_y = true;
            }
        }


        if (was_pressed_y)
        {
            if (!locked_controls)
            {
                Sweep_servo.setPosition(.9);        //REVERSE SWEEPER!!!
            }

            if (was_pressed_a)
            {
                was_pressed_a = false;
                bypass_a = false;
            }

            if (!gamepad1.y)
            {
                bypass_y = true;
            }

            if (bypass_y)
            {
                if (gamepad1.y)
                {
                    was_pressed_y = false;
                    bypass_y = false;
                    the_stop_button_y = true;
                }
            }
        }


        if (locked_controls)
        {
            was_pressed_y = false;
            was_pressed_a = false;
            bypass_y = false;
            bypass_a = false;
            the_stop_button_y = false;
            the_stop_button_a = false;
        }

        if (!was_pressed_a && !was_pressed_y)
        {
            Sweep_servo.setPosition(.5);
        }




        /*float wrist_elevation = gamepad2.left_stick_y;


        wrist_elevation = Range.clip(wrist_elevation, -1, 1);
        double converted_wrist_elevation = (wrist_elevation + 1)/2;
        // scale the joystick value to make it easier to control
        // the robot more precisely at slower speeds.

        right = (float)scaleInput(right);
        left =  (float)scaleInput(left);



		//Make the IF move up and down based on controller
		IronFist_change_elevation(converted_wrist_elevation);
        */



        /*
        Variables that will hold values showing
        degree that left and right bumpers
        are pressed in order to control Iron Fist rotation
        (trigger buttons are analog, so adjustments were made.
        */

        /*
        float wrist_rotation_left = gamepad2.left_trigger;
        float wrist_rotation_right = gamepad2.right_trigger;

        wrist_rotation_left = Range.clip(wrist_rotation_left, 0, 1);
        wrist_rotation_right = Range.clip(wrist_rotation_right, 0, 1);

        //Split the range of 0 to 1 of the rotation servo between the two triggers.
        double converted_wrist_rotation_left = (1 - wrist_rotation_left)/2;
        double converted_wrist_rotation_right = (wrist_rotation_right + 1)/2;

        //Control IF rotation.
        Iron_Fist_rotate(converted_wrist_rotation_left, converted_wrist_rotation_right);
        */

        /*
        //Make it stop!!!
        if(converted_wrist_elevation == .5)
        {
            IronFist_stop_elevation();
        }
        not necessary */


		/*
		 * Send telemetry data back to driver station. Note that if we are using
		 * a legacy NXT-compatible motor controller, then the getPower() method
		 * will return a null value. The legacy NXT-compatible motor controllers
		 * are currently write only.
		 */
        telemetry.addData("Text", "*** Robot Data***");
        telemetry.addData("left tgt pwr",  "left  pwr: " + String.format("%.2f", left));
        telemetry.addData("right tgt pwr", "right pwr: " + String.format("%.2f", right));
    }

    /*
     * Code to run when the op mode is first disabled goes here
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#stop()
     */

    @Override
    public void stop()
    {

    }
}
