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

public class Team7104TeleOp extends Team7104Hardware
{

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


    boolean was_pressed_left_flipper = false;
    boolean was_pressed_right_flipper = false;
    boolean bypass_left_flipper = false;
    boolean bypass_right_flipper = false;
    boolean the_stop_button_left_flipper = false;
    boolean the_stop_button_right_flipper = false;


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
        //PullUp!!!
        /*if (gamepad2.left_bumper)
        {
            PullUp_Motors_SetPower(1.0);
        }

        if (gamepad2.left_trigger > 0.2)
        {
            PullUp_Motors_SetPower(-1.0);
        }*/

        //Climber control variable!
        if (!gamepad2.a && !gamepad2.b && !gamepad2.y && gamepad2.right_stick_y > -.05 && gamepad2.right_stick_y < .05)
        {
            climber_bypass = false;
        }
        if (gamepad2.right_bumper)
        {
            PullUp_Motors_SetPower(1.0);
        }
        if (gamepad2.right_trigger > 0.2)
        {
            PullUp_Motors_SetPower(-1.0);
        }

        if (gamepad2.left_stick_y > 0.1)
        {
            PullUp_Motor_Tape.setPower(-gamepad2.left_stick_y);
        }
        if (gamepad2.left_stick_y < 0.1 && !gamepad2.right_bumper && gamepad2.right_trigger <= 0.2)
        {
            PullUp_Motor_Tape.setPower(0);                              //The tape stop.
        }



        if (gamepad2.left_stick_y < -0.1)
        {
            //PullUp_Motor_String.setPower(-gamepad2.left_stick_y);
        }

        if (gamepad2.left_stick_y > -0.1 && !gamepad2.right_bumper && gamepad2.right_trigger <= 0.2)
        {
            PullUp_Motor_String.setPower(0);                            //The string stop.
        }


        if (gamepad2.left_stick_y > -0.1 && gamepad2.left_stick_y < 0.1 && !gamepad2.right_bumper && gamepad2.right_trigger <= 0.2)
        {
            PullUp_Motors_SetPower(0);                                  //Both stop.
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
        if (!climber_bypass)
        {
            Climber_dump(gamepad1.b);
        }


        //FLIPPERS!!!
        if (the_stop_button_left_flipper)
        {
            if (!gamepad2.dpad_right)
            {
                the_stop_button_left_flipper = false;
            }
        }

        if (!the_stop_button_left_flipper)
        {
            if (gamepad2.dpad_right)
            {
                was_pressed_left_flipper = true;
            }
        }

        if (was_pressed_left_flipper)
        {
            Flipper_Servo_Left.setPosition(0);

            if (!gamepad2.dpad_right)
            {
                bypass_left_flipper = true;
            }

            if (bypass_left_flipper)
            {
                if (gamepad2.dpad_right)
                {
                    was_pressed_left_flipper = false;
                    bypass_left_flipper = false;
                    the_stop_button_left_flipper = true;
                }
            }
        }

        if (!was_pressed_left_flipper)
        {
            Flipper_Servo_Left.setPosition(.59);
        }



        if (the_stop_button_right_flipper)
        {
            if (!gamepad2.dpad_left)
            {
                the_stop_button_right_flipper = false;
            }
        }

        if (!the_stop_button_right_flipper)
        {
            if (gamepad2.dpad_left)
            {
                was_pressed_right_flipper = true;
            }
        }

        if (was_pressed_right_flipper)
        {
            Flipper_Servo_Right.setPosition(1);

            if (!gamepad2.dpad_left)
            {
                bypass_right_flipper = true;
            }

            if (bypass_right_flipper)
            {
                if (gamepad2.dpad_left)
                {
                    was_pressed_right_flipper = false;
                    bypass_right_flipper = false;
                    the_stop_button_right_flipper = true;
                }
            }
        }

        if (!was_pressed_right_flipper)
        {
            Flipper_Servo_Right.setPosition(.40);
        }




        /*
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
        */


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
            Scoop_Motor.setTargetPosition(5);       //Preset for Collecting Debris, previously was 670
                                                    // (assumes scoop is ALL THE WAY TO THE FLOOR AT INIT)
            Scoop_Motor.setPower(.1);               //You set this as the max power the motor can have...
                                                    // (foresee issues depending on which side you are on)
            MoveClimberDepositor(0);
        }

        if (gamepad2.b)
        {
            Scoop_Motor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
            Scoop_Motor.setTargetPosition(-360);     //Preset for Dumping Debris into Conveyor, previously was 260
            Scoop_Motor.setPower(.1);

            MoveClimberDepositor(0);
        }

        if (gamepad2.y)
        {
            Scoop_Motor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
            Scoop_Motor.setTargetPosition(-640);     //Preset for Storage, previously was 30
            Scoop_Motor.setPower(.1);

            Climber_servo.setPosition(climber_safe_from_smashing_position);

            climber_bypass = true;
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

            MoveClimberDepositor(200);
        }
        //END Scoop Fine-Tuning


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


        //telemetry.addData("Text", "*** Robot Data***");
        telemetry.addData("Scoop Encoder value", Scoop_Motor.getCurrentPosition());
        telemetry.addData("Scoop Motor Power", Scoop_Motor.getPower());
        telemetry.addData("PullUp Tape Motor Power", PullUp_Motor_Tape.getPower());
        telemetry.addData("PullUp String Motor Power", PullUp_Motor_String.getPower());
        telemetry.addData("Climber Position", Climber_servo.getPosition());
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
        Flipper_Servo_Left.setPosition(.59);
        Flipper_Servo_Right.setPosition(.40);
        Climber_servo.setPosition(0);
        Scoop_Motor.setPower(0);
        Sweep_servo.setPosition(.5);
    }
}
