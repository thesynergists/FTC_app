package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Daniel on 2/12/2016.
 */

public class Team7104EncoderTest extends LinearOpMode{

    DcMotor motorLeft1;
    DcMotor motorLeft2;

    DcMotor motorRight1;
    DcMotor motorRight2;

    @Override
    public void runOpMode() throws InterruptedException {

        motorLeft1 = hardwareMap.dcMotor.get("motorLeft1");
        motorLeft2 = hardwareMap.dcMotor.get("motorLeft2");

        motorRight1 = hardwareMap.dcMotor.get("motorRight1");
        motorRight2 = hardwareMap.dcMotor.get("motorRight2");

        motorRight1.setDirection(DcMotor.Direction.REVERSE);
        motorRight2.setDirection(DcMotor.Direction.REVERSE);

        //Scoop_Motor = hardwareMap.dcMotor.get("Scoop_Motor");
        motorLeft1.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        motorLeft2.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        motorRight1.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        motorRight2.setMode(DcMotorController.RunMode.RESET_ENCODERS);

        waitForStart();

        motorLeft1.setPower(.5);
        motorLeft2.setPower(.5);
        motorRight1.setPower(.5);
        motorRight2.setPower(.5);
        while (motorLeft1.getCurrentPosition() < 1000) {
            waitOneFullHardwareCycle();
        }
        motorLeft1.setPower(0);
        motorLeft2.setPower(0);
        motorRight1.setPower(0);
        motorRight2.setPower(0);
    }
}
//  eg: if (Math.abs(target-position) < 100) stop motor.
   //     Then exit when all motors are off.
