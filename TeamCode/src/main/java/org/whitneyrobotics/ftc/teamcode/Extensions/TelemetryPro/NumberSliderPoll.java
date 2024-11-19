package org.whitneyrobotics.ftc.teamcode.Extensions.TelemetryPro;

import android.os.Build;

import androidx.annotation.RequiresApi;

import org.whitneyrobotics.ftc.teamcode.Extensions.GamepadEx.GamepadEx;

import java.util.function.Consumer;

@RequiresApi(api = Build.VERSION_CODES.N)
public class NumberSliderPoll extends Interactable{
    private double min = 0d;
    private double max = 100.0d;
    private double current = 0.0d;
    private boolean wrap = false;
    private double smallStep = 1.0d;
    private double largeStep = 5.0d;
    private GamepadEx gp;

    private boolean focused;
    private Consumer<Double> valueConsumer = v -> {};

    /**
     * Use {@link NumberSliderPollBuilder}
     * @param caption
     * @param color
     * @param richTextFormats
     */
    private NumberSliderPoll(String caption, Color color, RichTextFormat... richTextFormats) {
        super(caption, color, richTextFormats);
    }

    private void wrapLower(){
        if(current < min){
            current = min;
        }
    }

    private void wrapHigher(){
        if(current > max){
            current = max;
        }
    }

    @Override
    public void connectGamepad(GamepadEx gamepad) {
        gp = gamepad;
        gp.DPAD_LEFT.onPress(e -> {
            if ((current == min) && wrap){
                current = max;
                return;
            }
            current -= smallStep;
            wrapLower();
            valueConsumer.accept(current);
        });
        gp.DPAD_RIGHT.onPress(e -> {
            if ((current == max) && wrap){
                current = min;
                return;
            }
            current += smallStep;
            wrapHigher();
            valueConsumer.accept(current);
        });
        gp.SQUARE.onPress(e -> {
            if ((current == min) && wrap){
                current = max;
                return;
            }
            current -= largeStep;
            wrapLower();
            valueConsumer.accept(current);
        });
        gp.CIRCLE.onPress(e -> {
            if ((current == max) && wrap){
                current = min;
                return;
            }
            current += largeStep;
            wrapHigher();
            valueConsumer.accept(current);
        });
    }

    @Override
    public void focus() {
        focused = true;
        setRichTextFormat(RichTextFormat.HIGHLIGHT);
    }

    @Override
    public void disconnect() {
        this.rtfFormats.remove(RichTextFormat.HIGHLIGHT);
        focused = false;
        gp.DPAD_LEFT.removePressHandler();
        gp.DPAD_RIGHT.removePressHandler();
        gp.SQUARE.removePressHandler();
        gp.CIRCLE.removePressHandler();
        gp = null;
    }

    public void onChange(Consumer<Double> valueConsumer) {
        this.valueConsumer = valueConsumer;
    }

    @Override
    public void reset() {
        current = 0;
    }

    @Override
    protected String format(boolean blink) {
        return caption + ": " + current;
    }

    public static class NumberSliderPollBuilder {
        private NumberSliderPoll instance;

        public NumberSliderPollBuilder(String caption) {
            instance = new NumberSliderPoll(caption, Color.WHITE);
        }

        public NumberSliderPollBuilder(String caption, Color color, RichTextFormat... richTextFormats) {
            instance = new NumberSliderPoll(caption, color, richTextFormats);
        }

        public NumberSliderPollBuilder setMin(double min) {
            instance.min = min;
            return this;
        }

        public NumberSliderPollBuilder setMax(double max) {
            instance.max = max;
            return this;
        }

        public NumberSliderPollBuilder setInitial(double initial) {
            instance.current = initial;
            return this;
        }

        public NumberSliderPollBuilder allowWrap(boolean allow) {
            instance.wrap = allow;
            return this;
        }

        public NumberSliderPollBuilder setSmallStep(double smallStep) {
            instance.smallStep = smallStep;
            return this;
        }

        public NumberSliderPollBuilder setLargeStep(double largeStep) {
            instance.largeStep = largeStep;
            return this;
        }

        public NumberSliderPoll build() {
            return instance;
        }
    }
}
