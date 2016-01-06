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
