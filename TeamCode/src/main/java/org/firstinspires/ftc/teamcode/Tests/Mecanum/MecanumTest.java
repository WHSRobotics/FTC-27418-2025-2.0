// Written by: Christopher Gholmieh
// Package:
package org.firstinspires.ftc.teamcode.Tests.Mecanum;

// Imports:
import org.firstinspires.ftc.teamcode.Extensions.OpModeEx.OpModeEx;
import org.firstinspires.ftc.teamcode.Subsystems.Mecanum.Mecanum;
import org.firstinspires.ftc.teamcode.Constants.Channel.Channel;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


// Class:
@TeleOp(name = "27418-mecanum-test", group = "27418")
public class MecanumTest extends OpModeEx {
    // Variables (Declaration):
    public Channel channel;
    public Mecanum mecanum;

    // Methods:
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

    // Initialization:
    @Override
    public void initInternal() {
        // Variables (Definition):
        // Channel:
        channel = new Channel(gamepad1, gamepad2);

        // Mecanum:
        mecanum = new Mecanum(hardwareMap);
    }

    // Loop:
    @Override
    protected void loopInternal() {
        // Channel:
        channel.update(gamepad1, gamepad2);

        // Telemetry:
        record_telemetry(channel);

        // Mecanum:
        mecanum.update(channel);
    }
}