package org.whitneyrobotics.ftc.teamcode.Libraries.Filters;
import org.whitneyrobotics.ftc.teamcode.Libraries.Utilities.Functions;

public class RateLimitingFilter implements Filter {
    private boolean firstCall = true;
    private double lastKnownOutput = 0.0d;
    private double adjustedOutput = 0.0d;
    private long lastKnownTime = 0L;
    private double maxRate = 0.0d;

    public RateLimitingFilter(double maxRate, double initial){
        this.maxRate = maxRate;
        this.adjustedOutput = initial;
    }

    @Override
    public void calculate(double newState) {
        long newTime = System.currentTimeMillis();
        if(firstCall){
            lastKnownTime = newTime;
            lastKnownOutput = adjustedOutput;
            firstCall = false;
        }
        double maxChange = (newTime - lastKnownTime) * maxRate;
        adjustedOutput += Functions.clamp(newState-lastKnownOutput, -maxChange,maxChange);
        lastKnownOutput = adjustedOutput;
        lastKnownTime = newTime;
    }

    @Override
    public double getOutput() {
        return lastKnownOutput;
    }
}
