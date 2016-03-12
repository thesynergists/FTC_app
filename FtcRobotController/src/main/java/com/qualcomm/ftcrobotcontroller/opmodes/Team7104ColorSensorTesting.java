package com.qualcomm.ftcrobotcontroller.opmodes;
import static java.lang.Math.*;

/**
 * Created by Daniel on 3/9/2016.
 */
public class Team7104ColorSensorTesting extends Team7104AutoHardware{

    @Override
    public void runOpMode() throws InterruptedException{
        super.runOpMode();

        FloorLeftColor.enableLed(true);
        ColorSensorDriving(.1, .1, true, 1);

        //Change 2nd i2c address to 0x70?
    }
}
