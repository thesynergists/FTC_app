package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by thomasmatthews on 2/27/16.
 */
public class Team7104AutoBlueState extends Team7104AutoHardware
{

    private ElapsedTime Scoop_time = new ElapsedTime();
    @Override public void runOpMode() throws InterruptedException
    {
        //BaconColor.enableLed(false);
        //FloorRightColor.enableLed(false);
        //FloorLeftColor.enableLed(false);
        //Servos to initial position
        super.runOpMode();

        Scoop_Motor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        Scoop_Motor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        Scoop_time.reset();
        Scoop_Motor.setTargetPosition(500);     //Should be 660!!! Temporarily adjusted because PullUp is in way.
        Scoop_Motor.setPower(.1);

        while (Scoop_time.time() < 2)
        {
            SetLeftMotors(0);
            SetRightMotors(0);
        }

        //11.5 encoders are about 90 degree turn.
        RunWithEncoders(.6, .6, 6, 1);      //Small drive forward
        sleep(500);

        Turn_degrees(.5, 45, 2);    //45 degree turn right using gyro
        sleep(500);

        RunWithEncoders(.6, .6, 40, 3);     //Long drive forward
        sleep(500);

        Turn_degrees(.5, 45, 4);     //45 degree turn right using gyro
        sleep(500);

        RunWithEncoders(.5, .5, 5, 5);      //Push to wall.
        sleep(500);

        Scoop_Motor.setTargetPosition(30);
        Scoop_Motor.setPower(.1);

        Scoop_time.reset();
        while (Scoop_time.time() < 3)
        {
        }
        Scoop_Motor.setPower(0);
    }
}
