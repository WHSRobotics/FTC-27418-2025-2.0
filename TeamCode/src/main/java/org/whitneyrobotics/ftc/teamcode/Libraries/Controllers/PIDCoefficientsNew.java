package org.whitneyrobotics.ftc.teamcode.Libraries.Controllers;

/**
 * Class for compacting PID Constants.
 * */
public class PIDCoefficientsNew {
    private double kP = 1;
    private double kI = 0;
    private double kD = 0;
    private double kF = 1;
    /**
     * FeedForwardProvider is by default, a lambda that returns 0, but can be modified to return an additional gain to be added.
     */
    private FeedForwardProvider F = (double t, double c, long time) -> 0;

    public void setKP(double kP) { this.kP = kP; }
    public void setKI(double kI) { this.kI = kI; }
    public void setKD(double kD) { this.kD = kD; }
    public void setKF(double kF) { this.kF = kF; }
    public void setF(FeedForwardProvider f) { F = f; }

    public double getKP() { return kP; }
    public double getKI() { return kI; }
    public double getKD() { return kD; }
    public double getKF() { return kF; }
    public FeedForwardProvider getF() { return F; }

    /**
     * Constructor for proportional controller constants
     * @param kP Proportional Gain
     */
    public PIDCoefficientsNew(double kP){
        setKP(kP);
    }
    /**
     * Constructor for a simple PD controller constants
     * @param kP Proportional Gain
     * @param kD Derivative Gain
     */
    public PIDCoefficientsNew(double kP, double kD){
        setKP(kP);
        setKD(kD);
    }

    /**
     * Constructor for feedback-based PID controller constants
     * @param kP Proportional Gain
     * @param kI Integral Gain
     * @param kD Derivative Gain
     */
    public PIDCoefficientsNew(double kP, double kI, double kD){
        setKP(kP);
        setKI(kI);
        setKD(kD);
    }

    /**
     * Constructor for PID controller with feedforward control
     * @param kP Proportional Gain
     * @param kI Integral Gain
     * @param kD Derivative Gain
     * @param kF Feedforward Gain
     * @param F Feedforward Provider Function
     */
    public PIDCoefficientsNew(double kP, double kI, double kD, double kF, FeedForwardProvider F){

    }

    @FunctionalInterface
    public interface FeedForwardProvider {
        double apply(double target, double current, long elapsedTime);
    }
}
