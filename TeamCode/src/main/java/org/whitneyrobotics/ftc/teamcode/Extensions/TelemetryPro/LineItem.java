package org.whitneyrobotics.ftc.teamcode.Extensions.TelemetryPro;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public abstract class LineItem {
    public enum Color {
        BLACK("000000"),
        SILVER("C0C0C0"),
        GRAY("808080"),
        WHITE("FFFFFF"),
        MAROON("800000"),
        RED("FF0000"),
        PURPLE("800080"),
        FUCHSIA("FF00FF"),
        GREEN("008000"),
        LIME("00FF00"),
        OLIVE("808000"),
        YELLOW("FFFF00"),
        ROBOTICS("FFDD00"),
        NAVY("000080"),
        BLUE("0000FF"),
        TEAL("008080"),
        AQUA("00FFFF");

        private final String hexCode;
        Color(String hexCode){
            this.hexCode = hexCode;
        }
        public String getHexCode(){
            return "#" + hexCode;
        }
    }

    private static int globalIdCounter = 0;
    public final int id;

    protected boolean isVisible = false;

    public enum RichTextFormat {
        BOLD, ITALICS, UNDERLINE, HIGHLIGHT
    }

    private boolean persistent = false;

    public boolean isPersistent() {return persistent;}

    protected Set<RichTextFormat> rtfFormats = new HashSet<RichTextFormat>();

    protected String caption;

    public int getImportance() {
        return importance;
    }

    protected void setImportance(int importance) {
        this.importance = importance;
    }

    private int importance;

    protected LineItem(String caption){
        this.caption = caption;
        this.id = globalIdCounter++;
    }
    protected LineItem(String caption, boolean persistent, Color color, RichTextFormat... rtfFormats){
        this.caption = caption;
        this.persistent = persistent;
        this.color = color;
        this.rtfFormats.addAll(Arrays.asList(rtfFormats));
        this.id = globalIdCounter++;
    }

    public void persistent(){
        persistent = true;
    }

    public void setPersistent(boolean persistent){
        this.persistent = persistent;
    }

    protected Color color = Color.WHITE;

    public void setColor(Color c){
        this.color = c;
    }

    public LineItem setRichTextFormat(RichTextFormat... rtf) {
        this.rtfFormats.addAll(Arrays.asList(rtf));
        return this;
    }

    public void resetRichTextFormats(){
        this.rtfFormats = new HashSet<>();
    }

    public abstract void reset();

    protected abstract String format(boolean blink);

    public String render(boolean blink){
        return String.format(
                ((rtfFormats.contains(RichTextFormat.BOLD) ? "<strong>" : "") + (rtfFormats.contains(RichTextFormat.ITALICS) ? "<em>" : "") + (rtfFormats.contains(RichTextFormat.UNDERLINE) ? "<u>" : "") + (rtfFormats.contains(RichTextFormat.HIGHLIGHT) ? "<span style=\"background-color: #FFFFFF\">" : "") +
                        "<font color=\"%s\">%s</font>" +
                        (rtfFormats.contains(RichTextFormat.HIGHLIGHT) ? "</span>" : "") + (rtfFormats.contains(RichTextFormat.UNDERLINE) ? "</u>" : "") + (rtfFormats.contains(RichTextFormat.ITALICS) ? "</em>" : "") + (rtfFormats.contains(RichTextFormat.BOLD) ? "</strong>" : "")),
                rtfFormats.contains(RichTextFormat.HIGHLIGHT) ? "#000000" : this.color.getHexCode(), format(blink));
    }

    public String renderPartial(String content){
        return String.format(
                    ((rtfFormats.contains(RichTextFormat.BOLD) ? "<strong>" : "") + (rtfFormats.contains(RichTextFormat.ITALICS) ? "<em>" : "") + (rtfFormats.contains(RichTextFormat.UNDERLINE) ? "<u>" : "") + (rtfFormats.contains(RichTextFormat.HIGHLIGHT) ? "<span style=\"background-color: #FFFFFF\">" : "") +
                            "<font color=\"%s\">%s</font>" +
                            (rtfFormats.contains(RichTextFormat.HIGHLIGHT) ? "</span>" : "") + (rtfFormats.contains(RichTextFormat.UNDERLINE) ? "</u>" : "") + (rtfFormats.contains(RichTextFormat.ITALICS) ? "</em>" : "") + (rtfFormats.contains(RichTextFormat.BOLD) ? "</strong>" : "")),
                    rtfFormats.contains(RichTextFormat.HIGHLIGHT) ? "#000000" : this.color.getHexCode(), content);
    }
}
