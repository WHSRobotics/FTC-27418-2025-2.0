package org.whitneyrobotics.ftc.teamcode.Libraries.Motion;

public class EfficientMotionProfileTrapezoidal {

    private static double maxAccelGeneric = 5.5;
    private double maxAccelSpecific;

    private static double maxVelGeneric = 5.5;
    private double maxVelSpecific = 5.5;

    private double maxTrianglePos;

    private double profilePos;
    private double profileMaxVel;

    private double motionProfileAccel;
    private double motionProfileStable;
    private double motionProfileDecel;

    public EfficientMotionProfileTrapezoidal (double maxAccel, double maxVel){
        this.maxAccelSpecific = maxAccel;
        this.maxVelSpecific = maxVel;
        maxTrianglePos = 2 * (maxAccelSpecific * Math.pow(maxVelSpecific / maxAccelSpecific, 2));
    }

    public EfficientMotionProfileTrapezoidal (){
        maxAccelSpecific = maxAccelGeneric;
        maxVelSpecific = maxVelGeneric;
        maxTrianglePos = 2 * (maxAccelSpecific * Math.pow(maxVelSpecific / maxAccelSpecific, 2));
    }

    public void modifyGenericMaxAccel (double maxAccelGeneric){
        EfficientMotionProfileTrapezoidal.maxAccelGeneric = maxAccelGeneric;
    }

    public void modifySpecificMaxAccel (double maxAccelSpecific){
        this.maxAccelSpecific = maxAccelSpecific;
    }

    public void modifyGenericMaxVel (double maxVelGeneric){
        EfficientMotionProfileTrapezoidal.maxVelGeneric = maxVelGeneric;
    }

    public void modifySpecificMaxVel (double maxVelSpecific){
        this.maxVelSpecific = maxVelSpecific;
    }

    public double getMaxAccelGeneric(){
        return maxAccelGeneric;
    }

    public double getMaxAccelSpecific(){
        return maxAccelSpecific;
    }

    public double getMaxVelGeneric(){
        return maxVelGeneric;
    }

    public double getMaxVelSpecific(){
        return maxVelSpecific;
    }

    public void mapMotionProfiles (double position){
        profilePos = position;
        maxTrianglePos = 2 * (maxAccelSpecific * Math.pow(maxVelSpecific / maxAccelSpecific, 2));
        if (position <= maxTrianglePos){
            motionProfileAccel = Math.sqrt(position / maxAccelSpecific);
            motionProfileStable = 0 + motionProfileAccel;
            motionProfileDecel = Math.sqrt(position / maxAccelSpecific) + motionProfileStable;
            profileMaxVel = motionProfileAccel * maxAccelSpecific;
        } else {
            motionProfileAccel = maxVelSpecific / maxAccelSpecific;
            motionProfileStable = (position - maxTrianglePos) / maxVelSpecific + motionProfileAccel;
            motionProfileDecel = maxVelSpecific / maxAccelSpecific + motionProfileStable;
            profileMaxVel = maxVelSpecific;
        }
    }

    public double posAt (double time){
        if (time < motionProfileAccel){
            return (0.5 * maxAccelSpecific * Math.pow(time, 2));
        } else if (time < motionProfileStable){
            return (0.5 * maxAccelSpecific * Math.pow(motionProfileAccel, 2) + (profileMaxVel * (time - motionProfileAccel)));
        } else if (time < motionProfileDecel){
            return (0.5 * maxAccelSpecific * Math.pow(motionProfileAccel, 2) + (profileMaxVel * (motionProfileStable - motionProfileAccel)) + (profileMaxVel * (time - motionProfileStable) - 0.5 * maxAccelSpecific * Math.pow(time - motionProfileStable, 2)));
        } else {
            return profilePos;
        }
    }

    public double velAt (double time){
        if (time < motionProfileAccel){
            return (maxAccelSpecific * time);
        } else if (time < motionProfileStable){
            return (profileMaxVel);
        } else if (time < motionProfileDecel){
            return (profileMaxVel - maxAccelSpecific * (time - motionProfileStable));
        } else {
            return 0;
        }
    }

}
