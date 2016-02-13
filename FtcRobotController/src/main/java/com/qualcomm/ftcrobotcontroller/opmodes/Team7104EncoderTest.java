package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DcMotor;
import static java.lang.Math.*;

/**
 * Created by Daniel on 2/12/2016.
 */

public class Team7104EncoderTest extends LinearOpMode{

    DcMotor motorLeft1;
    DcMotor motorLeft2;

    DcMotor motorRight1;
    DcMotor motorRight2;

    int CurrentPositionatEndOfEncoderRun = motorLeft1.getCurrentPosition();;

    @Override
    public void runOpMode() throws InterruptedException {

        motorLeft1 = hardwareMap.dcMotor.get("motorLeft1");
        motorLeft2 = hardwareMap.dcMotor.get("motorLeft2");

        motorRight1 = hardwareMap.dcMotor.get("motorRight1");
        motorRight2 = hardwareMap.dcMotor.get("motorRight2");

        motorRight1.setDirection(DcMotor.Direction.REVERSE);
        motorRight2.setDirection(DcMotor.Direction.REVERSE);
        waitOneFullHardwareCycle();

        //Scoop_Motor = hardwareMap.dcMotor.get("Scoop_Motor");
        motorLeft1.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        motorLeft2.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        motorRight1.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        motorRight2.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        waitOneFullHardwareCycle();
        telemetry.clearData();

        waitForStart();
        motorLeft1.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        motorLeft2.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        motorRight1.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        motorRight2.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);

        RunWithEncoders(-.3, 10000, 250);
        RunWithEncoders(.3, 500, 250);
    }

    void RunWithEncoders(double SetPower, int TargetPosition, int Sleep) throws InterruptedException {
        {
            motorLeft1.setPower(SetPower);
            motorLeft2.setPower(SetPower);
            motorRight1.setPower(SetPower);
            motorRight2.setPower(SetPower);

            telemetry.addData("Set Power", 1);
            while(abs(CurrentPositionatEndOfEncoderRun-motorLeft1.getCurrentPosition()) < abs(TargetPosition)) {
                telemetry.addData("Wait For Position", 2);
                telemetry.addData("Current Encoder Counts Right:", String.valueOf(motorRight1.getCurrentPosition()));
                telemetry.addData("Current Encoder Counts Left:", String.valueOf(motorLeft1.getCurrentPosition()));
                telemetry.addData("Left Motor Power:", String.valueOf(motorLeft1.getPower()));
                telemetry.addData("Left Motor Power:", String.valueOf(motorLeft2.getPower()));
                telemetry.addData("Right Motor Power:", String.valueOf(motorRight1.getPower()));
                telemetry.addData("Left Motor Power:", String.valueOf(motorRight2.getPower()));
                //waitOneFullHardwareCycle();
            }
            telemetry.addData("Stop Motors", 3);
            motorLeft1.setPower(0);
            motorLeft2.setPower(0);
            motorRight1.setPower(0);
            motorRight2.setPower(0);

            sleep(Sleep);
            CurrentPositionatEndOfEncoderRun = motorLeft1.getCurrentPosition();
            telemetry.addData("Current Encoder Counts @ End of Run:", String.valueOf(CurrentPositionatEndOfEncoderRun));
            waitOneFullHardwareCycle();
            sleep(Sleep);
        }
    }
}
//  eg: if (Math.abs(target-position) < 100) stop motor.
   //     Then exit when all motors are off.
