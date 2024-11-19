package org.whitneyrobotics.ftc.teamcode.Libraries.Motion;

import java.util.function.Supplier;

/**
 * Time invariant motion profile generator for trapezoidal motion profiles
 * Uses time instead of distance to minimize oscillation.
 */
public class MotionProfileTrapezoidal {
    //Use suppliers to dynamically change velocity and acceleration limits during runtime
    private Supplier<Double> maxAccel, maxVelocity;
    private double t1,t2,tf;

    private double goalPoint;

    public MotionProfileTrapezoidal(double maxVelocity, double maxAccel){
        this.maxAccel = () -> maxAccel;
        this.maxVelocity = () -> maxVelocity;
    }

    private void calculateTimes(){
        if(Math.abs(this.goalPoint)<=Math.pow(maxVelocity.get(),2)/maxAccel.get()){ //Check if goal is less than the max triangular integral without a cruise phase
            t1 = Math.sqrt(Math.abs(this.goalPoint)/maxAccel.get());
            t2=t1;
            tf = 2*t1;
        } else {
            t1 = maxVelocity.get()/maxAccel.get();
            t2 = (Math.abs(this.goalPoint)-(maxVelocity.get()*t1))/maxVelocity.get()+t1; //Subtract the regions of the acceleration/deceleration triangles from the integral of the trapezoid and divide by rate
            tf = t2+t1;
        }
    }

    /**
     * Set the ideal change in displacement for the profile
     * @param goal How far you want to move. Must be a nonchanging value for the profile's duration.
     */
    public void setGoal(double goal){
        this.goalPoint = goal;
        calculateTimes();
    }

    public double getGoal(){
        return this.goalPoint;
    }

    public double positionAt(double t_elapsed){
        double coefficient  = (goalPoint < 0) ? -1 : 1;
        double pos;
        double a_max = maxAccel.get(), v_max = maxVelocity.get();
        if(t1==t2){
            v_max = Math.sqrt(Math.abs(goalPoint)*maxAccel.get()); //Use kinematic without t
        }
        if(t_elapsed <= t1){
            pos = 0.5*a_max*Math.pow(t_elapsed,2)*coefficient;
        } else if(t_elapsed <= t2){
            pos = positionAt(t1) + v_max*(t_elapsed-t1)*coefficient;
        } else if(t_elapsed < tf){
            pos =  positionAt(t2) + v_max*(t_elapsed-t2)*coefficient+0.5*-a_max*Math.pow(t_elapsed-t2,2)*coefficient;
        } else {
            pos =  goalPoint;
        }
        return pos;
    }

    public double velocityAt(double t_elapsed){
        double coefficient  = (goalPoint < 0) ? -1 : 1;
        double v = 0;
        double a_max = maxAccel.get(), v_max = maxVelocity.get();
        if(t1==t2){
            v_max = Math.sqrt(Math.abs(goalPoint)*maxAccel.get()); //Use kinematic without t
        }
        if(t_elapsed <= t1) v=a_max*t_elapsed;
        else if(t_elapsed <= t2) v = v_max;
        else if(t_elapsed <= tf) v = (v_max-a_max*(t_elapsed-t2));
        return v*coefficient;
    }

    public double accelerationAt(double t_elapsed){
        double coefficient  = (goalPoint < 0) ? -1 : 1;
        double a = 0;
        double a_max = maxAccel.get();
        if (t_elapsed <= t1) a=  a_max;
        else if (t_elapsed <= t2) return 0;
        else if (t_elapsed <= tf) return a = -a_max;
        return a * coefficient;

    }

    public double getDuration(){
        return tf;
    }

    public double getMaxAccel() {
        return maxAccel.get();
    }

    public void setMaxAccel(double maxAccel) {
        if(maxAccel<=0) throw new IllegalArgumentException("Max acceleration must be positive");
        if(this.maxAccel.get() == maxAccel) return; //small memory optimization
        this.maxAccel = () -> maxAccel;
        calculateTimes();
    }

    public double getMaxVelocity() {
        return maxVelocity.get();
    }

    public void setMaxVelocity(double maxVelocity) {
        if(maxVelocity<=0) throw new IllegalArgumentException("Max velocity must be positive");
        if(this.maxVelocity.get() == maxVelocity) return; //small memory optimization
        this.maxVelocity = () -> maxVelocity;
        calculateTimes();
    }

    public boolean isFinished(double t_elapsed){
        return t_elapsed >= tf;
    }
}
