package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Daniel on 2/5/2016.
 */
public class Team7104AutoR_Linear extends LinearOpMode {

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

        mStateTime.reset();

        // wait for the start button to be pressed
        waitForStart();

        MotorRightPower(.7); //DRIVE FORWARD TO BEACON
        MotorLeftPower(.7);
        while(mStateTime.time() >= 10){}//Wait...wait...wait
        StopAllMotors();

        MotorRightPower(-.5); //TURN TO FLOOR GOAL
        MotorLeftPower(.5);
        while(mStateTime.time() >= 2){}//Wait...wait...wait
        StopAllMotors();

        MotorRightPower(.3); //DRIVE FORWARD INTO FLOOR GOAL
        MotorLeftPower(.3);
        while(mStateTime.time() >= 10){}//Wait...wait...wait
        StopAllMotors();
    }

public void MotorRightPower(double RightPower){
    motorRight1.setPower(RightPower);
    motorRight2.setPower(RightPower);
}
    public void MotorLeftPower(double LeftPower){
        motorRight1.setPower(LeftPower);
        motorRight2.setPower(LeftPower);
    }
public void StopAllMotors(){
    motorLeft1.setPower(0);
    motorLeft2.setPower(0);
    motorRight1.setPower(0);
    motorRight2.setPower(0);
}
}