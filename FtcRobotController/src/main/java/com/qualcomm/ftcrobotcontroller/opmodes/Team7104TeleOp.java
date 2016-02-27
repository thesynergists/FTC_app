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

import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.util.ElapsedTime;
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
        super.init();
        Scoop_Motor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
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


        if (gamepad2.left_bumper)
        {
            PullUp_Motors_SetPower(1.0);
        }

        if (gamepad2.left_trigger > 0)
        {
            PullUp_Motors_SetPower(-1.0);
        }

        if(!gamepad2.left_bumper && gamepad2.left_trigger == 0)
        {
            PullUp_Motors_SetPower(0);
        }



        if (gamepad2.right_bumper)
        {
            PullUp_Motors_SetPower(1.0);
        }

        if (gamepad2.right_trigger > 0)
        {
            PullUp_Motors_SetPower(-1.0);
        }

        if (!gamepad2.right_bumper && gamepad2.right_trigger == 0)
        {
            PullUp_Motors_SetPower(0);
        }



        if (gamepad2.left_stick_y > 0.1)
        {
            PullUp_Motor_String.setPower(-1.0);
        }

        if (gamepad2.left_stick_y < 0.1 && !gamepad2.left_bumper && gamepad2.left_trigger == 0 && !gamepad2.right_bumper && gamepad2.right_trigger == 0)
        {
            PullUp_Motor_String.setPower(0);
        }



        if (gamepad2.left_stick_y < -0.1)
        {
            PullUp_Motor_Tape.setPower(1.0);
        }

        if (gamepad2.left_stick_y > -0.1 && !gamepad2.left_bumper && gamepad2.left_trigger == 0 && !gamepad2.right_bumper && gamepad2.right_trigger == 0)
        {
            PullUp_Motor_Tape.setPower(0);
        }



        //STUFF FOR DRIVE TRAIN!!!
        float left = gamepad1.left_stick_y;
        float right = gamepad1.right_stick_y;

        // clip the right/left values so that the values never exceed +/- 1 (changed range to -.8 to .8 to prevent motor overdrive.)
        right = Range.clip(right, -.80F, .80F);
        left = Range.clip(left, -.80F, .80F);

         // write the values to the motors
        setPowerLeftMotor(left);
        setPowerRightMotor(right);


        //CLIMBER!!!
        Climber_dump(gamepad1.b);


        //FLIPPERS!!!

        if (gamepad2.dpad_left)
        {
            Flipper_Servo_Right.setPosition(1);
        }

        if (!gamepad2.dpad_left)
        {
            Flipper_Servo_Right.setPosition(.57);
        }

        if (gamepad2.dpad_right)
        {
            Flipper_Servo_Left.setPosition(0);
        }

        if (!gamepad2.dpad_right)
        {
            Flipper_Servo_Left.setPosition(.40);
        }


        /*
        if (gamepad2.left_trigger > 0)
        {
            Flipper_Servo_Right.setPosition(1);
        }

        if (gamepad2.left_trigger == 0)
        {
            Flipper_Servo_Right.setPosition(.57);
        }

        if (gamepad2.right_trigger > 0)
        {
            Flipper_Servo_Left.setPosition(0);
        }

        if (gamepad2.right_trigger == 0)
        {
            Flipper_Servo_Left.setPosition(.40);
        }
        */

        //THE CONVEYOR BELT!
        boolean conveyor_left = gamepad1.left_bumper;
        boolean conveyor_right = gamepad1.right_bumper;

        Conveyor_Belt_Control(conveyor_left, conveyor_right);



        //SCOOP!!!

        if (gamepad2.a)
        {
            Scoop_Motor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
            Scoop_Motor.setTargetPosition(640);       //Preset for Collecting Debris
            Scoop_Motor.setPower(.1);               //You set this as the max power the motor can have...
                                                    // (foresee issues depending on which side you are on)
        }

        if (gamepad2.b)
        {
            Scoop_Motor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
            Scoop_Motor.setTargetPosition(220);      //Preset for Dumping Debris into Conveyor
            Scoop_Motor.setPower(.1);
        }

        if (gamepad2.y)
        {
            Scoop_Motor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
            Scoop_Motor.setTargetPosition(30);     //Preset for Storage
            Scoop_Motor.setPower(.1);
        }

        if (gamepad2.x)
        {
            int hover_position = Scoop_Motor.getCurrentPosition();
            Scoop_Motor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
            Scoop_Motor.setTargetPosition(hover_position);
            Scoop_Motor.setPower(.1);
        }

        double Scoop_Motor_value = Range.clip(gamepad2.right_stick_y, -.1, .1);
        double Scoop_Motor_Power = Scoop_Motor_value;//Equivalent to x^3? Based on https://docs.oracle.com/javase/tutorial/java/data/beyondmath.htm

        //if joystick 2, right joystick
        //Stop Motor

        if (gamepad2.right_stick_y < 0.05 && gamepad2.right_stick_y > -0.05 && !gamepad2.a && !gamepad2.b && !gamepad2.y && !gamepad2.x)
        {
            Scoop_Motor.setMode((DcMotorController.RunMode.RUN_USING_ENCODERS));
            Scoop_Motor.setPower(0);
        }

        if (gamepad2.right_stick_y > 0.05 || gamepad2.right_stick_y < -.05)
        {
            Scoop_Motor.setMode((DcMotorController.RunMode.RUN_USING_ENCODERS));
            Scoop_Motor.setPower(Scoop_Motor_Power);
        }
        //END Scoop Fine-Tuning

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
        //telemetry.addData("Text", "*** Robot Data***");
        telemetry.addData("Encoder value", Scoop_Motor.getCurrentPosition());
        telemetry.addData("Scoop motor value", Scoop_Motor.getPower());
        /*
        telemetry.addData("left tgt pwr",  "left  pwr: " + String.format("%.2f", left));
        telemetry.addData("right tgt pwr", "right pwr: " + String.format("%.2f", right));
        */
    }

    /*
     * Code to run when the op mode is first disabled goes here
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#stop()
     */

    @Override
    public void stop()
    {
        Conveyor_servo.setPosition(.5);
        PullUp_Motor_String.setPower(0);
        PullUp_Motor_Tape.setPower(0);
        setPowerLeftMotor(0);
        setPowerRightMotor(0);
        Flipper_Servo_Left.setPosition(.40);
        Flipper_Servo_Right.setPosition(.57);
        Climber_servo.setPosition(0);
        Scoop_Motor.setPower(0);
        Sweep_servo.setPosition(.5);
    }
}
