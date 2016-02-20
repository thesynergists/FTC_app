package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DcMotor;
import static java.lang.Math.*;

/**
 * Created by Daniel on 2/12/2016.
 */

/*
    Encoder Counts/Revolution
    NeveRest 20: 560
    NeveRest 40: 1120
    NeveRest 60: 1680
*/

public class Team7104EncoderTest extends LinearOpMode{

    DcMotor motorLeft1;
    DcMotor motorLeft2;

    DcMotor motorRight1;
    DcMotor motorRight2;

    int CurrentPositionatEndOfEncoderRun;
    final int SLEEP = 250;


    @Override
    public void runOpMode() throws InterruptedException {

        motorLeft1 = hardwareMap.dcMotor.get("motorLeft1");
        motorLeft2 = hardwareMap.dcMotor.get("motorLeft2");

        motorRight1 = hardwareMap.dcMotor.get("motorRight1");
        motorRight2 = hardwareMap.dcMotor.get("motorRight2");

        motorRight1.setDirection(DcMotor.Direction.REVERSE);
        motorRight2.setDirection(DcMotor.Direction.REVERSE);

        waitOneFullHardwareCycle();

        motorLeft1.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        motorLeft2.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        motorRight1.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        motorRight2.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        waitOneFullHardwareCycle();
        CurrentPositionatEndOfEncoderRun = motorLeft1.getCurrentPosition();
        telemetry.clearData();
        sleep(1000);
        telemetry.addData("Ready for Program", 1);

        waitForStart();
        motorLeft1.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        motorLeft2.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        motorRight1.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        motorRight2.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);

        //RunWithEncoders(-.5, .5, 30, 1);
        //sleep(2000);
        //RunWithEncoders(.5, .5, 5, 2);
        //sleep(2000);
        //RunWithEncoders(-.5,-.5, 35, 3);
        //sleep(2000);
        RunWithEncoders(.8,-.8, 11.25, 3);
        sleep(5000);
        RunWithEncoders(-.8,.8, 11.5, 3);
        sleep(5000);
        RunWithEncoders(.8,-.8, 11.75, 3);
        sleep(5000);
    }
    public double EncoderCountsToInches(double InchesTarget) {
         return ((InchesTarget*1680)/(2*PI*(11/4)));
    }
    public void RunWithEncoders(double SetPowerLeft, double SetPowerRight, double TargetPosition, int TelemetryPosition) throws InterruptedException {
        {
            telemetry.addData("Position in Program", TelemetryPosition);
            telemetry.addData("Distance (In):", TargetPosition);
            telemetry.addData("Motor Power Left/Right" + SetPowerLeft, SetPowerRight);
            sleep(1000);
            motorLeft1.setPower(SetPowerLeft);
            motorLeft2.setPower(SetPowerLeft);
            motorRight1.setPower(SetPowerRight);
            motorRight2.setPower(SetPowerRight);

            telemetry.addData("Set Power", 1);
            while (abs(CurrentPositionatEndOfEncoderRun - motorLeft1.getCurrentPosition()) < abs(EncoderCountsToInches(TargetPosition))) {
                telemetry.addData("Wait For Position", 2);
                telemetry.addData("Current Encoder Counts Right:", String.valueOf(motorRight1.getCurrentPosition()));
                telemetry.addData("Current Encoder Counts Left:", String.valueOf(motorLeft1.getCurrentPosition()));
                telemetry.addData("Left Motor Power:", String.valueOf(motorLeft1.getPower()));
                telemetry.addData("Left Motor Power:", String.valueOf(motorLeft2.getPower()));
                telemetry.addData("Right Motor Power:", String.valueOf(motorRight1.getPower()));
                telemetry.addData("Right Motor Power:", String.valueOf(motorRight2.getPower()));
                //waitOneFullHardwareCycle();
            }
            telemetry.addData("Stop Motors", 3);
            motorLeft1.setPower(0);
            motorLeft2.setPower(0);
            motorRight1.setPower(0);
            motorRight2.setPower(0);

            sleep(SLEEP);
            CurrentPositionatEndOfEncoderRun = motorLeft1.getCurrentPosition();
            telemetry.addData("Current Encoder Counts @ End of Run:", String.valueOf(CurrentPositionatEndOfEncoderRun));
            waitOneFullHardwareCycle();
            sleep(SLEEP);
        }
    }
}
//  eg: if (Math.abs(target-position) < 100) stop motor.
   //     Then exit when all motors are off.