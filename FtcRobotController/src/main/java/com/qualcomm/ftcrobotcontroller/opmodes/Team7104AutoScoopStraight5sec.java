package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by Daniel on 3/26/2016.
 */
public class Team7104AutoScoopStraight5sec extends Team7104AutoHardware
{
    @Override public void runOpMode() throws InterruptedException
    {
        super.runOpMode();
        sleep(MatchWaitTimeBEFOREScoop);
        ScoopPrep();
        sleep(MatchWaitTimeAFTERScoop);

        RunForTime(.5, .5, 5, 1);
        sleep(500);
        Scoop_Motor.setPower(0);
        SetLeftMotors(0);
        SetRightMotors(0);
    }
}
