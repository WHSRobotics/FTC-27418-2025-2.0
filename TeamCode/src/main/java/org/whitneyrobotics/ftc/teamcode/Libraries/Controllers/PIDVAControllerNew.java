package org.whitneyrobotics.ftc.teamcode.Libraries.Controllers;

public class PIDVAControllerNew {

    public String phase = "IDLE";
    public PIDVACoefficients coefficients;

    //private PIDCoefficientsNew.FeedForwardProvider FeedforwardCalculation = (double t, double c, long time) -> 0;
    /*public void setFeedforwardCalculation(PIDCoefficientsNew.FeedForwardProvider f) {
        this.FeedforwardCalculation = f;
    }*/

    /**
     * Create a PID Controller with given constant values.
     * @param kP
     * @param kI
     * @param kD
     * @param kV
     * @param kA
     */
    public PIDVAControllerNew(double kP, double kI, double kD, double kV, double kA, double maxVelocity, double maxAcceleration){
        this(kP, kI, kD, kV, kA, 0, maxVelocity, maxAcceleration);
    }

    public PIDVAControllerNew(PIDVACoefficients coeff){
        this.coefficients = coeff;
    }

    public PIDVAControllerNew(double kP, double kI, double kD, double kV, double kA, double kStatic, double maxVelocity, double maxAcceleration){
        coefficients = new PIDVACoefficients()
                .setKP(kP)
                .setKI(kI)
                .setKD(kD)
                .setKV(kV)
                .setKA(kA)
                .setKStatic(kStatic)
                .setMaxVelocity(maxVelocity)
                .setMaxAcceleration(maxAcceleration);
    }


    private double lastKnownTime = 0;
    private double lastKnownError = 0;
    private double lastKnownInput = 0;

    private double target = 0;

    public PIDVAControllerNew(PIDVACoefficients lSlidesCoeffs, int i, int i1, int i2, int i3) {
    }

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
     */
    public void reset() {
        lastKnownTime = System.nanoTime() / 1E9;
        lastKnownError = target - lastKnownInput;
        velocity = 0;
        outputAcceleration = 0;
        integral = 0;
    }

    public void setTarget(double target){
        this.target = target;
    }

    /**
     * Calculate function for a changing target.
     * @param target Current target
     * @param current Current state of the system
     * @see #calculate(double)
     */
    //public MotionProfileTrapezoidal motionProfile;
    public void calculate(double target, double current){
        setTarget(target);
        calculate(current);
    }

    public double velocity = 0;
    public double outputAcceleration = 0;

    /*
    private Supplier<Double> velocityProvider = () -> 0.0d;
    public void setVelocityProvider(Supplier<Double> velocityProvider){
        this.velocityProvider = velocityProvider;
    }
     */

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

        int direction = 1;
        //direction
        if (error < 0){
            direction = -1;
        }
        double outputVelocity;

        if (Math.abs(velocity) < coefficients.getMaxVelocity()){
            outputVelocity = velocity + direction * coefficients.getMaxAcceleration() * deltaTime;
            outputAcceleration = direction * coefficients.getMaxAcceleration();
            phase = "RAMP UP";
        } else {
            outputVelocity = direction* coefficients.getMaxVelocity();
            outputAcceleration = 0;
            phase = "CRUISE";
        }

        if(Math.abs(error) <= Math.pow(velocity,2)/(2* coefficients.getMaxAcceleration())){
            outputVelocity = velocity - direction * coefficients.getMaxAcceleration() * deltaTime;
            outputAcceleration = -direction * coefficients.getMaxAcceleration();
            phase = "RAMP DOWN";
        }
        velocity = outputVelocity;
    }

    /**
     * Retrieves the value of the PID Controller with adjustments from coefficients.
     * @return Value of the PID controller. It's recommended to pass this to a scaling or clamping function before applying it to the system.
     */
    public double getOutput(){
        return (coefficients.getKP() * lastKnownError) +
                (coefficients.getKI() * integral) +
                (coefficients.getKD() * derivative) +
                (coefficients.getKV() * velocity) +
                (coefficients.getKA() * outputAcceleration) +
                coefficients.getkStatic();
    }
}
