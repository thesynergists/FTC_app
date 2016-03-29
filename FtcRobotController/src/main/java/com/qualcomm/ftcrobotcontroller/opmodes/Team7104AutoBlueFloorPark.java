package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by thomasmatthews on 2/27/16.
 */
public class Team7104AutoBlueFloorPark extends Team7104AutoHardware
{
    @Override public void runOpMode() throws InterruptedException
    {
        super.runOpMode();

        sleep(MatchWaitTimeBEFOREScoop);
        ScoopPrep();
        sleep(MatchWaitTimeAFTERScoop);

        Sweep_servo.setPosition(Sweeper_Reverse);

        RunWithEncoders(FP_SD_MPLeft, FP_SD_MPRight, FPSmallDrive, 1);      //Small drive forward
        Turn_degrees(FP_FT_MP, FPFirstTurn, 2);            //45 degree turn right using gyro
        RunWithEncoders(FP_LD_MPLeft, FP_LD_MPRight, FPLongDrive, 3);     //Long drive forward
        Scoop_Motor.setPower(0);
        Sweep_servo.setPosition(Sweeper_Forward);
        RunForTime(FP_IntoWall_MPLeft, FP_IntoWall_MPRight, FP_IntoWall_Time, 1); //Forward 'into wall' to get closer to wall
        RunWithEncoders(FP_BackupWall_MPLeft, FP_BackupWall_MPRight, FP_BackupWall, 3);     //Backup from wall a little
        Turn_degrees(-FP_BeaconTurn_MP, FPSecondTurnNearBeacon, 4);           //45 degree turn right using gyro

        //Collect Debris
        Scoop_Motor.setTargetPosition(Scoop_Floor); //To ensure the scoop is low enough to collect debris
        Scoop_Motor.setPower(.1);
        RunWithEncoders(FP_Debris_MPLeft, FP_Debris_MPRight, FPForwardtoCollectSomeDebris, 5);      //Forward to Collect Some Debris in front of Beacon
        Scoop_Motor.setTargetPosition(Scoop_Deposit);
        Scoop_Motor.setPower(.1);
        sleep(750);

        //Move Conveyor
        Conveyor_time.reset();
        Conveyor_servo.setPosition(.6);
        while (Conveyor_time.time() < 2) {} //Do nothing
        Conveyor_servo.setPosition(.5);
        Sweep_servo.setPosition(.5);        //Stop Sweeper

        Scoop_Motor.setTargetPosition(Scoop_Floor);
        Scoop_Motor.setPower(.1);
        Turn_degrees(-FP90Turn_MP, FP90Turn, 6);
        Scoop_Motor.setPower(0);
        RunForTime(-FPForTime_MPLeft, -FPForTime_MPRight, FPForTime_Time, 1);
        SetLeftMotors(0);
        SetRightMotors(0);
    }
}