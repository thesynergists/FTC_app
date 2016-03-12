package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import static java.lang.Math.abs;

/**
 * Created by thomasmatthews on 11/6/15.
 */

public class Team7104Hardware extends OpMode
{
    DcMotor motorLeft1;
    DcMotor motorLeft2;

    DcMotor motorRight1;
    DcMotor motorRight2;

    DcMotor Scoop_Motor;


    Servo rotation_servo;          //Make ourselves da servos! :)
    Servo pitch_servo_left;
    Servo pitch_servo_right;
    Servo Bacon_servo;
    Servo Conveyor_servo;
    Servo Sweep_servo;
    Servo Climber_servo;

    Servo Flipper_Servo_Left;
    Servo Flipper_Servo_Right;

    DcMotor PullUp_Motor_Tape;
    DcMotor PullUp_Motor_String;

    ColorSensor BaconColor;
    ColorSensor FloorRightColor;
    ColorSensor FloorLeftColor;

    ElapsedTime Climber_loop = new ElapsedTime();


    //                                UNDER CONSTRUCTION!!!!!!!!!!!!!!!



    //PullUp_Motors
    void PullUp_Motors_SetPower (double Power)
    {
        //PullUp_Motor_String.setPower(-Power);       //String retracts on positive values, extends on negative values.
        PullUp_Motor_Tape.setPower(Power);          //Tape retracts on negative values, extends on positive values. Ratio is 1 to 1 now?
    }


    //Climber!!!
    void Climber_dump (boolean dump_active)
    {
        if (dump_active)
        {
            Climber_servo.setPosition(1); //Limit was .6
        }

        if (!dump_active)
        {
                Climber_servo.setPosition(0);
        }
    }


    /* Es much strangeness.
    void Climber_dump (boolean dump_active)
    {
        double set_position = 0;
        double max_position = 0;

        if (dump_active)
        {
            max_position = .6;
        }

        if (!dump_active)
        {
            max_position = 0;
        }

        while (Climber_servo.getPosition() < max_position && set_position < 0.6)
        {

            set_position = set_position + .01;
            Climber_servo.setPosition(set_position);
            telemetry.addData("Climber Position", Climber_servo.getPosition());

            Climber_loop.reset();
            while (Climber_loop.time() < .10)
            {
                telemetry.addData("Set Position", set_position);
            }
        }
    }
    */

    /*
    //Adjust IronFist elevation.

    void IronFist_change_elevation (double p_position)
    {
        //
        // Ensure the specific value is legal.
        //

        double l_position = Range.clip (p_position, 0, 1);

        //
        // Set the value.  The right servo value must be opposite of the left servo
        // value.
        //
        if (pitch_servo_left != null)
        {
            pitch_servo_left.setPosition (l_position);
        }
        if (pitch_servo_right != null)
        {
            pitch_servo_right.setPosition (1.0 - l_position);
        }
    } // IronFist_change_elevation

    //--------------------------------------------------------------------------
    //
    void IronFist_stop_elevation ()
    {
        //MAKE IT STOP!!!
        pitch_servo_right.setPosition(.5);
        pitch_servo_left.setPosition(.5);
    }

    //Control Iron Fist rotation.

    void Iron_Fist_rotate (double left_double, double right_double)
    {

        double left_double_rotate = Range.clip (left_double, 0, 1);
        double right_double_rotate = Range.clip (right_double, 0, 1);

        if (rotation_servo != null)
        {
            rotation_servo.setPosition(left_double_rotate);
        }
        if (rotation_servo != null)
        {
            rotation_servo.setPosition(right_double_rotate);
        }

        //Make servo become motionless if receiving conflicting control commands.
        // This last part may require some testing and adjustment in case of slight sensor error...
        if (rotation_servo != null)
        {
            if (left_double_rotate != .5 && right_double_rotate != .5)
            {
                rotation_servo.setPosition(.5);
            }
        }
    }
    */

    //Control the Conveyor Belt.
    void Conveyor_Belt_Control (boolean left_bool, boolean right_bool)
    {
        if (Conveyor_servo == null)
        {
            return;
        }
        if (left_bool)
        {
            Conveyor_servo.setPosition(1);
        }

        if (right_bool)
        {
            Conveyor_servo.setPosition(0);
        }

        if (left_bool != true && right_bool != true)
        {
            Conveyor_servo.setPosition(.5);
        }

        //Set the conveyor_servo to stop if receiving conflicting inputs.
        if (left_bool && right_bool)
        {
            Conveyor_servo.setPosition(.5);
        }
    }


    //                       END OF CONSTRUCTION!!!!!!!!!!!





    public Team7104Hardware ()
    {

    }

    /*
     * Code to run when the op mode is first enabled goes here
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#start()
     */
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

        motorLeft1 = hardwareMap.dcMotor.get("motorLeft1");
        motorLeft2 = hardwareMap.dcMotor.get("motorLeft2");

        motorRight1 = hardwareMap.dcMotor.get("motorRight1");
        motorRight2 = hardwareMap.dcMotor.get("motorRight2");

        Scoop_Motor = hardwareMap.dcMotor.get("Scoop_Motor");
        Scoop_Motor.setPower(0);

        Bacon_servo = hardwareMap.servo.get("Bacon_servo");
        Bacon_servo.setPosition(.5);

        //pitch_servo_left = hardwareMap.servo.get("pitch_servo_left");
        //pitch_servo_right = hardwareMap.servo.get("pitch_servo_right");

