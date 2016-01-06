package com.qualcomm.ftcrobotcontroller.opmodes;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.Hardware;

/**
 * TeleOp Mode
 * <p>
 * Enables control of the robot via the gamepad
 */
public class Team7104BaconTeleOp extends Team7104Hardware

{

    public Team7104BaconTeleOp()
    {


    }
    @Override
    public void init () {

         Bacon_servo.setPosition(0);
    }
    @Override
    public void loop(){
        if (gamepad1.right_bumper){


        }
        if (gamepad1.left_bumper){


        }
    }
    @Override
    public void stop(){

    }
}