package com.qualcomm.ftcrobotcontroller.opmodes;
import static java.lang.Math.*;

/**
 * Created by Daniel on 2/12/2016.
 */

/*
    Encoder Counts/Revolution
    NeveRest 20: 560
    NeveRest 40: 1120
    NeveRest 60: 1680
*/

public class Team7104EncoderTest extends Team7104AutoHardware{

    @Override
    public void runOpMode() throws InterruptedException {
        super.runOpMode();
        //RunWithEncoders(-.5, .5, 30, 1);
        //sleep(2000);
        //RunWithEncoders(.5, .5, 5, 2);
        //sleep(2000);
        //RunWithEncoders(-.5,-.5, 35, 3);
        //sleep(2000);
        RunWithEncoders(.8,.8, 40, 1);
        sleep(5000);
        RunWithEncoders(.5,-.5, 8, 2);
        sleep(5000);
        RunWithEncoders(.5,.5, 10, 3);
        sleep(5000);
    }
}
//  eg: if (Math.abs(target-position) < 100) stop motor.
   //     Then exit when all motors are off.