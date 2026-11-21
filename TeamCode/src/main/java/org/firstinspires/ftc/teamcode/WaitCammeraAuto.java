package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraCompatibilityManager;
import org.firstinspires.ftc.vision.VisionPortal;


@Autonomous(name="WaitCammeraAuto", group="Robot")
public class WaitCammeraAuto extends LinearOpMode
{



        private static final boolean USE_WEBCAM = true;

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
        private VisionPortal visionPortal;
        static final int VENDOR_ID_SUNPLUS_INNOVATION_TECHNOLOGY = 0x046D;
        static final int PRODUCT_ID_ARDUCAM_OV5648 = 0x0825;


        public final int LAUNCHER_TARGET_VELOCITY = 1324; //2678 RPM, for chainsaw extension 1375 ticks/s, original 1250

        @Override
        public void runOpMode() {

            /*
             * Initialize the hardware variables. Note that the strings used here as parameters
             * to 'get' must correspond to the names assigned during the robot configuration
             * step.
             */


            CameraCompatibilityManager.getInstance()
                    .addQuirk(
                            VENDOR_ID_SUNPLUS_INNOVATION_TECHNOLOGY,
                            PRODUCT_ID_ARDUCAM_OV5648,
                            CameraCompatibilityManager.Quirk.AVOID_LIB_USB_RESET_DEVICE);


            // Wait for the DS start button to be touched.
            telemetry.addData("DS preview on/off", "3 dots, Camera Stream");
            telemetry.addData(">", "Touch START to start OpMode");
            telemetry.update();
            waitForStart();

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
            sleep(1000);
            windmillServo.setPower(-1);
            intake.setPower(0.5);
            sleep(6000);
            windmillServo.setPower(0);
            launcher.setVelocity(0);
            intake.setPower(0);

        }

        public void move() {

            leftFrontDrive.setPower(-0.25);
            rightBackDrive.setPower(-0.25);
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

            leftFrontDrive.setPower(-0.25);
            rightBackDrive.setPower(-0.25);
            rightFrontDrive.setPower(-0.25);
            leftBackDrive.setPower(-0.25);

        }

        public void leftstrafe() {

            leftFrontDrive.setPower(0.25);
            rightBackDrive.setPower(-0.25);
            rightFrontDrive.setPower(0.25);
            leftBackDrive.setPower(-0.25);
    }
    public void rightstrafe() {

        leftFrontDrive.setPower(-0.25);
        rightBackDrive.setPower(0.25);
        rightFrontDrive.setPower(-0.25);
        leftBackDrive.setPower(0.25);

    }

        public void Stop() {

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