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
    public void runOpMode() throws InterruptedException {
        headingTarget = 90; //Code Turn Limits from -180 < Theta < +180....HW Limits likely -170 to 170
        SetPower = .5;

        super.runOpMode();

        telemetry.addData("Heading Previous", headingPrevious);
        telemetry.addData("Heading Current", headingCurrent);
        telemetry.addData("Heading Difference", headingDifference);
        telemetry.addData("Heading Target", headingTarget);
        sleep(500);

        /*motorLeft1.setPower(-SetPower);
        waitOneFullHardwareCycle();
        motorLeft2.setPower(-SetPower);
        waitOneFullHardwareCycle();
        motorRight1.setPower(SetPower);
        waitOneFullHardwareCycle();
        motorRight2.setPower(SetPower);
        waitOneFullHardwareCycle();*/
        //Switch Left Direction from - to +
        //Right 2? Is negative now?

        //motorLeft1.setPower(0);
        //waitOneFullHardwareCycle();

        //motorLeft2.setPower(0);
        //waitOneFullHardwareCycle();

        //motorRight1.setPower(.5);
        //waitOneFullHardwareCycle();

        //motorRight2.setPower(.5);
        //waitOneFullHardwareCycle();
        SetLeftMotors(0);
        SetRightMotors(0.5);

        while(abs(headingDifference)<abs(headingTarget))
        {
            headingCurrent = sensorGyro.getHeading();

            //motorLeft1.setPower(SetPower);
            //waitOneFullHardwareCycle();
            //motorLeft2.setPower(SetPower);
            //waitOneFullHardwareCycle();
            //motorRight1.setPower(-SetPower);
            //waitOneFullHardwareCycle();
            //motorRight2.setPower(-SetPower);
            //waitOneFullHardwareCycle();

            GyroHeadingDifference();

            //telemetry.addData("Previous:", String.valueOf(headingPrevious));
            //telemetry.addData("Current:", String.valueOf(headingCurrent));
            //telemetry.addData("Target:", String.valueOf(headingTarget));
            //telemetry.addData("Difference:", String.valueOf(headingDifference));
            //telemetry.addData("Motor Power Left:" + motorLeft1.getPower(), motorLeft2.getPower());
            //telemetry.addData("Motor Power Right:" + motorRight1.getPower(), motorRight2.getPower());
        }

        motorLeft1.setPower(0);
        motorLeft2.setPower(0);
        motorRight1.setPower(0);
        motorRight2.setPower(0);

        sleep(SLEEP);
        headingPrevious = motorRight1.getCurrentPosition();
        telemetry.addData("Previous Heading:", String.valueOf(headingPrevious));
        waitOneFullHardwareCycle();
        sleep(SLEEP);
    }
    public void GyroHeadingDifference()
    {
        if(abs(headingPrevious-headingCurrent) > headingTarget) //If the difference is greater than the target
        {
            headingDifference = abs(abs(headingPrevious-headingCurrent)-360); //Then the difference can be corrected by -360
        }
        else
        {
            headingDifference = abs(headingPrevious-headingCurrent); //Otherwise, difference is in a good zone
        }
        if(abs(headingTarget) > 180)
        {
            telemetry.addData("|Target Heading| > 180* !!!!!!!!!", 1); //If Target is over limit....then CAUTION!!!
        }
    }
}