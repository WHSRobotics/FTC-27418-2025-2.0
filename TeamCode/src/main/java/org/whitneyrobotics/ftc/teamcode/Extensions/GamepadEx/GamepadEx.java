package org.whitneyrobotics.ftc.teamcode.Extensions.GamepadEx;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.function.Consumer;

@RequiresApi(api = Build.VERSION_CODES.N)
public class GamepadEx {
    public enum InteractionType {
        GENERIC, PRESS, RELEASE
    }

    public final Button A = new Button();
    public final Button B = new Button();
    public final Button X = new Button();
    public final Button Y = new Button();

    public final Button CROSS = A;
    public final Button CIRCLE = B;
    public final Button SQUARE = X;
    public final Button TRIANGLE = Y;

    public final Button LEFT_STICK_DOWN = new Button();
    public final Button RIGHT_STICK_DOWN = new Button();

    public final Button DPAD_UP = new Button();
    public final Button DPAD_RIGHT = new Button();
    public final Button DPAD_DOWN = new Button();
    public final Button DPAD_LEFT = new Button();

    public final Button START = new Button();
    public final Button SELECT = new Button();

    public final Button HOME = new Button();
    public final Button PSButton = HOME;

    public final Button BACK = new Button();
    public final Button SHARE = BACK;

    public final Button BUMPER_LEFT = new Button();
    public final Button BUMPER_RIGHT = new Button();

    public final Button TOUCHPAD = new Button();

    @GamepadScalarHardware.LimitSensitivity
    public final GamepadScalarHardware LEFT_STICK_X = new GamepadScalarHardware();
    @GamepadScalarHardware.LimitSensitivity
    public final GamepadScalarHardware LEFT_STICK_Y = new GamepadScalarHardware(true);
    @GamepadScalarHardware.LimitSensitivity
    public final GamepadScalarHardware RIGHT_STICK_X = new GamepadScalarHardware();
    @GamepadScalarHardware.LimitSensitivity
    public final GamepadScalarHardware RIGHT_STICK_Y = new GamepadScalarHardware(true);

    public final GamepadScalarHardware LEFT_TRIGGER = new GamepadScalarHardware();
    public final GamepadScalarHardware RIGHT_TRIGGER = new GamepadScalarHardware();

    public final GamepadHardware[] inputs = {A,B,X,Y,LEFT_STICK_DOWN,DPAD_UP,DPAD_RIGHT,DPAD_DOWN,DPAD_LEFT, START,SELECT,HOME,BUMPER_LEFT,BUMPER_RIGHT, LEFT_STICK_X, LEFT_STICK_Y, RIGHT_STICK_X, RIGHT_STICK_Y, LEFT_TRIGGER, RIGHT_TRIGGER};

    private Gamepad gamepad;

    public final boolean hasError;

    public GamepadEx(Gamepad gamepad){
        this.gamepad = gamepad;
        if(this.gamepad != null) this.gamepad.rumble(250);
        hasError = this.gamepad == null;
    }

    public void addGenericEventListener(GamepadHardware hardware, Consumer<GamepadInteractionEvent> handler){
        hardware.onInteraction(handler);
    }

    public void removeAllEventListeners(){
        for(GamepadHardware hardware : inputs){
            hardware.disconnectAllHandlers();
        }
    }

    public Gamepad getEncapsulatedGamepad(){
        return gamepad;
    }

    public void addButtonEventListener(Button b, InteractionType interactionType, Consumer<GamepadInteractionEvent> handler){
        switch (interactionType){
            case PRESS:
                b.onPress(handler);
                break;
            case RELEASE:
                b.onRelease(handler);
                break;
            default:
                b.onInteraction(handler);
                break;
        }
    }

    public void update(){
        if(gamepad == null) return;
        A.update(gamepad.a);
        B.update(gamepad.b);
        X.update(gamepad.x);
        Y.update(gamepad.y);
        LEFT_STICK_DOWN.update(gamepad.left_stick_button);
        RIGHT_STICK_DOWN.update(gamepad.right_stick_button);
        DPAD_UP.update(gamepad.dpad_up);
        DPAD_RIGHT.update(gamepad.dpad_right);
        DPAD_DOWN.update(gamepad.dpad_down);
        DPAD_LEFT.update(gamepad.dpad_left);
        START.update(gamepad.start);
        SELECT.update(gamepad.options);
        HOME.update(gamepad.ps);
        BUMPER_LEFT.update(gamepad.left_bumper);
        BUMPER_RIGHT.update(gamepad.right_bumper);
        LEFT_STICK_X.update(gamepad.left_stick_x);
        LEFT_STICK_Y.update(gamepad.left_stick_y);
        RIGHT_STICK_X.update(gamepad.right_stick_x);
        RIGHT_STICK_Y.update(gamepad.right_stick_y);
        LEFT_TRIGGER.update(gamepad.left_trigger);
        RIGHT_TRIGGER.update(gamepad.right_trigger);
        BACK.update(gamepad.back);
        TOUCHPAD.update(gamepad.touchpad);
    }

}
