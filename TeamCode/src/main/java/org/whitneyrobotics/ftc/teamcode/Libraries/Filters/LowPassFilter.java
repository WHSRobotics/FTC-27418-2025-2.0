package org.whitneyrobotics.ftc.teamcode.Libraries.Filters;

/**
 * Written by Perry on 10/16/22 <br>
 * Implementation based on <a href="http://phrogz.net/js/framerate-independent-low-pass-filter.html">http://phrogz.net/js/framerate-independent-low-pass-filter.html</a>
 */
public class LowPassFilter implements Filter{
    private double output;
    private double smoothing;
    private long lastUpdated;
    private boolean firstCall = true;

    /**
     * Constructs a new low pass filter with initial value and smoothing
     * @param initial Initial value
     * @param smoothing Smoothing, in units/second
     */
    public LowPassFilter(double initial, double smoothing){
        this.output = initial;
        this.smoothing = smoothing;
    }

    public void setSmoothing(double smoothing){this.smoothing = smoothing;}

    protected void init(){
        firstCall = true;
    }

    /**
     * Calculates the next pulse of the low pass filter
     * @param newState The new measurement
     */
    @Override
    public void calculate(double newState) {
        if(firstCall){
            lastUpdated = System.currentTimeMillis();
            firstCall = false;
        }
        long now = System.currentTimeMillis();
        long elapsedTime = now - lastUpdated;
        output +=  (elapsedTime/1E4) * (newState-output) / smoothing;
        lastUpdated = now;
    }

    @Override
    public double getOutput() {
        return output;
    }
}
