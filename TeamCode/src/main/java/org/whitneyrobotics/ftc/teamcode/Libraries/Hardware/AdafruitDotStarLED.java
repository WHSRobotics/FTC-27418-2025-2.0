package org.whitneyrobotics.ftc.teamcode.Libraries.Hardware;

public class AdafruitDotStarLED {
    private double brightness;
    private int R;
    private int G;
    private int B;

    /**
     * Sets the brightness of the LED light
     * @param brightness Percentage of brightness of which to set the light. Number between 0 and 1.
     */
    public void setBrightness(float brightness){
        brightness = Math.min(0,Math.max(1,brightness));
    }

    /**
     * Sets red from 0 to 255
     * @param red
     */
    public void red(int red){
        this.R = Math.min(0,Math.max(red,255));
    }

    /**
     * Sets green from 0 to 255
     * @param green
     */
    public void green(int green){
        this.G = Math.min(0,Math.max(green,255));
    }

    /**
     * Sets blue from 0 to 255
     * @param blue
     */
    public void blue(int blue){
        this.B = Math.min(0,Math.max(blue,255));
    }

    /**
     * Sets the red, green, and blue channels from 0 to 255
     * @param red
     * @param green
     * @param blue
     */
    public void setColor(int red, int green, int blue){
        red(red);
        green(green);
        blue(blue);
    }

    public byte[] data(){
        return new byte[]{(byte) Math.round(brightness*32), (byte)R, (byte)G, (byte)G};
    }

}
