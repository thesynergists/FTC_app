package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
/**
 * Created by thomasmatthews on 11/13/15.
 */
public class Team7104AutoEncoder extends Team7104Telemetry
{


/*





    TENTATIVELY, IT APPEARS THAT ENCODER VALUE OF 1120 IS EQUIVALENT TO ONE ROTATION!






 */
    /**
     * Construct the class.
     *
     * The system calls this member when the class is instantiated.
     */
    public Team7104AutoEncoder ()
    {
        //
        // Initialize base classes.
        //
        // All via self-construction.

        //
        // Initialize class members.
        //
        // All via self-construction.

    } // Team7104Auto

    //--------------------------------------------------------------------------
    //
    // start
    //
    /**
     * Perform any actions that are necessary when the OpMode is enabled.
     *
     * The system calls this member once when the OpMode is enabled.
     */

    @Override public void init()
    {
        super.init();
    }

    @Override public void start ()
    {
        //
        // Call the Team7104Hardware (super/base class) start method.
        //
        super.start ();

        //
        // Reset the motor encoders on the drive wheels.
        //
        reset_drive_encoders ();

    } // start

    //--------------------------------------------------------------------------
    //
    // loop
    //
    /**
     * Implement a state machine that controls the robot during auto-operation.
     * The state machine uses a class member and encoder input to transition
     * between states.
     *
     * The system calls this member repeatedly while the OpMode is running.
     */

    @Override public void loop ()
    {
        switch (drive_state)
        {
            //
            // Synchronize the state machine and hardware.
            //
            case 0:
                //
                // Reset the encoders to ensure they are at a known good value.
                //
                reset_drive_encoders();

                //
                // Transition to the next state when this method is called again.
                //
                drive_state++;

                break;
            //
            // Drive forward until the encoders exceed the specified values.
            //
            case 1:
                //
                // Tell the system that motor encoders will be used.  This call MUST
                // be in this state and NOT the previous or the encoders will not
                // work.  It doesn't need to be in subsequent states.
                //
                run_using_encoders();

                //
                // Start the drive wheel motors at full power.
                //
                setPowerLeftMotor(.8);
                setPowerRightMotor(.8);


                //
                // Have the motor shafts turned the required amount?
                //
                // If they haven't, then the op-mode remains in this state (i.e this
                // block will be executed the next time this method is called).
                //
                if (have_drive_encoders_reached(-1000, -1000)) //these parameters will affect motor value before change
                {
                    //
                    // Reset the encoders to ensure they are at a known good value.
                    //
                    reset_drive_encoders();


                    run_using_encoders();
                    //
                    // Stop the motors.
                    //
                    setPowerLeftMotor(0);
                    setPowerRightMotor(0);
                    //
                    // Transition to the next state when this method is called
                    // again.
                    //
                    drive_state++;
                }
                break;
            //
            // Wait...
            //
            case 2:

                if (have_drive_encoders_reset())
                {
                    drive_state++;
                }
                break;
            //
            // Turn left until the encoders exceed the specified values.
            //
            case 3:

                run_using_encoders();
                setPowerLeftMotor(-.7);
                setPowerRightMotor(.7);

                if (have_drive_encoders_reached(200, -200))
                {
                    reset_drive_encoders();
                    setPowerLeftMotor(0);
                    setPowerRightMotor(0);
                    drive_state++;
                }
                break;
            //
            // Wait...
            //
            case 4:
                if (have_drive_encoders_reset())
                {
                    drive_state++;
                }
                break;
            //
            // Move backwards until the encoders exceed the specified values.
            //
            case 5:
                run_using_encoders();
                setPowerLeftMotor(-.8);
                setPowerRightMotor(-.8);

                if (have_drive_encoders_reached(700, 700))
                {
                    reset_drive_encoders();
                    setPowerLeftMotor(0);
                    setPowerRightMotor(0);
                    drive_state++;
                }
                break;
        }


        update_telemetry(); // Update common telemetry
        telemetry.addData("18", "State: " + drive_state);
        telemetry.addData("Current encoder counts:" + a_left_encoder_count(), a_right_encoder_count());
    } // loop

    //--------------------------------------------------------------------------
    //
    // drive_state
    //variable responsible for running previous switch statement
    private int drive_state = 0;








    //update_telemetry method to be used by the auto
    public void update_telemetry()
    {
        int something = 0;
        //FILL THIS IN LATER!!! (WITH WHATEVER TELEMETRY IS...)
    }













//--------------------------------------------------------------------------------------------

    
    //moved methods from PushBotHardware manually into here as a temporary workaround:







    //encoder reset methods:





    //
    // reset_left_drive_encoder
    //
    /**
     * Reset the left drive wheel encoder.
     */
    public void reset_left_drive_encoder ()
    {
        if (motorLeft1 != null)
        {
            motorLeft1.setMode (DcMotorController.RunMode.RESET_ENCODERS);
        }
    } // reset_left_drive_encoder

    //--------------------------------------------------------------------------
    //
    // reset_right_drive_encoder
    //
    /**
     * Reset the right drive wheel encoder.
     */
    public void reset_right_drive_encoder ()
    {
        if (motorRight1 != null)
        {
            motorRight1.setMode (DcMotorController.RunMode.RESET_ENCODERS);
        }
    } // reset_right_drive_encoder

    //--------------------------------------------------------------------------
    //
    // reset_drive_encoders
    //
    /**
     * Reset both drive wheel encoders.
     */
    public void reset_drive_encoders ()
    {
        //
        // Reset the motor encoders on the drive wheels.
        //
        reset_left_drive_encoder ();
        reset_right_drive_encoder ();
    } // reset_drive_encoders








