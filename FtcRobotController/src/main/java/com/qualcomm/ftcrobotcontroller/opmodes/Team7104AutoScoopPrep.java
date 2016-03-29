package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by Daniel on 3/25/2016.
 */
public class Team7104AutoScoopPrep extends Team7104AutoHardware
{
    @Override public void runOpMode() throws InterruptedException
    {
        super.runOpMode();
        sleep(MatchWaitTimeBEFOREScoop);
        ScoopPrep();
        sleep(500);
        Scoop_Motor.setPower(0);
    }
}
