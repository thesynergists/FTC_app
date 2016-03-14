package com.qualcomm.ftcrobotcontroller.opmodes;
import android.graphics.Color;

import static java.lang.Math.*;

/**
 * Created by Daniel on 3/9/2016.
 */
public class Team7104ColorSensorTesting extends Team7104AutoHardware{

    @Override
    public void runOpMode() throws InterruptedException{
        super.runOpMode();

        //FloorLeftColor.enableLed(true);
        while(true)
        {
            ColorSensorDriving(.1, .1, true, 1);
        }
        //Change 2nd i2c address to 0x70?
        // Will need to implement 'driver' for Color Sensor Driver or MRRGBExample
    }
}
