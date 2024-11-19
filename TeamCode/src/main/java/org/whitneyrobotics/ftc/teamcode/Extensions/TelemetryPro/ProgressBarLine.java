package org.whitneyrobotics.ftc.teamcode.Extensions.TelemetryPro;

import org.firstinspires.ftc.robotcore.external.Func;
import org.whitneyrobotics.ftc.teamcode.Libraries.Utilities.Functions;

import java.util.function.Consumer;

public class ProgressBarLine extends LineItem {

    protected double progress = 0.0d;
    /**
     * Scale to set completion as. For instance, 1 would scale the set the progression bar to be from 0-1,
     * while 5 would scale it to be from 0-5, where 2.5 is 50%.
     * */
    protected int numChars = 30;
    protected double scale = 1.0d;
    protected static char completed = '█';
    protected static char empty = '▒'; //▒

    protected Func<Double> valueProvider;

    public void setValueProvider(Func<Double> valueProvider){
        this.valueProvider = valueProvider;
    }

    /**
     * useValueUpdater is a experimental function designed after React Hooks to allow you to "hook" into a function that updates the state of the progress bar instance when it's called.
     * @return A {@link Consumer} lambda that updates the progress.
     * @see #useValueUpdater(ProgressBarLine)
     */
    public Consumer<Double> useValueUpdater(){
        return (Double d) -> {
            synchronized (d){
                progress = d;
            }
        };
    }

    /**
     * useValueUpdater is a experimental function designed after React Hooks to allow you to "hook" into a function that updates the state of the progress bar instance when it's called.
     * This implementation is a class-wide implementation that allows you to target a specific instance and hook into a function to update its value.
     * @param progressBar The instance of the Progress Bar you want to update.
     * @return A {@link Consumer} lambda that updates the progress.
     * @see #useValueUpdater()
     */
    public static Consumer<Double> useValueUpdater(ProgressBarLine progressBar){
        return (Double d) -> {
            synchronized (d){
                progressBar.progress = d;
            }
        };
    }

    public ProgressBarLine(String caption){
        this(caption, null, Color.WHITE);
    }

    public ProgressBarLine(String caption, Color c){
        this(caption, null, c);
    }

    public ProgressBarLine(String caption, Func<Double> valueProvider, Color c){
        super(caption,true, c);
        this.valueProvider = valueProvider;
    }

    public ProgressBarLine setScale(double scale) {
        this.scale = scale;
        return this;
    }

    public ProgressBarLine setNumChars(int numChars){
        this.numChars = numChars;
        return this;
    }

    public static String repeat(char input, int num){
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i<num; i++){
            buffer.append(input);
        }
        return buffer.toString();
    }

    @Override
    public void reset() {
        progress = 0.0d;
    }

    @Override
    protected String format(boolean blink) {
        if(valueProvider != null) progress=valueProvider.value();
        double progress = Functions.map(this.progress, 0, scale, 0,1);
        int numCompletedChars = (int)Math.floor(progress * numChars);
        return String.format("%s: %s%% <br> <strong>%s</strong>",caption, Math.floor(progress*100), repeat(completed, numCompletedChars) + "<font color=\"#FFFFFF\">" + repeat(empty, numChars-numCompletedChars) + "</font>");
    }
}
