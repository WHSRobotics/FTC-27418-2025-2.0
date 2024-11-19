package org.whitneyrobotics.ftc.teamcode.Extensions.TelemetryPro;

import org.whitneyrobotics.ftc.teamcode.Extensions.GamepadEx.GamepadEx;

public abstract class Interactable extends LineItem {
    private GamepadEx gamepad;

    public Interactable(String caption) {
        super(caption);
    }

    public Interactable(String caption, Color color, RichTextFormat... richTextFormats){
        super(caption, true, color, richTextFormats);
    }
    public abstract void connectGamepad(GamepadEx gamepad);

    public abstract void focus();

    public abstract void disconnect();
}
