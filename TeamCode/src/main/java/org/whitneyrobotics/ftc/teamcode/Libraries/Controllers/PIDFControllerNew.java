package org.whitneyrobotics.ftc.teamcode.Libraries.Controllers;

public class PIDFControllerNew {
    private PIDFCoefficientsNew coefficients;
    public PIDFControllerNew(){};

    /**
     * Create a PIDF controller with constants bundle. Ideal for production-ready code.
     * @param pidCoefficients
     * @see PIDCoefficientsNew
     */
    public PIDFControllerNew(PIDFCoefficientsNew pidCoefficients){
        coefficients = pidCoefficients;
    }

    /**
     * Create a PID Controller with given constant values.
     * @param kP
     * @param kI
     * @param kD
     */
    public PIDFControllerNew(double kP, double kI, double kD){
        this.coefficients = new PIDFCoefficientsNew()
                .setKP(kP)
                .setKI(kI)
                .setKD(kD);
    }

    /**
     * Create a PIDF Controller with given constant values.
     * @param kP
     * @param kI
     * @param kD
     * @param kF
     * @param F
     */
    public PIDFControllerNew(double kP, double kI, double kD, double kF, PIDFCoefficientsNew.FeedForwardProvider F){
        this.coefficients = new PIDFCoefficientsNew()
                .setKP(kP)
                .setKI(kI)
                .setKD(kD)
                .setKF(kF)
                .setF(F);
    }

    private double lastKnownTime = 0;
    private double lastKnownError = 0;
    private double lastKnownInput = 0;

    public void setTarget(double target) {
        this.target = target;
        //reset();
    }

    public void setTargetWithoutReset(double target){
        this.target = target;
    }

    public double getTarget(){
        return this.target;
    }

    private double target = 0;

    public long getIntegral() {
        return integral;
    }

    public double getDerivative() {
        return derivative;
    }

    private long integral = 0;
    private double derivative = 0;

    /**
     * Resets the last known time, input, error, and integral of the PID controller. This method is internally called when setting a new static target.
     * @see #setTarget(double)
     */
    public void reset() {
        lastKnownTime = System.nanoTime() / (long)1E9;
        lastKnownError = target - lastKnownInput;
        integral = 0;
    }

    /**
     * Calculate function for a changing target.
     * @param target Current target
     * @param current Current state of the system
     * @see #calculate(double)
     */
    public void calculate(double target, double current){
        setTarget(target);
        calculate(current);
    }

    /**
     * Calculates the current error, integral, and derivative of the PID controller and caches results.
     * @param current current state of the system
     * @see #getOutput()
     */
    public void calculate(double current){
        double error = target-current;

        //Integral
        double currentTime = System.nanoTime()/1E9;
        double deltaTime =  (currentTime - lastKnownTime);
        lastKnownTime = currentTime;
        integral += ((0.5 * (lastKnownError + error))*deltaTime); //trapezoidal riemann sum

        //Derivative
        double deltaError = error - lastKnownError;
        derivative = deltaError / deltaTime;
        lastKnownError = error;
        lastKnownInput = current;
    }

    /**
     * Retrieves the value of the PID Controller with adjustments from coefficients.
     * @return Value of the PID controller. It's recommended to pass this to a scaling or clamping function before applying it to the system.
     */
    public double getOutput(){
        return coefficients.getKP() * lastKnownError + coefficients.getKI() * integral + coefficients.getKD() * derivative + coefficients.getKF() * coefficients.getF().apply(target, lastKnownInput, (long)lastKnownTime);
    }
}
