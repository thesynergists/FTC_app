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
        //ColorSensorDriving(.1, .1, true, 1);
        /*
        while (true)
        {
            Color.RGBToHSV(FloorLeftColor.red() * 8, FloorLeftColor.green() * 8, FloorLeftColor.blue() * 8, hsvValues);

            // send the info back to driver station using telemetry function.
            telemetry.addData("Clear", FloorLeftColor.alpha());
            telemetry.addData("Red  ", FloorLeftColor.red());
            telemetry.addData("Green", FloorLeftColor.green());
            telemetry.addData("Blue ", FloorLeftColor.blue());
            telemetry.addData("Hue", hsvValues[0]);

            // change the background color to match the color detected by the RGB sensor.
            // pass a reference to the hue, saturation, and value array as an argument
            // to the HSVToColor method.
            relativeLayout.post(new Runnable() {
                public void run() {
                    relativeLayout.setBackgroundColor(Color.HSVToColor(0xff, values));
                }
            });
        }
*/
        //Change 2nd i2c address to 0x70?
        // Will need to implement 'driver' for Color Sensor Driver or MRRGBExample
    }
}
