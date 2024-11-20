// Written by: Christopher Gholmieh:
// Package:
package org.firstinspires.ftc.teamcode.Subsystems.Intake;

// Imports:
import org.firstinspires.ftc.teamcode.Constants.Subsystem.Subsystem;
import org.firstinspires.ftc.teamcode.Constants.Channel.Channel;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;


// Class:
public class Intake implements Subsystem {
    // Variables (Declaration):
    protected DcMotor horizontal_slide_right;
    protected DcMotor horizontal_slide_left;

    // Variables (Assignment):
    private final double conversion = 0.25;
    protected double power = 0.0;

    // Constructor:
    public Intake(HardwareMap hardware_map) {
        // Variables (Definition):
        horizontal_slide_right = hardware_map.get(DcMotor.class, "horizontal-slide-right");
        horizontal_slide_left = hardware_map.get(DcMotor.class, "horizontal-slide-left");

        // Initialization:
        horizontal_slide_right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        horizontal_slide_left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    // Methods:
    /**
     * 
     * @param minimum The minimum value that the value parameter has to be greater than.
     * @param value The value parameter that you would like to be clamped.
     * @param maximum The maximum value that the value parameter has to be less than.
     * 
     * @return The clamped value, or the value greater than minimum and less than maximum.
     */
    private double clamp(double minimum, double value, double maximum) {
        if (value < minimum) {
            return minimum;
        }

        if (value > maximum) {
            return maximum;
        }

        return value;
    }

    /**
     * Supplies the same power to both horizontal linear slides.
     * 
     * @param linear_slide_power The power you would like both linear slide motors to be set to.
     */
    private void synchronized_power(double linear_slide_power) {
        horizontal_slide_right.setPower(linear_slide_power);
        horizontal_slide_left.setPower(linear_slide_power);
    }

    @Override
    public void update(Channel channel) {
        // Power:
        power = clamp(-1.0, (power + (-channel.gamepad_one_left_stick_x) * conversion), 1.0);

        // Logic:
        synchronized_power(power);
    }
}