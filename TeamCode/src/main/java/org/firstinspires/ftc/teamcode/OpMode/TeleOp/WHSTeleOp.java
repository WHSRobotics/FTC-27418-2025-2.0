// Written by: Christopher Gholmieh
// Package:
package org.firstinspires.ftc.teamcode.OpMode.TeleOp;

// Imports:
import org.firstinspires.ftc.teamcode.Extensions.OpModeEx.OpModeEx;
import org.firstinspires.ftc.teamcode.Subsystems.Implementation;
import org.firstinspires.ftc.teamcode.Constants.Channel.Channel;


// Class:
public class WHSTeleOp extends OpModeEx  {
    // Variables (Declaration):
    // Implementation:
    public Implementation implementation;

    // Channel:
    public Channel channel;

    // Initialization:
    /**
     * 
     * @param channel The channel class that contains the data that will be recorded.
     */
    public void record_telemetry(Channel channel) {
        // Telemetry:
        // Gamepad (One):
        telemetryPro.addData("gamepad-one-left-stick-x", channel.gamepad_one_left_stick_x);
        telemetryPro.addData("gamepad-one-left-stick-y", channel.gamepad_one_left_stick_y);

        telemetryPro.addData("gamepad-one-right-stick-x", channel.gamepad_one_right_stick_x);
        telemetryPro.addData("gamepad-one-right-stick-y", channel.gamepad_two_right_stick_y);

        telemetryPro.addData("gamepad-one-options", channel.gamepad_one_options);

        // Gamepad (Two):
        telemetryPro.addData("gamepad-two-left-stick-x", channel.gamepad_two_left_stick_x);
        telemetryPro.addData("gamepad-two-left-stick-y", channel.gamepad_one_left_stick_y);

        telemetryPro.addData("gamepad-two-right-stick-x", channel.gamepad_two_right_stick_x);
        telemetryPro.addData("gamepad-two-right-stick-y", channel.gamepad_two_right_stick_y);

        telemetryPro.addData("gamepad-two-options", channel.gamepad_two_options);

        // Logic:
        telemetryPro.update();
    }

    @Override
    public void initInternal() {
        // Variables (Assignment):
        // Implementation:
        implementation = new Implementation(hardwareMap);

        // Channel:
        channel = new Channel(gamepad1, gamepad2);
    }

    // Loop:
    @Override
    public void loopInternal() {
        // Channel:
        channel.update(gamepad1, gamepad2);

        // Telemetry:
        record_telemetry(channel);

        // Implementation:
        implementation.update(channel);
    }
}