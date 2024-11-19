package org.whitneyrobotics.ftc.teamcode.Extensions.TelemetryPro;

import android.os.Build;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.N)
public class TextLine extends LineItem{
    public TextLine(String text, boolean persistent, Color color, RichTextFormat... richTextFormats){

        super(TelemetryPro.replaceNewLineWithLineBreaks(text),persistent,color, richTextFormats);
    }

    public void setText(String text){
        this.caption = text;
    }

    @Override
    public void reset() {

    }

    @Override
    protected String format(boolean blink) {
        return caption;
    }
}
