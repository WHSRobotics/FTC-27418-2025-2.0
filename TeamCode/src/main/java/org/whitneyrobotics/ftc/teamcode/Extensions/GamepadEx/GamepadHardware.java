package org.whitneyrobotics.ftc.teamcode.Extensions.GamepadEx;

import java.util.function.Consumer;

public interface GamepadHardware {
    Consumer<GamepadInteractionEvent> defaultConsumer = event -> {};

    void onInteraction(Consumer<GamepadInteractionEvent> callback);
    void update(Object newState);
    void disconnectAllHandlers();
}
