package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.VoltageSensor;


@Autonomous(name="AutoShootNoReset", group="Robot")
public class AutoShootNoReset extends LinearOpMode
{

    // Declare OpMode members.
    private DcMotor leftFrontDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightBackDrive = null;
    private DcMotorEx launcher = null;
    private CRServo windmillServo = null;
    private Servo shield1 = null;
    private Servo shield2 = null;
    private DcMotor intake = null;
    VoltageSensor battery = null;


    public final int LAUNCHER_TARGET_VELOCITY = 1324; //2678 RPM, for chainsaw extension 1375 ticks/s, original 1250

    @Override
    public void runOpMode() {

        /*
         * Initialize the hardware variables. Note that the strings used here as parameters
         * to 'get' must correspond to the names assigned during the robot configuration
         * step.
         */
        leftFrontDrive = hardwareMap.get(DcMotor.class, "left_front_drive");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "right_front_drive");
        leftBackDrive = hardwareMap.get(DcMotor.class, "left_back_drive");
        rightBackDrive = hardwareMap.get(DcMotor.class, "right_back_drive");
        launcher = hardwareMap.get(DcMotorEx.class, "launcher");
        windmillServo = hardwareMap.get(CRServo.class, "windmillServo");
        shield1 = hardwareMap.get(Servo.class, "shield1");
        shield2 = hardwareMap.get(Servo.class, "shield2");
        intake = hardwareMap.get(DcMotor.class, "intake");
        battery = hardwareMap.get(VoltageSensor.class, "Control Hub");
        launcher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        launcher.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(40, 0, 0, 12.5));

        leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        rightBackDrive.setDirection(DcMotor.Direction.FORWARD);

        leftFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();

        launcher.setVelocity(LAUNCHER_TARGET_VELOCITY);
        sleep(2000);
        windmillServo.setPower(-1);
        intake.setPower(0.5);
        sleep(6000);
        windmillServo.setPower(0);
        launcher.setVelocity(0);
        intake.setPower(0);
        move();
        long time = (long) (-550L * (battery.getVoltage()) + 14400L);
        sleep(time);
        turn();
        sleep(1750);
        moveBack();
        sleep(4500);
        Stop();
        moveLeft();
        sleep(4500);
        Stop();
        /*sleep(2000);
        celebration();*/
    }
    public void move()
    {

        leftFrontDrive.setPower(-0.25);
        rightBackDrive.setPower(-0.25);
        rightFrontDrive.setPower(-0.25);
        leftBackDrive.setPower(-0.25);

    }

    public void moveLeft(){

        leftFrontDrive.setPower(0.25);
        rightBackDrive.setPower(0.25);
        rightFrontDrive.setPower(-0.25);
        leftBackDrive.setPower(-0.25);
    }

    public void turn() {

        leftFrontDrive.setPower(-0.25);
        rightBackDrive.setPower(0.25);
        rightFrontDrive.setPower(0.25);
        leftBackDrive.setPower(-0.25);

    }

    public void moveBack() {

        leftFrontDrive.setPower(0.25);
        rightBackDrive.setPower(0.25);
        rightFrontDrive.setPower(0.25);
        leftBackDrive.setPower(0.25);

    }

    public void Stop(){

        leftFrontDrive.setPower(0);
        rightBackDrive.setPower(0);
        rightFrontDrive.setPower(0);
        leftBackDrive.setPower(0);
        sleep(500);
    }

    public void celebration() {
        shield1.setPosition(1);
        shield2.setPosition(0);
        sleep(500);
        shield1.setPosition(0.6);
        shield2.setPosition(0.4);
        sleep(500);
        shield1.setPosition(1);
        shield2.setPosition(0);
        sleep(500);
        shield1.setPosition(0.6);
        shield2.setPosition(0.4);
        sleep(500);
        shield1.setPosition(1);
        shield2.setPosition(0);
    }
}