    //encoder run methods:





    //-------------------------------------------------------------------
    //
    // run_using_left_drive_encoder
    //
    /**
     * Set the left drive wheel encoder to run, if the mode is appropriate.
     */
    public void run_using_left_drive_encoder ()
    {
        if (motorLeft1 != null)
        {
            motorLeft1.setMode (DcMotorController.RunMode.RUN_USING_ENCODERS);
        }
    } // run_using_left_drive_encoder

    //--------------------------------------------------------------------------
    //
    // run_using_right_drive_encoder
    //
    /**
     * Set the right drive wheel encoder to run, if the mode is appropriate.
     */
    public void run_using_right_drive_encoder ()
    {
        if (motorRight1 != null)
        {
            motorRight1.setMode (DcMotorController.RunMode.RUN_USING_ENCODERS);
        }
    } // run_using_right_drive_encoder

    //--------------------------------------------------------------------------
    //
    // run_using_encoders
    //
    /**
     * Set both drive wheel encoders to run, if the mode is appropriate.
     */
    public void run_using_encoders ()
    {
        //
        // Call other members to perform the action on both motors.
        //
        run_using_left_drive_encoder ();
        run_using_right_drive_encoder ();
    } // run_using_encoders
    //-------------------------------------------------------------



    //have_encoder_reached methods:







    //------------------------------------------------------------
    boolean has_left_drive_encoder_reached (double p_count)
    {
        //
        // Assume failure.
        //
        boolean l_return = false;

        if (motorLeft1 != null)
        {
            //
            // Has the encoder reached the specified values?
            //
            // TODO Implement stall code using these variables.
            //
            if (Math.abs (motorLeft1.getCurrentPosition ()) > p_count)
            {
                //
                // Set the status to a positive indication.
                //
                l_return = true;
            }
        }
        //
        // Return the status.
        //
        return l_return;
    } // has_left_drive_encoder_reached

    //--------------------------------------------------------------------------
    //
    // has_right_drive_encoder_reached
    //
    /**
     * Indicate whether the right drive motor's encoder has reached a value.
     */
    boolean has_right_drive_encoder_reached (double p_count)
    {
        //
        // Assume failure.
        //
        boolean l_return = false;

        if (motorRight1 != null)
        {
            //
            // Have the encoders reached the specified values?
            //
            // TODO Implement stall code using these variables.
            //
            if (Math.abs (motorRight1.getCurrentPosition ()) > p_count)
            {
                //
                // Set the status to a positive indication.
                //
                l_return = true;
            }
        }
        //
        // Return the status.
        //
        return l_return;
    } // has_right_drive_encoder_reached

    //--------------------------------------------------------------------------
    //
    // have_drive_encoders_reached
    //
    /**
     * Indicate whether the drive motors' encoders have reached a value.
     */
    boolean have_drive_encoders_reached (double p_left_count, double p_right_count)
    {
        //
        // Assume failure.
        //
        boolean l_return = false;

        //
        // Have the encoders reached the specified values?
        //
        if (has_left_drive_encoder_reached (p_left_count) && has_right_drive_encoder_reached (p_right_count))
        {
            //
            // Set the status to a positive indication.
            //
            l_return = true;
        }
        //
        // Return the status.
        //
        return l_return;
    } // have_encoders_reached
    //-------------------------------------------------




    //have_drive_encoders reset methods:




    //--------------------------------------------------------------------------
    //
    // has_left_drive_encoder_reset
    //
    /**
     * Indicate whether the left drive encoder has been completely reset.
     */
    boolean has_left_drive_encoder_reset ()
    {
        //
        // Assume failure.
        //
        boolean l_return = false;

        //
        // Has the left encoder reached zero?
        //
        if (a_left_encoder_count () == 0)
        {
            //
            // Set the status to a positive indication.
            //
            l_return = true;
        }
        //
        // Return the status.
        //
        return l_return;
    } // has_left_drive_encoder_reset

    //--------------------------------------------------------------------------
    //
    // has_right_drive_encoder_reset
    //
    /**
     * Indicate whether the left drive encoder has been completely reset.
     */
    boolean has_right_drive_encoder_reset ()
    {
        //
        // Assume failure.
        //
        boolean l_return = false;
        //
        // Has the right encoder reached zero?
        //
        if (a_right_encoder_count () == 0)
        {
            //
            // Set the status to a positive indication.
            //
            l_return = true;
        }
        //
        // Return the status.
        //
        return l_return;
    } // has_right_drive_encoder_reset

    //--------------------------------------------------------------------------
    //
    // have_drive_encoders_reset
    //
    /**
     * Indicate whether the encoders have been completely reset.
     */
    boolean have_drive_encoders_reset ()
    {
        //
        // Assume failure.
        //
        boolean l_return = false;
        //
        // Have the encoders reached zero?
        //
        if (has_left_drive_encoder_reset () && has_right_drive_encoder_reset ())
        {
            //
            // Set the status to a positive indication.
            //
            l_return = true;
        }
        //
        // Return the status.
        //
        return l_return;
    } // have_drive_encoders_reset

    //--------------------------------------------------------------------------






    //a_encoder_count methods:




    //--------------------------------------------------------------------------
    //
    // a_left_encoder_count
    //
    /**
     * Access the left encoder's count.
     */
    int a_left_encoder_count ()
    {
        int l_return = 0;

        if (motorLeft1 != null)
        {
            l_return = motorLeft1.getCurrentPosition ();
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
            l_return = motorRight1.getCurrentPosition ();
        }
        return l_return;
    } // a_right_encoder_count

    //--------------------------------------------------------------------------

}
