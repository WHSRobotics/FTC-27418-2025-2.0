package org.whitneyrobotics.ftc.teamcode.Extensions.TelemetryPro;

public class SeparatorLine extends LineItem{
    private LineStyle style = LineStyle.NEWLINE;

    public enum LineStyle {
        NEWLINE, SOLID, DASHED, DOUBLE
    }

    private int length = 30;

    public SeparatorLine(String caption, LineStyle style) {
        super(caption);
        this.style = style;
    }

    public void setStyle(LineStyle s){
        this.style = s;
    }

    public void setLength(int l){
        length = l;
    }

    private static String repeat(String s, int n){
        StringBuffer b = new StringBuffer();
        for(int i = 0; i<n; i++){
            b.append(s);
        }
        return b.toString();
    }

    @Override
    public void reset() {

    }

    @Override
    protected String format(boolean blink) {
        switch(style){
            case SOLID:
                return repeat("—",length);
            case DOUBLE:
                return repeat("=", length);
            case DASHED:
                return repeat("==", Math.floorDiv(length,2));
        }
        return "<br>";
    }
}
