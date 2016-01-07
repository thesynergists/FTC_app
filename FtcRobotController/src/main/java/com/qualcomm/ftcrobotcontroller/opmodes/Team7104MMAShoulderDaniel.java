package com.qualcomm.ftcrobotcontroller.opmodes;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.Hardware;

/**
 * Created by Daniel on 12/18/2015.
 */
public class Team7104MMAShoulderDaniel extends Team7104Hardware

{
    public Team7104MMAShoulderDaniel()
    {

    }
    @Override
    public void init(){
    }
    //New Function Checking Remaining Distance from current encoder locating to location goal
    //If the abs(current encoder value-encoder goal value)>200, then 100 power
    //If the abs(current encoder value-encoder goal value)<200, then [abs(current encoder value-encoder goal value) * .4] power

    //New Function or if statement for normal joystick (y) power mode
    //if joystick > 0.01, then set power to (gamepad * 100)
    //Make a bigger slow zone for jittery fingers?
    @Override
    public void loop(){
        //if joystick 2, right joystick
        //Stop Motor
        if(gamepad2.right_stick_y < 0.01 && gamepad2.right_stick_y > -0.01)
        {
            MMAShouldermotorLeft.setPower(0);
            MMAShouldermotorRight.setPower(0);
        }
        if(gamepad2.right_stick_y > 0.01)
        {
            MMAShouldermotorLeft.setPower(15);
        }
        if(gamepad2.right_stick_y < -0.01)
        {
            MMAShouldermotorLeft.setPower(-15);
        }
    }
@Override
public void stop(){}
}
