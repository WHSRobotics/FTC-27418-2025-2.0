package org.whitneyrobotics.ftc.teamcode.Extensions.TelemetryPro;

import java.util.function.Supplier;

public class TextLineLambda extends TextLine {
    private Supplier<String> supplierFunc = () -> "";
    public TextLineLambda(Supplier<String> text, Color color, RichTextFormat... richTextFormats) {
        super(text.get(), true, color, richTextFormats);
    }

    @Override
    protected String format(boolean blink) {
        return supplierFunc.get();
    }
}
