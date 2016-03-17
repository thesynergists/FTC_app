package com.qualcomm.ftcrobotcontroller.opmodes;
import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.ftcrobotcontroller.R;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
//import com.qualcomm.robotcore.ftcrobotcontroller.ColorSensorDriver.java;

import static java.lang.Math.*;

/**
 * Created by Daniel on 2/19/2016.
 */

/*
    Encoder Counts/Revolution
    NeveRest 20: 560
    NeveRest 40: 1120
    NeveRest 60: 1680

    Turning With Encoders...Approximately:
      90*~11.5 inches
*/

public class Team7104AutoHardware extends LinearOpMode {

    DcMotor motorLeft1;
    DcMotor motorLeft2;

    DcMotor motorRight1;
    DcMotor motorRight2;

    DcMotor Scoop_Motor;


    Servo Bacon_servo;
    Servo Conveyor_servo;
    Servo Sweep_servo;
    Servo Climber_servo;

    Servo Flipper_Servo_Left;
    Servo Flipper_Servo_Right;

    DcMotor PullUp_Motor_Tape;
    DcMotor PullUp_Motor_String;

    ColorSensor BaconColor;
    ColorSensor FloorRightColor;
    ColorSensor FloorLeftColor;
    GyroSensor sensorGyro;

    private ElapsedTime mStateTime = new ElapsedTime();
    private ElapsedTime TotalTime = new ElapsedTime();

    int CurrentPositionatEndOfEncoderRun;
    final int SLEEP = 250;
    double headingTarget;
    int headingCurrent;
    int headingPrevious;
    double headingDifference;
    double SetPower;
    //double driveSteering;
    //double driveGain = 0.7;

    //GLOBAL VARIABLES
    double Climber_Saftey_Position = .33;
    double Climber_Default_Position = .06;
    int Scoop_Floor = 610;
    int Scoop_Deposit = 250;
    int Scoop_Storage = 25;
    int Sweeper_Forward = 0;
    double Sweeper_Reverse = .75;
    int SLEEP_After_Movement = 1000;

    int MatchWaitTime = 0;


    public ElapsedTime Scoop_time = new ElapsedTime();
    public ElapsedTime Conveyor_time = new ElapsedTime();
    @Override
    public void runOpMode() throws InterruptedException {
        //hardwareMap.logDevices();
        motorLeft1 = hardwareMap.dcMotor.get("motorLeft1");
        motorLeft2 = hardwareMap.dcMotor.get("motorLeft2");

        motorRight1 = hardwareMap.dcMotor.get("motorRight1");
        motorRight2 = hardwareMap.dcMotor.get("motorRight2");

        Scoop_Motor = hardwareMap.dcMotor.get("Scoop_Motor");

        Bacon_servo = hardwareMap.servo.get("Bacon_servo");
        Bacon_servo.setPosition(.5);

        Sweep_servo = hardwareMap.servo.get("Sweep_servo");
        Sweep_servo.setPosition(.5);

        Conveyor_servo = hardwareMap.servo.get("Conveyor_servo");
        Conveyor_servo.setPosition(.5);

        Climber_servo = hardwareMap.servo.get("Climber_servo");
        Climber_servo.setPosition(Climber_Default_Position);                               //Hopefully the neutral position...

        Flipper_Servo_Left = hardwareMap.servo.get("Flipper_Servo_Left");
        Flipper_Servo_Left.setPosition(.59);
        Flipper_Servo_Right = hardwareMap.servo.get("Flipper_Servo_Right");
        Flipper_Servo_Right.setPosition(.40);

        BaconColor = hardwareMap.colorSensor.get("Bacon_Color");
        FloorLeftColor = hardwareMap.colorSensor.get("Floor_Left_Color");
        FloorRightColor = hardwareMap.colorSensor.get("Floor_Right_Color");
        sensorGyro = hardwareMap.gyroSensor.get("gyro");
        waitOneFullHardwareCycle();
        FloorLeftColor.enableLed(false);
        waitOneFullHardwareCycle();
        FloorRightColor.enableLed(false);
        waitOneFullHardwareCycle();

        PullUp_Motor_Tape = hardwareMap.dcMotor.get("PullUp_Motor_Tape");
        //PullUp_Motor_String = hardwareMap.dcMotor.get("PullUp_Motor_String");

        motorRight1.setDirection(DcMotor.Direction.REVERSE);
        motorRight2.setDirection(DcMotor.Direction.REVERSE);
        motorLeft1.setDirection(DcMotor.Direction.FORWARD);
        motorLeft2.setDirection(DcMotor.Direction.FORWARD);
        waitOneFullHardwareCycle();

        //ENCODERS Setup
        motorLeft1.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        motorLeft2.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        motorRight1.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        motorRight2.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        waitOneFullHardwareCycle();
        CurrentPositionatEndOfEncoderRun = motorRight1.getCurrentPosition();
        telemetry.addData("Encoder Initialization Complete", 1);
        telemetry.addData("Don't start yet!", 1);
        sleep(1500);


        //GYRO Setup
        sensorGyro.calibrate();
        while (sensorGyro.isCalibrating()) {
            telemetry.addData("Gyro Sensor Calibrating.......", 2);
        }
        telemetry.addData("Gyro Calibration Complete", 3);
        waitOneFullHardwareCycle();
        sleep(2000);
        headingPrevious = sensorGyro.getHeading();
        waitOneFullHardwareCycle();
        headingCurrent = sensorGyro.getHeading();
        waitOneFullHardwareCycle();
        telemetry.addData("Gyro Initialization Complete", 4);
        telemetry.clearData();
        GyroHeadingDifference(); //Calculate Gyro Difference
        sleep(1000);

        telemetry.addData("Go!", 6);
        telemetry.addData("Initialization Complete, Ready for Program", 5);
        waitForStart(); //WAIT FOR START

        //TotalTime.reset();
        //telemetry.addData("Total Time:", TotalTime);

        //Encoder Setup
        motorLeft1.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        motorLeft2.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        motorRight1.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        motorRight2.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        Scoop_Motor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        CurrentPositionatEndOfEncoderRun = motorRight1.getCurrentPosition();

        //Color Sensor Setup
        FloorLeftColor.enableLed(true);
        FloorRightColor.enableLed(true);
    }


