// Refactored by: Christopher Gholmieh
// Package:
package org.firstinspires.ftc.teamcode.Extensions.IMUEx;

// Imports:
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;


// Class:
public class IMUEx {
    // Variables (Declaration):
    private double calibration_radians = 0;
    private double heading;

    public IMU imu;

    // Constructor:
    /**
     *
     * @param hardware_map The map that links hardware to software.
     * @param hardware_identifier The identifier used in the configuration.
     * @param imu_parameters The parameters of the IMU.
     */
    public IMUEx(
        // Hardware:
        HardwareMap hardware_map,

        // Identifier:
        String hardware_identifier,

        // Parameters:
        RevHubOrientationOnRobot imu_parameters
    ) {
        // Variables (Definition):
        imu = hardware_map.get(IMU.class, hardware_identifier);

        // Variables (Assignment):
        IMU.Parameters parameters = new IMU.Parameters(imu_parameters);

        // Initialization:
        imu.initialize(parameters);
    }

    // Methods:
    public void zero_calibration_offset(double offset_radians) {
        calibration_radians = imu.getRobotOrientation(
            // Reference:
            AxesReference.INTRINSIC,

            // Order:
            AxesOrder.XYZ,

            // Units:
            AngleUnit.RADIANS
        ).firstAngle - offset_radians;
    }

    public double[] get_yaw_pitch_roll() {
        // Variables (Assignment):
        YawPitchRollAngles angles = imu.getRobotYawPitchRollAngles();

        return new double[] {
            // Yaw:
            angles.getYaw(AngleUnit.RADIANS),

            // Pitch:
            angles.getPitch(AngleUnit.RADIANS),

            // Roll:
            angles.getRoll(AngleUnit.RADIANS)
        };
    }

    public double get_heading_yaw() {
        return (
            imu.getRobotYawPitchRollAngles()
                .getYaw(AngleUnit.RADIANS)
            - calibration_radians
        );
    }

    public void reset() {
        imu.resetYaw();
    }
}