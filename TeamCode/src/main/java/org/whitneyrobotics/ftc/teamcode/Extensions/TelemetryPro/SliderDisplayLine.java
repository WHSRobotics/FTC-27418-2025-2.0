package org.whitneyrobotics.ftc.teamcode.Extensions.TelemetryPro;

import android.os.Build;

import androidx.annotation.RequiresApi;

import org.firstinspires.ftc.robotcore.external.Func;
import org.whitneyrobotics.ftc.teamcode.Libraries.Utilities.Functions;

public class SliderDisplayLine extends ProgressBarLine{

    protected double min = 0.0d;

    public SliderDisplayLine(String caption) {
        super(caption);
        super.numChars = 40;
    }

    public SliderDisplayLine(String caption, Color c) {
        super(caption, c);
        super.numChars = 40;
    }

    public SliderDisplayLine(String caption, Func<Double> valueProvider, Color c) {
        super(caption, valueProvider, c);
        super.numChars = 40;
    }

    public SliderDisplayLine setMin(double min){
        this.min = min;
        return this;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected String format(boolean blink) {
        if(valueProvider != null) progress=valueProvider.value();
        double progress = Functions.map(this.progress, min, scale * (min < 0 ? 2 : 1), 0,1);
        int numCompletedChars = (int)Math.floor(progress * numChars);
        return String.format("%s: %s <br> <font color=\"#FFFFFF\"><strong>|%s<font color=\"%s\">%s</font>%s</strong>|</font>",
                caption,
                this.progress,
                repeat('-', numCompletedChars-1),
                this.color.getHexCode(),
                completed,
                repeat('-',numChars-numCompletedChars-1)
                );
    }
}
