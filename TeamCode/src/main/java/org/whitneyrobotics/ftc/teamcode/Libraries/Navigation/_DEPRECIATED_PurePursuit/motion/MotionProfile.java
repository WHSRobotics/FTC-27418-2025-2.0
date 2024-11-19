package org.whitneyrobotics.ftc.teamcode.Libraries.Navigation._DEPRECIATED_PurePursuit.motion;
import org.whitneyrobotics.ftc.teamcode.Libraries.Utilities.Functions;

public class MotionProfile {
    int numOfPoints;
    double[] pointArray;
    double MAXIMUM_ACCELERATION = 30;
    double MAXIMUM_VELOCITY = 700;
    double increment;
    double[] targetVelocities;
    double initalPos;

    public MotionProfile(double initialPos, double finalPos,int numOfPoints){
        this.numOfPoints = numOfPoints;
        increment = (finalPos - initialPos)/ this.numOfPoints;
        pointArray = getPoints(); 
        targetVelocities = getTargetVelocities();
        this.initalPos = initialPos;
    }


    public double[] getTargetVelocities(){
        double[] targetVelocities = new double[numOfPoints];
        targetVelocities[numOfPoints-1] = 0;
        for (int i = numOfPoints - 2; i >= 0; i--) {
            double distance = increment;
            double targetVelocity = Math.sqrt(Math.pow(targetVelocities[i + 1], 2) + 2 * MAXIMUM_ACCELERATION * distance);
            targetVelocities[i] = Functions.constrain(targetVelocity, -MAXIMUM_VELOCITY, MAXIMUM_VELOCITY);
        }
        return targetVelocities;
    }

    public double[] getPoints(){
        double[] pointArray = new double[numOfPoints];
        double previousPos = initalPos;
        for(int i = 0; i < numOfPoints; i++){
            pointArray[i] = previousPos + increment;
            previousPos = pointArray[i];
        }

        return pointArray;
    }
}
