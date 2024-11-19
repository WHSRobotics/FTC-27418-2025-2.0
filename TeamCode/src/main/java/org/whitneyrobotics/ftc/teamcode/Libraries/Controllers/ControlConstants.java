package org.whitneyrobotics.ftc.teamcode.Libraries.Controllers;

public class ControlConstants {
   //Feedback
    public double kP;
    public double kI;
    public double kD;

    // Velocity + Acceleration
    public double kV;
    public double kA;

    //feedforward
    @FunctionalInterface
    public interface FeedforwardFunction{
        double invoke(double currentPosition, double currentVelocity);
    }
    public FeedforwardFunction kF;

    public ControlConstants(double kP, double kI, double kD, FeedforwardFunction kF) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.kF = kF;
    }

    public ControlConstants(double kP, double kI, double kD) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
    }

    public ControlConstants(double kP, double kI, double kD, double kV, double kA) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.kV = kV;
        this.kA = kA;
    }



    public double getkP() {
        return kP;
    }

    public void setkP(double kP) {
        this.kP = kP;
    }

    public double getkI() {
        return kI;
    }

    public void setkI(double kI) {
        this.kI = kI;
    }

    public double getkD() {
        return kD;
    }

    public void setkD(double kD) {
        this.kD = kD;
    }

    public double getkV() {
        return kV;
    }

    public void setkV(double kV) {
        this.kV = kV;
    }

    public double getkA() {
        return kA;
    }

    public void setkA(double kA) {
        this.kA = kA;
    }

    public FeedforwardFunction getkF() {
        return kF;
    }

    public void setkF(FeedforwardFunction kF) {
        this.kF = kF;
    }
}
