package org.whitneyrobotics.ftc.teamcode.Libraries.Controllers;

public class PIDVACoefficients {
    public double kP = 1;
    public double getKP() { return kP; }
    public PIDVACoefficients setKP(double kP) {
        this.kP = kP;
        return this;
    }

    public double kI = 0;
    public double getKI() { return kI; }
    public PIDVACoefficients setKI(double kI) {
        this.kI = kI;
        return this;
    }

    public double kD = 0;
    public double getKD() { return kD; }
    public PIDVACoefficients setKD(double kD) {
        this.kD = kD;
        return this;
    }

    public double kA = 0;
    public double getKA() { return kA; }
    public PIDVACoefficients setKA(double kA) {
        this.kA = kA;
        return this;
    }

    public double kV = 0;
    public double getKV() { return kV; }
    public PIDVACoefficients setKV(double kV) {
        this.kV = kV;
        return this;
    }

    public double kStatic = 0;
    public double getkStatic() {return kStatic; }
    public PIDVACoefficients setKStatic(double kStatic) {
        this.kStatic = kStatic;
        return this;
    }

    public double maxVelocity;

    public double getMaxVelocity() {
        return maxVelocity;
    }

    public PIDVACoefficients setMaxVelocity(double maxVelocity) {
        this.maxVelocity = maxVelocity;
        return this;
    }

    public double getMaxAcceleration() {
        return maxAcceleration;
    }

    public PIDVACoefficients setMaxAcceleration(double maxAcceleration) {
        this.maxAcceleration = maxAcceleration;
        return this;
    }

    public double maxAcceleration;
}
