// Written by: Bo-Yun Hsu
// Package:
package org.firstinspires.ftc.teamcode.Subsystems.Mecanum;

// Imports:
import org.firstinspires.ftc.teamcode.Constants.Channel.Channel;
import org.firstinspires.ftc.teamcode.Constants.Subsystem.Subsystem;
import org.firstinspires.ftc.teamcode.Extensions.IMUEx.IMUEx;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;


// Class:
public class Mecanum implements Subsystem {
    // Variables (Declaration):
    public DcMotor front_right, front_left, back_right, back_left;
    public IMUEx imu;

    // Constructor:
    public Mecanum(HardwareMap hardware_map) {
        // Variables (Definition):
        front_right = hardware_map.get(DcMotor.class, "front-right");
        front_left = hardware_map.get(DcMotor.class, "front-left");

        back_right = hardware_map.get(DcMotor.class, "back-right");
        back_left = hardware_map.get(DcMotor.class, "back-left");

        imu = new IMUEx(
            // Hardware:
            hardware_map,

            // Identifier:
            "imu",

            // Parameters:
            new RevHubOrientationOnRobot(
                // Logo:
                RevHubOrientationOnRobot.LogoFacingDirection.DOWN,

                // USB:
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD
            )
        );

        // Directions:
        front_left.setDirection(DcMotorSimple.Direction.REVERSE);
        back_left.setDirection(DcMotorSimple.Direction.REVERSE);

        // Brake:
        front_right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        front_left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        back_right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        back_left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    // Methods:
    @Override
    public void update(Channel channel) {
        // Variables (Assignment):
        double gamepad_one_right_stick_x = (channel.gamepad_one_right_stick_x);
        double gamepad_one_left_stick_x = (channel.gamepad_one_left_stick_x);
        double gamepad_one_left_stick_y = (-channel.gamepad_one_left_stick_y);

        // Logic:
        if (channel.gamepad_one_options) {
            imu.reset();
        }

        // Variables (Assignment):
        double bottom_heading = imu.get_heading_yaw();

        double rotation_x =
            (gamepad_one_left_stick_x *
                Math.cos(-bottom_heading) - gamepad_one_left_stick_y *
                Math.sin(-bottom_heading)
            ) * 1.1;

        double rotation_y =
            (gamepad_one_left_stick_x *
                Math.sin(-bottom_heading) + gamepad_one_left_stick_y *
                Math.cos(-bottom_heading)
            );

        double denominator = Math.max(
            Math.abs(rotation_y) + Math.abs(rotation_x) + Math.abs(gamepad_one_right_stick_x), 1
        );

        // Variables (Assignment):
        double front_right_power = (
            rotation_y - rotation_x - gamepad_one_right_stick_x
        ) / denominator;

        double front_left_power = (
            rotation_y + rotation_x + gamepad_one_right_stick_x
        ) / denominator;

        double back_right_power = (
            rotation_y + rotation_x - gamepad_one_right_stick_x
        ) / denominator;

        double back_left_power = (
            rotation_y - rotation_x + gamepad_one_right_stick_x
        ) / denominator;

        // Logic:
        front_right.setPower(front_right_power);
        front_left.setPower(front_left_power);

        back_right.setPower(back_right_power);
        back_left.setPower(back_left_power);
    }
}