    //REUSABLE FUNCTIONS
    //POWER DEFINITIONS
    public void SetLeftMotors(double level) {
        motorRight1.setPower(level);
        motorRight2.setPower(level);
    }

    public void SetRightMotors(double level) {
        motorLeft1.setPower(level);
        motorLeft2.setPower(level);
    }

    //ENCODERS
    public double EncoderCountsToInches(double InchesTarget) {
        return ((InchesTarget * 1680) / (2 * PI * (11 / 4)));
    }

    public void RunWithEncoders(double SetPowerLeft, double SetPowerRight, double TargetPosition, int TelemetryPosition) throws InterruptedException {
        //telemetry.addData("Position in Program", TelemetryPosition);
        //telemetry.addData("Distance (In):", TargetPosition);
        //telemetry.addData("Motor Power Left/Right" + SetPowerLeft, SetPowerRight);
        //sleep(1000);

        //Positive values on any of the motors SHOULD move it forward, with scoop as front.
        SetLeftMotors(SetPowerLeft);

        SetRightMotors(SetPowerRight);


        telemetry.addData("Set Power", 1);
        while (abs(CurrentPositionatEndOfEncoderRun - motorRight1.getCurrentPosition()) <= abs(EncoderCountsToInches(TargetPosition))) {
            telemetry.addData("Position in Program:", TelemetryPosition);
            telemetry.addData("Distance (In):", TargetPosition);
            telemetry.addData("Motor Power Left/Right" + SetPowerLeft, SetPowerRight);

            //telemetry.addData("Wait For Position", 2);
            telemetry.addData("Current Encoder Counts Right:", String.valueOf(motorRight1.getCurrentPosition()));
            telemetry.addData("Current Encoder Counts Left:", String.valueOf(motorLeft1.getCurrentPosition()));
            telemetry.addData("Left Motor Power:", String.valueOf(motorLeft1.getPower()));
            telemetry.addData("Left Motor Power:", String.valueOf(motorLeft2.getPower()));
            telemetry.addData("Right Motor Power:", String.valueOf(motorRight1.getPower()));
            telemetry.addData("Right Motor Power:", String.valueOf(motorRight2.getPower()));
            //waitOneFullHardwareCycle();
        }
        telemetry.addData("Stop Motors", 1);
        SetLeftMotors(0);
        SetRightMotors(0);

        telemetry.addData("Total Distance 'Run':", TargetPosition);
        telemetry.addData("Current Encoder Counts @ End of Run:", String.valueOf(CurrentPositionatEndOfEncoderRun));
        ResetAndPrepareAllVariables();
    }

    //GYRO
    /*
    Gyro turn function (POSITIVE turn_power inputs will result in RIGHT turns
    while NEGATIVE ones will result in LEFT turns)
     */

    public void Turn_degrees(double turn_power, double turn_degrees, int TelemetryPosition) throws InterruptedException {
        headingTarget = turn_degrees; //Code Turn Limits from -180 < Theta < +180....HW Limits likely -170 to 170

        GyroHeadingDifference();

        telemetry.addData("Position in Program:", TelemetryPosition);
        telemetry.addData("Heading Previous", headingPrevious);
        telemetry.addData("Heading Current", headingCurrent);
        telemetry.addData("Heading Difference", headingDifference);
        telemetry.addData("Heading Target", headingTarget);

        //sleep(SLEEP);

        SetLeftMotors(turn_power);
        SetRightMotors(-turn_power);

        while (abs(headingDifference) < abs(headingTarget)) {
            headingCurrent = sensorGyro.getHeading();
            waitOneFullHardwareCycle();

            GyroHeadingDifference();

            telemetry.addData("Previous:", String.valueOf(headingPrevious));
            telemetry.addData("Current:", String.valueOf(headingCurrent));
            telemetry.addData("Target:", String.valueOf(headingTarget));
            telemetry.addData("Difference:", String.valueOf(headingDifference));
        }

        SetLeftMotors(0);
        SetRightMotors(0);

        telemetry.addData("Previous Heading:", String.valueOf(headingPrevious));
        ResetAndPrepareAllVariables();
    }

