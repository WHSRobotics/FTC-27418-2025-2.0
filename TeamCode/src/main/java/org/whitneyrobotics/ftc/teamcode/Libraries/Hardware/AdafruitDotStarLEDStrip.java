package org.whitneyrobotics.ftc.teamcode.Libraries.Hardware;

public class AdafruitDotStarLEDStrip {
    public static class ColorTuple {
        public double red;
        public double green;
        public double blue;
        public int brightness;
        public ColorTuple(int red, int green, int blue, double brightness){
            this.red = red;
            this.green = green;
            this.blue = blue;
            this.brightness = (int)Math.floor(brightness*32) ^ 0b11100000;
        }
    }

    private int length;
    private AdafruitDotStarLED[] leds;
    //make constructor with int parameter length
    public AdafruitDotStarLEDStrip(int length){
        this.length = length;
        leds = new AdafruitDotStarLED[length];
    }


}
