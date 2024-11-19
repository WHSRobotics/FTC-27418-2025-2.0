package org.whitneyrobotics.ftc.teamcode.Libraries.Controllers;


/**
 * Create a PID Coefficients with default constants. Ideal for testing constants using the getters and setters.
 * @see #setKP(double)
 * @see #setKI(double)
 * @see #setKD(double)
 * @see #setKF(double)
 * @see #setF(FeedForwardProvider)
 */
public class PIDFCoefficientsNew {
    public double kP = 1;
    public double getKP() { return kP; }
    public PIDFCoefficientsNew setKP(double kP) {
        this.kP = kP;
        return this;
    }

    public double kI = 0;
    public double getKI() { return kI; }
    public PIDFCoefficientsNew setKI(double kI) {
        this.kI = kI;
        return this;
    }

    public double kD = 0;
    public double getKD() { return kD; }
    public PIDFCoefficientsNew setKD(double kD) {
        this.kD = kD;
        return this;
    }

    private double kF = 1;
    public double getKF() { return kF; }
    public PIDFCoefficientsNew setKF(double kF) {
        this.kF = kF;
        return this;
    }

    private FeedForwardProvider F = (double t, double c, long time) -> 0;

    public FeedForwardProvider getF() {
        return F;
    }

    public PIDFCoefficientsNew setF(FeedForwardProvider f) {
        this.F = f;
        return this;
    }

    @FunctionalInterface
    public interface FeedForwardProvider {
        double apply(double target, double current, long elapsedTime);
    }
}
