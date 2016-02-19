package com.qualcomm.ftcrobotcontroller.opmodes;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DcMotor;
import static java.lang.Math.*;

/**
 * Created by Daniel on 2/13/2016.
 */
public class Team7104GyroTest extends LinearOpMode{
    DcMotor motorLeft1;
    DcMotor motorLeft2;

    DcMotor motorRight1;
    DcMotor motorRight2;
    GyroSensor sensorGyro;

    int headingTarget;
    int headingCurrent;
    int headingPrevious;
    final int SLEEP = 250;
    double headingDifference;
    double SetPower;
    double driveSteering;
    double driveGain = 0.7;

    //int CurrentPositionatEndOfEncoderRun;

    @Override
    public void runOpMode() throws InterruptedException {

        motorLeft1 = hardwareMap.dcMotor.get("motorLeft1");
        motorLeft2 = hardwareMap.dcMotor.get("motorLeft2");

        motorRight1 = hardwareMap.dcMotor.get("motorRight1");
        motorRight2 = hardwareMap.dcMotor.get("motorRight2");

        sensorGyro = hardwareMap.gyroSensor.get("gyro");

        motorRight1.setDirection(DcMotor.Direction.REVERSE);
        motorRight2.setDirection(DcMotor.Direction.REVERSE);

        waitOneFullHardwareCycle();
        //CurrentPositionatEndOfEncoderRun = motorLeft1.getCurrentPosition();

        //Scoop_Motor = hardwareMap.dcMotor.get("Scoop_Motor");
        motorLeft1.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        motorLeft2.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        motorRight1.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        motorRight2.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        waitOneFullHardwareCycle();
        sensorGyro.calibrate();
        telemetry.clearData();
        while (sensorGyro.isCalibrating())  {
            telemetry.addData("Gyro Sensor Calibrating.......", 1);
        }
        telemetry.addData("Initialization Complete", 2);

        waitForStart();
        motorLeft1.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        motorLeft2.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        motorRight1.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        motorRight2.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);

        headingTarget = 90; //Code Turn Limits from -180 < Theta < +180....HW Limits likely -170 to 170
        SetPower = .5;
        headingPrevious = sensorGyro.getHeading();
        waitOneFullHardwareCycle();
        headingCurrent = sensorGyro.getHeading();
        waitOneFullHardwareCycle();

        GyroHeadingDifference();

        telemetry.addData("Heading Previous", headingPrevious);
        telemetry.addData("Heading Current", headingCurrent);
        telemetry.addData("Heading Difference", headingDifference);
        telemetry.addData("Heading Target", headingTarget);
        sleep(5000);

        //motorLeft1.setPower(-SetPower);
       // motorLeft2.setPower(-SetPower);
       // motorRight1.setPower(SetPower);
       // motorRight2.setPower(SetPower);
        waitOneFullHardwareCycle();

        while(abs(headingDifference)<abs(headingTarget))
        {
            headingCurrent = sensorGyro.getHeading();

            GyroHeadingDifference();

            telemetry.addData("Previous:", String.valueOf(headingPrevious));
            telemetry.addData("Current:", String.valueOf(headingCurrent));
            telemetry.addData("Target:", String.valueOf(headingTarget));
            telemetry.addData("Difference:", String.valueOf(headingDifference));
        }

        motorLeft1.setPower(0);
        motorLeft2.setPower(0);
        motorRight1.setPower(0);
        motorRight2.setPower(0);

        sleep(SLEEP);
        headingPrevious = motorLeft1.getCurrentPosition();
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