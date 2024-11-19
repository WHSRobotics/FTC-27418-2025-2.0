package org.whitneyrobotics.ftc.teamcode.Extensions.TelemetryPro;

import org.firstinspires.ftc.robotcore.external.Func;

public class KeyValueLine<T> extends LineItem {
    private Object value;
    private Func valueProducer = null;
    private static String separator = " : ";

    public static void setSeparator(String sep){
        separator = sep;
    }

    public KeyValueLine(String caption, boolean persistent, Object data, Color color, RichTextFormat... richTextFormats){
        super(caption, persistent, color, richTextFormats);
        this.caption = caption;
        this.value = data;
    }

    public KeyValueLine(String caption, boolean persistent, Func<T> valueProducer, Color color, RichTextFormat... richTextFormats){
        super(caption, persistent, color, richTextFormats);
        this.valueProducer = valueProducer;
    }

    public T value(){
        if(valueProducer != null){
            return (T) valueProducer.value();
        }
        return (T) value;
    }

    public String getCaption(){
        return this.caption;
    }

    public void update(Object value){
        if(valueProducer == null) this.value = value;
    }

    @Override
    public void reset() {
        this.value = valueProducer.value();
    }

    @Override
    protected String format(boolean blink) {
        if(valueProducer != null) value = valueProducer.value();
        if(value == null) return "";
        return String.format("%s%s%s",caption,separator,value.toString());
    }
}
