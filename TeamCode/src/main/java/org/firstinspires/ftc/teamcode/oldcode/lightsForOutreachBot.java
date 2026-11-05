package org.firstinspires.ftc.teamcode.oldcode;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Prism.Color;
import org.firstinspires.ftc.teamcode.Prism.GoBildaPrismDriver;
import org.firstinspires.ftc.teamcode.Prism.PrismAnimations;

@TeleOp(name = "LightUpBot", group = "StarterBot")
//@Disabled
public class lightsForOutreachBot extends OpMode {


    final Color ORANGE = new Color(255, 100, 0);

    PrismAnimations.Snakes right = new PrismAnimations.Snakes(ORANGE);

    PrismAnimations.Solid middleSolid = new PrismAnimations.Solid(Color.PURPLE);
    PrismAnimations.PoliceLights middlePoliceLights = new PrismAnimations.PoliceLights();

    PrismAnimations.Snakes left = new PrismAnimations.Snakes(ORANGE);

    GoBildaPrismDriver prism;


    @Override
    public void init() {

        prism = hardwareMap.get(GoBildaPrismDriver.class, "PrismDriver");
        right.setBrightness(100);
        right.setStartIndex(0);
        right.setStopIndex(11);
        prism.insertAndUpdateAnimation(GoBildaPrismDriver.LayerHeight.LAYER_0, right);

        middleSolid.setBrightness(100);
        middleSolid.setStartIndex(12);
        middleSolid.setStopIndex(17);
        prism.insertAndUpdateAnimation(GoBildaPrismDriver.LayerHeight.LAYER_1, middleSolid);

        middlePoliceLights.setBrightness(100);
        middlePoliceLights.setStartIndex(12);
        middlePoliceLights.setStopIndex(17);
        middlePoliceLights.setPrimaryColor(Color.PURPLE);
        middlePoliceLights.setSecondaryColor(Color.WHITE);
        middlePoliceLights.setTertiaryColor(ORANGE);

        left.setBrightness(100);
        left.setStartIndex(18);
        left.setStopIndex(29);
        prism.insertAndUpdateAnimation(GoBildaPrismDriver.LayerHeight.LAYER_2, left);
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

    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }
}