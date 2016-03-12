package com.qualcomm.ftcrobotcontroller.opmodes;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DcMotor;
import static java.lang.Math.*;

/**
 * Created by Daniel on 2/13/2016.
 */
public class Team7104GyroTest extends Team7104AutoHardware
{
    @Override
    public void runOpMode() throws InterruptedException
    {
        super.runOpMode();
        Turn_degrees(.8, 20, 1);
    }
}