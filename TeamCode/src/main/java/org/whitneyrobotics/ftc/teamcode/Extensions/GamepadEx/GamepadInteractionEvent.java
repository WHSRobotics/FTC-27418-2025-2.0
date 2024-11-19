package org.whitneyrobotics.ftc.teamcode.Extensions.GamepadEx;

public class GamepadInteractionEvent {
    public final Boolean boolVal;
    public final Float floatVal;
    public final Long lastInteraction;
    public final Integer consecutivePresses;

    public GamepadInteractionEvent(Boolean boolVal, Float floatVal, Long lastInteraction, Integer consecutivePresses) {
        this.boolVal = boolVal;
        this.floatVal = floatVal;
        this.lastInteraction = lastInteraction;
        this.consecutivePresses = consecutivePresses;
    }
}
