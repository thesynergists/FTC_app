package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Daniel on 2/5/2016.
 */
public class Team7104AutoR_Linear extends LinearOpMode
{

    DcMotor motorLeft1;
    DcMotor motorLeft2;

    DcMotor motorRight1;
    DcMotor motorRight2;

    DcMotor Scoop_Motor;


    Servo rotation_servo;          //Make ourselves da servos! :)
    Servo pitch_servo_left;
    Servo pitch_servo_right;
    Servo Bacon_servo;
    Servo Conveyor_servo;
    Servo Sweep_servo;

    Servo Flipper_Servo_Left;
    Servo Flipper_Servo_Right;

    DcMotor PullUp_Motor;

    ColorSensor BaconColor;
    ColorSensor FloorRightColor;
    ColorSensor FloorLeftColor;

    private ElapsedTime mStateTime = new ElapsedTime();
    private ElapsedTime TotalTime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {

        motorLeft1 = hardwareMap.dcMotor.get("motorLeft1");
        motorLeft2 = hardwareMap.dcMotor.get("motorLeft2");

        motorRight1 = hardwareMap.dcMotor.get("motorRight1");
        motorRight2 = hardwareMap.dcMotor.get("motorRight2");

        Scoop_Motor = hardwareMap.dcMotor.get("Scoop_Motor");

        //Bacon_servo = hardwareMap.servo.get("Bacon_servo");

        //pitch_servo_left = hardwareMap.servo.get("pitch_servo_left");
        //pitch_servo_right = hardwareMap.servo.get("pitch_servo_right");

        Sweep_servo = hardwareMap.servo.get("Sweep_servo");
        Sweep_servo.setPosition(.5);

        Conveyor_servo = hardwareMap.servo.get("Conveyor_servo");
        Conveyor_servo.setPosition(.5);

        Flipper_Servo_Left = hardwareMap.servo.get("Flipper_Servo_Left");
        Flipper_Servo_Left.setPosition(.40);
        Flipper_Servo_Right = hardwareMap.servo.get("Flipper_Servo_Right");
        Flipper_Servo_Right.setPosition(.57);

        BaconColor = hardwareMap.colorSensor.get("Bacon_Color");
        FloorLeftColor = hardwareMap.colorSensor.get("Floor_Left_Color");
        FloorRightColor = hardwareMap.colorSensor.get("Floor_Right_Color");


        //PullUp_Motor = hardwareMap.servo.get("PullUp_Motor");
        motorRight1.setDirection(DcMotor.Direction.REVERSE);
        motorRight2.setDirection(DcMotor.Direction.REVERSE);



        // wait for the start button to be pressed
        waitForStart();
        TotalTime.reset();
        telemetry.addData("Total time", TotalTime);

        Scoop_Motor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        Scoop_Motor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        Scoop_Motor.setTargetPosition(660);
        Scoop_Motor.setPower(.1);


        mStateTime.reset();
        while (mStateTime.time() < 2)
        {

        }

        mStateTime.reset();
        while(mStateTime.time() <= 5)
        {
            telemetry.addData("State: forward", 0);
            MotorRightPower(.5); //DRIVE FORWARD TO BEACON
            MotorLeftPower(.5);
        }//Wait...wait...wait

        mStateTime.reset();
        while(mStateTime.time() <= .5)
        {
            MotorLeftPower(0);
            MotorRightPower(0);
        }

        mStateTime.reset();
        while(mStateTime.time() <= 1.3)
        {
            telemetry.addData("State: turning", 1);
            MotorRightPower(.4); //TURN TO FLOOR GOAL
            MotorLeftPower(-.4);
        }//Wait...wait...wait

        mStateTime.reset();
        while(mStateTime.time() <= .5)
        {
            MotorLeftPower(0);
            MotorRightPower(0);
        }

        mStateTime.reset();
        while(mStateTime.time() <= 1.4)
        {
            telemetry.addData("State: forward", 2);
            MotorRightPower(.3); //DRIVE FORWARD INTO FLOOR GOAL
            MotorLeftPower(.3);
        }//Wait...wait...wait

        while(mStateTime.time() <= 3 && mStateTime.time() > 1.4)
        {
            MotorLeftPower(0);
            MotorRightPower(0);
            telemetry.addData("State: done", 3);
        }

        Scoop_Motor.setTargetPosition(30);
        Scoop_Motor.setPower(.1);
    }

    public void MotorRightPower(double RightPower)
    {
        motorLeft1.setPower(RightPower);
        motorLeft2.setPower(RightPower);
    }
    public void MotorLeftPower(double LeftPower)
    {
        motorRight1.setPower(LeftPower);
        motorRight2.setPower(LeftPower);
    }
}