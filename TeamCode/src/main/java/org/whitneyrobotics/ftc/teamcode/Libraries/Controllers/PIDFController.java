package org.whitneyrobotics.ftc.teamcode.Libraries.Controllers;

import org.whitneyrobotics.ftc.teamcode.Libraries.Navigation._DEPRECIATED_PurePursuit.motion.MotionProfile;
import org.whitneyrobotics.ftc.teamcode.Libraries.Utilities.Functions;

public class PIDFController {
    ControlConstants constants;

    double lastKnownTime;
    double lastKnownError;
    double integral;
    double derivative;

    double targetVelocity;
    double targetAcceleration;

    double currentPosition;
    double currentVelocity;

    double error;

    MotionProfile motionProfile;

    public PIDFController (MotionProfile motionProfile){
        this.motionProfile = motionProfile;
    }

    public PIDFController(ControlConstants constants){
        setConstants(constants);
    }

    public PIDFController (MotionProfile motionProfile, ControlConstants constants ){
        this.motionProfile = motionProfile;
        setConstants(constants);

    }

    public void setConstants(ControlConstants constants){
        this.constants = constants;
    }

    public void init(double initialError) {
        lastKnownTime = System.nanoTime() / 1E9;
        lastKnownError = initialError;
        integral = 0;
    }

    public void calculate(double error, double currentPosition, double currentVelocity){
       this.currentPosition = currentPosition;
       this.currentVelocity = currentVelocity;
       this.error = error;
       double deltaError = error - lastKnownError;

       double currentTime = System.nanoTime() / 1E9;
       double deltaTime = currentTime - lastKnownTime;
       lastKnownTime = currentTime;

       integral += error * deltaTime;
       derivative = deltaError/deltaTime;
    }

    public double getOutput(){
        double output = constants.getkP() * error
                + constants.getkD() * derivative
                + constants.getkI() * integral
                + constants.getkV() * targetVelocity + constants.getkA() * targetAcceleration
                + constants.getkF().invoke(currentPosition, currentVelocity) * currentVelocity;

        return output;
    }

    public int velocityAtClosestPoint(double position){
        double[] differenceArray = new double[motionProfile.getPoints().length];
        for (int i = 0; i < motionProfile.getPoints().length; i++) {
            differenceArray[i] = motionProfile.getPoints()[i] - position;
        }
        return Functions.calculateIndexOfSmallestValue(differenceArray);
    }

    public double getTargetVelocity() {
        return targetVelocity;
    }

    public void setTargetVelocity(double targetVelocity) {
        this.targetVelocity = targetVelocity;
    }

    public double getTargetAcceleration() {
        return targetAcceleration;
    }

    public void setTargetAcceleration(double targetAcceleration) {
        this.targetAcceleration = targetAcceleration;
    }


}
