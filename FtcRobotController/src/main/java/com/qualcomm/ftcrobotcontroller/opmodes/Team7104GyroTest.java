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
    int headingError;
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

        headingTarget = 90;

        do {
            headingCurrent = sensorGyro.getHeading();
            if(headingCurrent > 180){
                headingCurrent = headingCurrent-360;
            }

            headingError = headingTarget-headingCurrent;
            driveSteering = headingError*driveGain;
            


        }
        while()


    }
}
