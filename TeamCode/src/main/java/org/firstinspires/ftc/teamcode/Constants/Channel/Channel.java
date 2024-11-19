// Written by: Christopher Gholmieh
// Package:
package org.firstinspires.ftc.teamcode.Constants.Channel;

// Imports:
import org.firstinspires.ftc.teamcode.Extensions.GamepadEx.GamepadEx;


// Class:
public class Channel {
    // Variables (Declaration):
    // Gamepad (One):
    public double gamepad_one_left_stick_x;
    public double gamepad_one_left_stick_y;

    public double gamepad_one_right_stick_x;
    public double gamepad_one_right_stick_y;

    public boolean gamepad_one_options;

    // Gamepad (Two):
    public double gamepad_two_left_stick_x;
    public double gamepad_two_left_stick_y;

    public double gamepad_two_right_stick_x;
    public double gamepad_two_right_stick_y;

    public boolean gamepad_two_options;

    // Channel:
    private static Channel singleton_instance;

    // Constructor:
    public Channel(GamepadEx gamepad_one, GamepadEx gamepad_two) {
        // Gamepad (One):
        this.gamepad_one_left_stick_x = gamepad_one.LEFT_STICK_X.value();
        this.gamepad_one_left_stick_y = gamepad_one.LEFT_STICK_Y.value();

        this.gamepad_one_right_stick_x = gamepad_one.RIGHT_STICK_X.value();
        this.gamepad_one_right_stick_y = gamepad_one.RIGHT_STICK_Y.value();

        this.gamepad_one_options = gamepad_one.SELECT.value();

        // Gamepad (Two):
        this.gamepad_two_left_stick_x = gamepad_two.LEFT_STICK_X.value();
        this.gamepad_two_left_stick_y = gamepad_two.LEFT_STICK_Y.value();

        this.gamepad_two_right_stick_x = gamepad_two.RIGHT_STICK_X.value();
        this.gamepad_two_right_stick_y = gamepad_two.RIGHT_STICK_Y.value();

        this.gamepad_two_options = gamepad_two.SELECT.value();
    }

    // Methods:
    public static Channel getInstance(GamepadEx gamepad_one, GamepadEx gamepad_two) {
        if (singleton_instance == null) {
            singleton_instance = new Channel(gamepad_one, gamepad_two);
        }

        return singleton_instance;
    }

    public void update(GamepadEx gamepad_one, GamepadEx gamepad_two) {
        // Gamepad (One):
        this.gamepad_one_left_stick_x = gamepad_one.LEFT_STICK_X.value();
        this.gamepad_one_left_stick_y = gamepad_one.LEFT_STICK_Y.value();

        this.gamepad_one_right_stick_x = gamepad_one.RIGHT_STICK_X.value();
        this.gamepad_one_right_stick_y = gamepad_one.RIGHT_STICK_Y.value();

        this.gamepad_one_options = gamepad_one.SELECT.value();

        // Gamepad (Two):
        this.gamepad_two_left_stick_x = gamepad_two.LEFT_STICK_X.value();
        this.gamepad_two_left_stick_y = gamepad_two.LEFT_STICK_Y.value();

        this.gamepad_two_right_stick_x = gamepad_two.RIGHT_STICK_X.value();
        this.gamepad_two_right_stick_y = gamepad_two.RIGHT_STICK_Y.value();

        this.gamepad_two_options = gamepad_two.SELECT.value();
    }
}