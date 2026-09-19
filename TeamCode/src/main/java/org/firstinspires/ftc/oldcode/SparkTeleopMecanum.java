/*
 * Copyright (c) 2025 FIRST
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior
 * written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;


@TeleOp(name = "LM3TeleopMecanum", group = "StarterBot")
//@Disabled
public class SparkTeleopMecanum extends OpMode {
   final double STOP_SPEED = 0.0; //We send this power to the servos when we want them to stop.



    //how fast the launcher spins for shorter range
    final double LAUNCHER_POWER = 0.8;


    // Declare OpMode members.
    //wheel motors
    private DcMotor leftFrontDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightBackDrive = null;
    //launching motors
    private DcMotorEx leftLauncher = null;
    private DcMotorEx rightLauncher = null;
    // feeder servos
    private CRServo lowerRightFeeder = null;
    private CRServo lowerLeftFeeder = null;
    private CRServo upperRightFeeder = null;
    private CRServo upperLeftFeeder = null;
    private CRServo middleRightFeeder = null;
    private CRServo middleLeftFeeder = null;
    // surgical tubing intake motor
    private DcMotor intakeMotor = null;
    // adjusts wheel motor power
    private double speedMultiplier = 1;

    // Setup a variable for each drive wheel to save power level for telemetry
    double leftFrontPower;
    double rightFrontPower;
    double leftBackPower;
    double rightBackPower;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {

        /*
         * Initialize the hardware variables. Note that the strings used here as parameters
         * to 'get' must correspond to the names assigned during the robot configuration
         * step in the driver hub
         */
        leftFrontDrive = hardwareMap.get(DcMotor.class, "FrontLeft");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "FrontRight");
        leftBackDrive = hardwareMap.get(DcMotor.class, "BackLeft");
        rightBackDrive = hardwareMap.get(DcMotor.class, "BackRight");
        leftLauncher = hardwareMap.get(DcMotorEx.class, "LeftPitcher");
        rightLauncher = hardwareMap.get(DcMotorEx.class, "RightPitcher");
        lowerRightFeeder = hardwareMap.get(CRServo.class, "LowerRightGeckoWheel");
        lowerLeftFeeder = hardwareMap.get(CRServo.class, "LowerLeftGeckoWheel");
        middleRightFeeder = hardwareMap.get(CRServo.class, "MiddleRightGeckoWheel");
        middleLeftFeeder = hardwareMap.get(CRServo.class, "MiddleLeftGeckoWheel");
        upperRightFeeder = hardwareMap.get(CRServo.class, "UpperRightGeckoWheel");
        upperLeftFeeder = hardwareMap.get(CRServo.class, "UpperLeftGeckoWheel");
        intakeMotor = hardwareMap.get(DcMotor.class, "IntakeMotor");
        /*
         * To drive forward, most robots need the motor on one side to be reversed,
         * because the axles point in opposite directions. Pushing the left stick forward
         * MUST make robot go forward. So adjust these four lines based on your first test drive.
         * Note: The settings here assume direct drive on left and right wheels. Gear
         * Reduction or 90 Deg drives may require direction flips
         */
        leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        rightBackDrive.setDirection(DcMotor.Direction.FORWARD);

        /*
         * Here we set our launchers to the RUN_USING_ENCODER runmode.
         * If you notice that you have no control over the velocity of the motor, it just jumps
         * right to a number much higher than your set point, make sure that your encoders are plugged
         * into the port right beside the motor itself. And that the motors polarity is consistent
         * through any wiring.
         */
        leftLauncher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightLauncher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        /*
         * Setting zeroPowerBehavior to BRAKE enables a "brake mode". This causes the motor to
         * slow down much faster when it is coasting. This creates a much more controllable
         * drivetrain. As the robot stops much quicker. we also added brakes to the motors
         */
        leftFrontDrive.setZeroPowerBehavior(BRAKE);
        rightFrontDrive.setZeroPowerBehavior(BRAKE);
        leftBackDrive.setZeroPowerBehavior(BRAKE);
        rightBackDrive.setZeroPowerBehavior(BRAKE);
        leftLauncher.setZeroPowerBehavior(BRAKE);
        rightLauncher.setZeroPowerBehavior(BRAKE);

        /*
         * set Feeders to an initial value to initialize the servo controller
         */
        lowerRightFeeder.setPower(STOP_SPEED);
        lowerLeftFeeder.setPower(STOP_SPEED);
        middleRightFeeder.setPower(STOP_SPEED);
        middleLeftFeeder.setPower(STOP_SPEED);
        upperLeftFeeder.setPower(STOP_SPEED);
        upperRightFeeder.setPower(STOP_SPEED);

        leftLauncher.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(300, 0, 0, 10));
        rightLauncher.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(300, 0, 0, 10));


        /*
         * Much like our drivetrain motors, we set the right servos
         *  to reverse so that they all work to feed the ball into the robot.
         */
        lowerRightFeeder.setDirection(DcMotorSimple.Direction.REVERSE);
        middleRightFeeder.setDirection(DcMotorSimple.Direction.REVERSE);
        upperRightFeeder.setDirection(DcMotorSimple.Direction.REVERSE);
        lowerLeftFeeder.setDirection(DcMotorSimple.Direction.FORWARD);
        upperLeftFeeder.setDirection(DcMotor.Direction.FORWARD);
        middleLeftFeeder.setDirection(DcMotor.Direction.FORWARD);

        leftLauncher.setDirection(DcMotor.Direction.REVERSE);
        rightLauncher.setDirection(DcMotor.Direction.FORWARD);

        intakeMotor.setDirection(DcMotor.Direction.REVERSE);




        /*
         * Tell the driver that initialization is complete.
         */
        telemetry.addData("Status", "Initialized");
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit START
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits START
     */
    @Override
    public void start() {
    }

    /*
     * Code to run REPEATEDLY after the driver hits START but before they hit STOP
     */
    @Override
    public void loop() {
        /*
         * Here we call a function called mecanumDrive. The mecanumDrive function
         * takes the input from the joysticks, and applies power to the motors to
         *  move the robot as requested by the driver.
         */
       mecanumDrive(-gamepad1.left_stick_y, -gamepad1.left_stick_x, gamepad1.right_stick_x);

         // Here we give driver 2 control of the feeder wheels.
        double bootWheelPower = -gamepad2.left_stick_y;
        double intakePower = gamepad2.right_stick_y;
        upperRightFeeder.setPower(bootWheelPower);
        upperLeftFeeder.setPower(bootWheelPower);
        lowerRightFeeder.setPower(bootWheelPower);
        lowerLeftFeeder.setPower(bootWheelPower);
        middleRightFeeder.setPower(bootWheelPower);
        middleLeftFeeder.setPower(bootWheelPower);
        //if the boot wheels are not spinning, but the intake is powered
        //the lower boot wheels will spin to help with intaking
        if(bootWheelPower == 0)
        {
            lowerRightFeeder.setPower(Math.abs(intakePower));
            lowerLeftFeeder.setPower(Math.abs(intakePower));
        }
        // Here we set the controls to the intake motor with surgical tubing
        intakeMotor.setPower(Math.abs(intakePower));

        if (gamepad2.b) { // stop flywheels
            leftLauncher.setPower(STOP_SPEED);
            rightLauncher.setPower(STOP_SPEED);
        }
        else if (gamepad2.a) // sets launchers to launch the ball
        {
            leftLauncher.setPower(LAUNCHER_POWER);
            rightLauncher.setPower(LAUNCHER_POWER);
        }

        // show Velocity of left and right Launcher wheels on driver hub screen
        telemetry.addData("LeftLauncherVelocity", leftLauncher.getVelocity());
        telemetry.addData("RightLauncherVelocity", rightLauncher.getVelocity());

    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }
    void mecanumDrive(double forward, double strafe, double rotate){

        //if driver 1 holds down the left bumper, the orientation of the robot is reversed
        if(gamepad1.left_bumper) {
            forward = -forward;
            strafe = -strafe;
        }
        // if driver 1 pushes b the robot slows down by 80%
        if(gamepad1.y) {
            speedMultiplier = 0.2;
        }
        // if driver 1 pushes b the robot slows down by 50%
        if(gamepad1.b) {
            speedMultiplier = 0.5;
        }
        //if driver 1 pushes a the robot goes normal speed
        if(gamepad1.a) {
            speedMultiplier = 1;
        }
        /* the denominator is the largest motor power (absolute value) or 1
         * This ensures all the powers maintain the same ratio,
         * but only if at least one is out of the range [-1, 1]
         */
        double denominator = Math.max(Math.abs(forward) + Math.abs(strafe) + Math.abs(rotate), 1);

        // we calculate the required motor powers to move the robot according to the joysticks
        leftFrontPower = (forward + strafe + rotate) / denominator;
        rightFrontPower = (forward - strafe - rotate) / denominator;
        leftBackPower = (forward - strafe + rotate) / denominator;
        rightBackPower = (forward + strafe - rotate) / denominator;

        // we set the power to each wheel multiplied by our speed multiplier
        leftFrontDrive.setPower(leftFrontPower * speedMultiplier);
        rightFrontDrive.setPower(rightFrontPower * speedMultiplier);
        leftBackDrive.setPower(leftBackPower * speedMultiplier);
        rightBackDrive.setPower(rightBackPower * speedMultiplier);
    }
}