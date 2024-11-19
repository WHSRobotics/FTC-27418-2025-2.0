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

        // Implementation:
        implementation.update(channel);
    }
}