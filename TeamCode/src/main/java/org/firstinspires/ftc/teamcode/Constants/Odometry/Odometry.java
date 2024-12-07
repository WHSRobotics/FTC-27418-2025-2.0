// Written by: Christopher Gholmieh
// Package:
package org.firstinspires.ftc.teamcode.Constants.Odometry;

// Imports:
import org.firstinspires.ftc.teamcode.Extensions.IMUEx.IMUEx;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.Locale;


// Class:
public class Odometry {
    // Variables (Declaration):
    private static Odometry singleton_instance;

    // Variables (Assignment):
    private final double TICKS_PER_REVOLUTION = 19512;
    private final double WHEEL_DIAMETER = 4.09;

    private final double TICKS_PER_INCH = (
        TICKS_PER_REVOLUTION / (WHEEL_DIAMETER * Math.PI)
    );

    public double position_x = 0;
    public double position_y = 0;
    public double heading = 0;

    private double previous_front_right_position = 0;
    private double previous_front_left_position = 0;

    private double previous_back_right_position = 0;
    private double previous_back_left_position = 0;

    // Constructor:
    public Odometry() {}

    // Methods:

    /**
     *
     * @return The odometry status for telemetry/debugging purposes.
     */
    public String get_odometry_status() {
        return String.format(
            // Locale:
            Locale.ENGLISH,

            // Format:
            "X-Coordinate: %.2f, Y-Coordinate: %.2f, Heading-Angle: %.2f",

            // Arguments:
            position_x, position_y, heading
        );
    }

    /**
     *
     * @return The odometry values for telemetry/debugging purposes.
     */
    public double[] get_odometry_values() {
        return new double[] {position_x, position_y, heading};
    }

    /**
     *
     * @param angle The angle to normalize.
     *
     * @return The normalized angle between -PI and PI.
     */
    private double normalize_angle(double angle) {
        return (angle + Math.PI) % (2 * Math.PI) - Math.PI;
    }

    /**
     *
     * @param front_right The front right motor of the mecanum drive.
     * @param front_left The front left motor of the mecanum drive.
     *
     * @param back_right The back right motor of the mecanum drive.
     * @param back_left The back left motor of the mecanum drive.
     *
     * @param imu The inertial measurement unit of the mecanum drive.
     */
    public void update_odometry(
        // Motors:
        DcMotor front_right,
        DcMotor front_left,

        DcMotor back_right,
        DcMotor back_left,

        // IMU:
        IMUEx imu
    ) {
        // Variables (Assignment):
        double front_right_position = front_right.getCurrentPosition();
        double front_left_position = front_left.getCurrentPosition();

        double back_right_position = back_right.getCurrentPosition();
        double back_left_position = back_left.getCurrentPosition();

        // Variables (Assignment):
        double delta_front_right_position = (front_right_position - previous_front_right_position) / TICKS_PER_INCH;
        double delta_front_left_position = (front_left_position - previous_front_left_position)/ TICKS_PER_INCH;

        double delta_back_right_position = (back_right_position - previous_back_right_position) / TICKS_PER_INCH;
        double delta_back_left_position = (back_left_position - previous_back_left_position) / TICKS_PER_INCH;

        // Logic:
        previous_front_right_position = front_right_position;
        previous_front_left_position = front_left_position;

        previous_back_right_position = back_right_position;
        previous_back_left_position = back_left_position;

        // Variables (Assignment):
        double delta_x_position = (
            delta_front_left_position + delta_front_right_position +
            delta_back_left_position + delta_back_right_position
        ) / 4.0;

        double delta_y_position = (
            -delta_front_left_position + delta_front_right_position +
            delta_back_left_position - delta_back_right_position
        ) / 4.0;

        double delta_heading = normalize_angle(imu.get_heading_yaw() - heading);

        // Variables (Assignment):
        double adjusted_x_position = (
            delta_x_position * Math.cos(heading) -
            delta_y_position * Math.sin(heading)
        );

        double adjusted_y_position = (
            delta_x_position * Math.sin(heading) +
            delta_y_position * Math.cos(heading)
        );

        // Logic:
        position_x += adjusted_x_position;
        position_y += adjusted_y_position;

        heading += delta_heading;
    }

    /**
     * Resets all odometry values to zero.
     *
     * @param imu The inertial measurement unit of the mecanum drive.
     */
    public void reset_odometry(IMUEx imu) {
        // Logic:
        position_x = 0;
        position_y = 0;

        heading = 0;

        previous_front_right_position = 0;
        previous_front_left_position = 0;

        previous_back_right_position = 0;
        previous_back_left_position = 0;

        imu.reset();
    }

    /**
     * Updates the singleton instance of the odometry class.
     *
     * @return The singleton instance of the odometry class.
     */
    public static Odometry getInstance() {
        if (singleton_instance == null) {
            singleton_instance = new Odometry();
        }

        return singleton_instance;
    }
}
