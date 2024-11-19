package org.whitneyrobotics.ftc.teamcode.Libraries.Controllers;

public class PIDController {

    ControlConstants constants;

    private double lastKnownTime = 0;
    private double lastKnownError = 0;

    private double error = 0;
    private double integral = 0;
    private double derivative = 0;

    public PIDController(ControlConstants constants) {
        setConstants(constants);
    }

    public void init(double initialError) {
        lastKnownTime = System.nanoTime() / 1E9;
        lastKnownError = initialError;
        integral = 0;
    }

    public void setConstants(ControlConstants constants) {
        this.constants = constants;
    }


    public void calculate(double error) {
        this.error = error;

        //Integral & Derivative
        double currentTime = System.nanoTime() / 1E9;
        double deltaTime = currentTime - lastKnownTime;
        lastKnownTime = currentTime;

        //Integral
        integral += error * deltaTime;

        //Derivative
        double deltaError = error - lastKnownError;
        derivative = deltaError / deltaTime;
        lastKnownError = error;


    }

    public double getOutput() {
        return constants.getkP() * error + constants.getkI() * integral + constants.getkD() * derivative;

    }

    public double getIntegral(){
        return integral;
    }

    public double getDerivative(){
        return derivative;
    }

}