        Sweep_servo = hardwareMap.servo.get("Sweep_servo");
        Sweep_servo.setPosition(.5);

        Conveyor_servo = hardwareMap.servo.get("Conveyor_servo");
        Conveyor_servo.setPosition(.5);

        Climber_servo = hardwareMap.servo.get("Climber_servo");
        Climber_servo.setPosition(0);                               //Hopefully the neutral position...

        Flipper_Servo_Left = hardwareMap.servo.get("Flipper_Servo_Left");
        Flipper_Servo_Left.setPosition(.59);    //Previous default was .40
        Flipper_Servo_Right = hardwareMap.servo.get("Flipper_Servo_Right");
        Flipper_Servo_Right.setPosition(.40);   //Previous default was .57

        BaconColor = hardwareMap.colorSensor.get("Bacon_Color");
        FloorLeftColor = hardwareMap.colorSensor.get("Floor_Left_Color");
        FloorRightColor = hardwareMap.colorSensor.get("Floor_Right_Color");



        PullUp_Motor_Tape = hardwareMap.dcMotor.get("PullUp_Motor_Tape");
        PullUp_Motor_Tape.setPower(0);
        PullUp_Motor_String = hardwareMap.dcMotor.get("PullUp_Motor_String");
        PullUp_Motor_String.setPower(0);

        motorRight1.setDirection(DcMotor.Direction.REVERSE);
        motorRight2.setDirection(DcMotor.Direction.REVERSE);

        telemetry.addData("Initialization Complete, Ready for Program", 5);
        telemetry.addData("Go!", 6);
}

    @Override public void loop ()
    {

    }
    //
    // start
    //
    /**
     * Perform any actions that are necessary when the OpMode is enabled.
     *
     * The system calls this member once when the OpMode is enabled.
     */


    @Override public void start ()
    {
        //
        // Only actions that are common to all Op-Modes (i.e. both automatic and
        // manual) should be implemented here.
        //
        // This method is designed to be overridden.
        //

    } // start


    //
    // stop
    //
    /**
     * Perform any actions that are necessary when the OpMode is disabled.
     *
     * The system calls this member once when the OpMode is disabled.
     */
    @Override public void stop ()
    {
        //
        // Nothing needs to be done for this method.
        //

    } // stop
    /*
         * This method scales the joystick input so for low joystick values, the
         * scaled value is less than linear.  This is to make it easier to drive
         * the robot more precisely at slower speeds.
         */
    double scaleInput(double dVal)
    {
        double[] scaleArray = { 0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };

        // get the corresponding index for the scaleInput array.
        int index = (int) (dVal * 16.0);

        // index should be positive.
        if (index < 0) {
            index = -index;
        }

        // index cannot exceed size of array minus 1.
        if (index > 16) {
            index = 16;
        }

        // get value from the array.
        double dScale = 0.0;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        // return scaled value.
        return dScale;
    }


    public void setPowerRightMotor(double level)
    {
        motorLeft1.setPower(-level);
        motorLeft2.setPower(-level);
    }
    public void setPowerLeftMotor(double level)
    {
        motorRight1.setPower(-level);
        motorRight2.setPower(-level);
    }
    public void setPowerBothMotor(double level)
    {
        setPowerRightMotor(level);
        setPowerLeftMotor(level);
    }

    private boolean v_warning_generated = false;

    //--------------------------------------------------------------------------
    //
    // v_warning_message
    //
    /**
     * Store a message to the user if one has been generated.
     */
    private String v_warning_message = "Can't map;";


    //--------------------------------------------------------------------------
    //
    // a_warning_generated
    //
    /**
     * Access whether a warning has been generated.
     */
    boolean a_warning_generated ()
    {
        return v_warning_generated;

    } // a_warning_generated

    //--------------------------------------------------------------------------
    //
    // a_warning_message
    //
    /**
     * Access the warning message.
     */
    String a_warning_message ()
    {
        return v_warning_message;

    } // a_warning_message


    void m_warning_message (String p_exception_message)
    {
        if (v_warning_generated)
        {
            v_warning_message += ", ";
        }
        v_warning_generated = true;
        v_warning_message += p_exception_message;

    } // m_warning_message

    double a_left_drive_power ()
    {
        double l_return = 0.0;

        if (motorLeft1 != null)
        {
            l_return = motorLeft1.getPower ();
        }

        return l_return;

    } // a_left_drive_power

    //--------------------------------------------------------------------------
    //
    // a_right_drive_power
    //
    /**
     * Access the right drive motor's power level.
     */
    double a_right_drive_power ()
    {
        double l_return = 0.0;

        if (motorRight1 != null)
        {
            l_return = motorRight1.getPower ();
        }

        return l_return;

    } // a_right_drive_power

    int a_left_encoder_count ()
    {
        int l_return = 0;

        if (motorLeft1 != null)
        {
            l_return = motorLeft1.getCurrentPosition();
        }

        return l_return;

    } // a_left_encoder_count

    //--------------------------------------------------------------------------
    //
    // a_right_encoder_count
    //
    /**
     * Access the right encoder's count.
     */
    int a_right_encoder_count ()
    {
        int l_return = 0;

        if (motorRight1 != null)
        {
            l_return = motorRight1.getCurrentPosition();
        }

        return l_return;

    } // a_right_encoder_count
}
