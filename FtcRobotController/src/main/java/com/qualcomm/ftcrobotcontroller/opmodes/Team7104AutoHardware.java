package com.qualcomm.ftcrobotcontroller.opmodes;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
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
public class Team7104AutoHardware extends LinearOpMode
{

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

    int CurrentPositionatEndOfEncoderRun;
    final int SLEEP = 250;
    int headingTarget;
    int headingCurrent;
    int headingPrevious;
    double headingDifference;
    double SetPower;
    //double driveSteering;
    //double driveGain = 0.7;

    @Override
    public void runOpMode() throws InterruptedException
    {
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
        Climber_servo.setPosition(0);                               //Hopefully the neutral position...

        Flipper_Servo_Left = hardwareMap.servo.get("Flipper_Servo_Left");
        Flipper_Servo_Left.setPosition(.40);
        Flipper_Servo_Right = hardwareMap.servo.get("Flipper_Servo_Right");
        Flipper_Servo_Right.setPosition(.57);

        BaconColor = hardwareMap.colorSensor.get("Bacon_Color");
        FloorLeftColor = hardwareMap.colorSensor.get("Floor_Left_Color");
        FloorRightColor = hardwareMap.colorSensor.get("Floor_Right_Color");
        sensorGyro = hardwareMap.gyroSensor.get("gyro");

        //PullUp_Motor_Tape = hardwareMap.dcMotor.get("PullUp_Motor_Tape");
        //PullUp_Motor_String = hardwareMap.dcMotor.get("PullUp_Motor_String");

        motorRight1.setDirection(DcMotor.Direction.REVERSE);
        motorRight2.setDirection(DcMotor.Direction.REVERSE);
        motorLeft1.setDirection(DcMotor.Direction.FORWARD);
        motorLeft2.setDirection(DcMotor.Direction.FORWARD);

        //ENCODERS Setup

        waitOneFullHardwareCycle();
        motorLeft1.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        motorLeft2.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        motorRight1.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        motorRight2.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        waitOneFullHardwareCycle();
        CurrentPositionatEndOfEncoderRun = motorRight1.getCurrentPosition();
        telemetry.addData("Encoder Initialization Complete", 1);
        sleep(1500);



        //GYRO Setup
        sensorGyro.calibrate();
        while (sensorGyro.isCalibrating())
        {
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


        telemetry.addData("Initialization Complete, Ready for Program", 5);
        telemetry.addData("Go!", 6);
        waitForStart();

        motorLeft1.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        motorLeft2.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        motorRight1.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        motorRight2.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
    }

    //REUSABLE FUNCTIONS

    //POWER DEFINITIONS
    public void SetLeftMotors (double level)
    {
        motorRight1.setPower(level);
        motorRight2.setPower(level);
    }

    public void SetRightMotors (double level)
    {
        motorLeft1.setPower(level);
        motorLeft2.setPower(level);
    }

    //ENCODERS
    public double EncoderCountsToInches(double InchesTarget)
    {
        return ((InchesTarget * 1680) / (2 * PI * (11 / 4)));
    }

    public void RunWithEncoders(double SetPowerLeft, double SetPowerRight, double TargetPosition, int TelemetryPosition) throws InterruptedException
    {
        //telemetry.addData("Position in Program", TelemetryPosition);
        //telemetry.addData("Distance (In):", TargetPosition);
        //telemetry.addData("Motor Power Left/Right" + SetPowerLeft, SetPowerRight);
        //sleep(1000);

        //Positive values on any of the motors SHOULD move it forward, with scoop as front.
        SetLeftMotors(SetPowerLeft);

        SetRightMotors(SetPowerRight);


        telemetry.addData("Set Power", 1);
        while (abs(CurrentPositionatEndOfEncoderRun - motorRight1.getCurrentPosition()) <= abs(EncoderCountsToInches(TargetPosition)))
        {
            telemetry.addData("Position in Program", TelemetryPosition);
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

        sleep(SLEEP);
        CurrentPositionatEndOfEncoderRun = motorRight1.getCurrentPosition();
        telemetry.addData("Total Distance 'Run':", TargetPosition);
        telemetry.addData("Current Encoder Counts @ End of Run:", String.valueOf(CurrentPositionatEndOfEncoderRun));
        waitOneFullHardwareCycle();
        headingPrevious = sensorGyro.getHeading();
        waitOneFullHardwareCycle();
        sleep(SLEEP);
    }

    //GYRO
    public void GyroHeadingDifference()
    {
        if (abs(headingPrevious - headingCurrent) > headingTarget) //If the difference is greater than the target
        {
            headingDifference = abs(abs(headingPrevious - headingCurrent) - 360); //Then the difference can be corrected by -360
        }

        else
        {
            headingDifference = abs(headingPrevious - headingCurrent); //Otherwise, difference is in a good zone
        }

        if (abs(headingTarget) > 180)
        {
            telemetry.addData("|Target Heading| > 180* !!!!!!!!!", 1); //If Target is over limit....then CAUTION!!!
        }
    }

    //COLOR SENSOR
    public void ColorSensorDriving(double LeftMotorPower, double RightMotorPower, boolean BlueColor)
    {

    }
}