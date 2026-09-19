/* Copyright (c) 2017 FIRST. All rights reserved.
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
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

/*
 * This OpMode illustrates the concept of driving a path based on time.
 * The code is structured as a LinearOpMode
 *
 * The code assumes that you do NOT have encoders on the wheels,
 *   otherwise you would use: RobotAutoDriveByEncoder;
 *
 *   The desired path in this example is:
 *   - Drive forward for 3 seconds
 *   - Spin right for 1.3 seconds
 *   - Drive Backward for 1 Second
 *
 *  The code is written in a simple form with no optimizations.
 *  However, there are several ways that this type of sequence could be streamlined,
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list
 */

@Autonomous(name="Robot: Auto Drive By Time With Shooting", group="Robot")
public class RobotLM3AutoShoot extends LinearOpMode {

    // Declare OpMode members.
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
    private ElapsedTime runtime = new ElapsedTime();
     final double LAUNCHER_POWER = 0.8;

    static final double     FORWARD_SPEED = 0.5;
    static final double     TURN_SPEED    = 0.5;

    @Override
    public void runOpMode() {

        /*
         * Initialize the hardware variables. Note that the strings used here as parameters
         * to 'get' must correspond to the names assigned during the robot configuration
         * step.
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
         * MUST make robot go forward. So adjust these two lines based on your first test drive.
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
        final double STOP_SPEED = 0.0; //We send this power to the servos when we want them to stop.
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

        // Wait for the game to start (driver presses START)
        waitForStart();

        // Step through each leg of the path, ensuring that the OpMode has not been stopped along the way.

        // Step 1:  Drive forward for 1 second
        leftFrontDrive.setPower(-FORWARD_SPEED);
        leftBackDrive.setPower(-FORWARD_SPEED);
        rightFrontDrive.setPower(-FORWARD_SPEED);
        rightBackDrive.setPower(-FORWARD_SPEED);
        sleep(2000);
        // Step 2:  Stop
        leftFrontDrive.setPower(0);
        leftBackDrive.setPower(0);
        rightFrontDrive.setPower(0);
        rightBackDrive.setPower(0);
        sleep(500);
        //
        leftLauncher.setPower(LAUNCHER_POWER);
        rightLauncher.setPower(LAUNCHER_POWER);
        sleep(250);
        double bootWheelPower = 1.;
        double intakePower = 1;
        upperRightFeeder.setPower(bootWheelPower);
        upperLeftFeeder.setPower(bootWheelPower);
        lowerRightFeeder.setPower(bootWheelPower);
        lowerLeftFeeder.setPower(bootWheelPower);
        middleRightFeeder.setPower(bootWheelPower);
        middleLeftFeeder.setPower(bootWheelPower);

        intakeMotor.setPower(Math.abs(intakePower));
        sleep(5000);
        //
        leftLauncher.setPower(0);
        rightLauncher.setPower(0);
        bootWheelPower = 0;
        intakePower = 0;
        upperRightFeeder.setPower(bootWheelPower);
        upperLeftFeeder.setPower(bootWheelPower);
        lowerRightFeeder.setPower(bootWheelPower);
        lowerLeftFeeder.setPower(bootWheelPower);
        middleRightFeeder.setPower(bootWheelPower);
        middleLeftFeeder.setPower(bootWheelPower);
        intakeMotor.setPower(Math.abs(intakePower));
        //
        leftFrontDrive.setPower(-FORWARD_SPEED);
        leftBackDrive.setPower(-FORWARD_SPEED);
        rightFrontDrive.setPower(-FORWARD_SPEED);
        rightBackDrive.setPower(-FORWARD_SPEED);
        sleep(1000);
        //
        leftFrontDrive.setPower(0);
        leftBackDrive.setPower(0);
        rightFrontDrive.setPower(0);
        rightBackDrive.setPower(0);
        sleep(1000);

        telemetry.addData("Path", "Complete");
        telemetry.update();
        sleep(1000);
    }
}