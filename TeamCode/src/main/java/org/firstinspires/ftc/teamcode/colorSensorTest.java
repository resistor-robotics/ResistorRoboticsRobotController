package org.firstinspires.ftc.teamcode;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

@TeleOp(name = "Color Sensor Test", group = "StarterBot")
//@Disabled
public class colorSensorTest extends OpMode {

    private NormalizedColorSensor colorSensor;

    // Map the sensor to the hardware configuration name
    public void init() {
        colorSensor = hardwareMap.get(NormalizedColorSensor.class, "colorSensor");
    }

    public void loop() {
        // Get a normalized reading of the RGBA values
        NormalizedRGBA colors = colorSensor.getNormalizedColors();

        // Display individual normalized color values (values range roughly from 0.0 to 1.0)
        telemetry.addData("Red", "%.3f", colors.red);
        telemetry.addData("Green", "%.3f", colors.green);
        telemetry.addData("Blue", "%.3f", colors.blue);

        // The sensor can also act as an optical distance/light sensor
        double light = ((OpticalDistanceSensor) colorSensor).getLightDetected();
        telemetry.addData("Light Detected", "%.3f", light);

        telemetry.update();
    }
}