    public void GyroHeadingDifference() {
        if (abs(headingPrevious - headingCurrent) > headingTarget) //If the difference is greater than the target
        {
            headingDifference = abs(abs(headingPrevious - headingCurrent) - 360); //Then the difference can be corrected by -360
        } else {
            headingDifference = abs(headingPrevious - headingCurrent); //Otherwise, difference is in a good zone
        }

        if (abs(headingTarget) > 180) {
            telemetry.addData("|Target Heading| > 180* !!!!!!!!!", 1); //If Target is over limit....then CAUTION!!!
        }
    }

    //float hsvValues[] = {0F,0F,0F};
    //final float values[] = hsvValues;
    //final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(R.id.RelativeLayout);

    //COLOR SENSOR
    public void ColorSensorDriving(double LeftMotorPower, double RightMotorPower, boolean BlueColor, int TelemetryPosition) throws InterruptedException
    {
        float hsvValues[] = {0F,0F,0F};
        final float values[] = hsvValues;
        //Blue from 198* to 279*
        //Red from 342* to 18*
        FloorColorLeftHue();
        if(FloorLeftColor.alpha() > 5) {
            telemetry.addData("Clear > 5", 1);
            if (FloorColorLeftHue() < 279 & FloorColorLeftHue() > 198) {
                telemetry.addData("Blue!", 1);
            } else {
                if (FloorColorLeftHue() < 360 && FloorColorLeftHue() > 342 || FloorColorLeftHue() >= 0 && FloorColorLeftHue() < 18) {
                    telemetry.addData("Red!", 1);
                } else {
                    telemetry.addData("No Color", 1);
                }
            }
        }
        else
        {
            telemetry.addData("Clear < 5, No Color?", 1);
            telemetry.addData("Clear", FloorLeftColor.alpha());
            telemetry.addData("Hue", hsvValues[0]);
        }

    ResetAndPrepareAllVariables();
    //If both sensors do not see color, go forward/backward
    //If one sensor sees, put the side that sees the color into a slower reverse, other side that does not see slows down
    //If both color sensors see, break
    }
    public double FloorColorLeftHue() throws InterruptedException
    {
        float hsvValues[] = {0F,0F,0F};
        final float values[] = hsvValues;
        final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(R.id.RelativeLayout);

        Color.RGBToHSV(FloorLeftColor.red()*8, FloorLeftColor.green()*8, FloorLeftColor.blue()*8, hsvValues);

        telemetry.addData("Clear", FloorLeftColor.alpha());
        //telemetry.addData("Red  ", FloorLeftColor.red());
        //telemetry.addData("Green", FloorLeftColor.green());
        //telemetry.addData("Blue ", FloorLeftColor.blue());
        telemetry.addData("Hue", hsvValues[0]);

        // change the background color to match the color detected by the RGB sensor.
        // pass a reference to the hue, saturation, and value array as an argument
        // to the HSVToColor method.
        relativeLayout.post(new Runnable() {
            public void run() {
             relativeLayout.setBackgroundColor(Color.HSVToColor(0xff, values));
                }
        }); waitOneFullHardwareCycle();

        //ResetAndPrepareAllVariables();
        return hsvValues[0];
    }

    //Run for TIME
    public void RunForTime(double LeftMotorPower, double RightMotorPower, double TimeToRun, int TelemetryPosition) throws InterruptedException
    {
        telemetry.addData("Position in Program:", TelemetryPosition);
        mStateTime.reset(); waitOneFullHardwareCycle();
        while (mStateTime.time() < TimeToRun)
        {
            SetLeftMotors(LeftMotorPower);
            SetRightMotors(RightMotorPower);
            telemetry.addData("mStateTime", mStateTime.time());
        }
        SetLeftMotors(0);
        SetRightMotors(0);
        ResetAndPrepareAllVariables();
    }

    //RESET and PREPARE ALL VARIABLES!!!
    public void ResetAndPrepareAllVariables () throws InterruptedException
    {
        sleep(SLEEP);
        headingPrevious = sensorGyro.getHeading(); waitOneFullHardwareCycle();
        CurrentPositionatEndOfEncoderRun = motorRight1.getCurrentPosition();
        sleep(SLEEP_After_Movement);
    }
}