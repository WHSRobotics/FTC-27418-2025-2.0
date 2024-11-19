package org.whitneyrobotics.ftc.teamcode.Libraries.Controllers;

import android.os.Build;

import androidx.annotation.RequiresApi;

import org.whitneyrobotics.ftc.teamcode.Libraries.Utilities.Functions;

@RequiresApi(api = Build.VERSION_CODES.N)
public class PIDVAController extends PIDControllerNew {
    double maxVelocity;
    double maxAcceleration;
    double desiredPos;
    double expectedPosRampDown;
    double lastPos = 0;
    double lastVel = 0;
    double lastTime = 0;

    public PIDVAController(double mV, double mA, double kP, double kI, double kD)  {
        this.setKP(kP);
        this.setKI(kI);
        this.setKD(kD);
        this.setF((target, current, elapsedTime) -> (target > current) ? 15 : 0); //additional power when going up to reduce gravity
        maxVelocity = mV;
        maxAcceleration = mA;
        expectedPosRampDown = (maxVelocity*maxVelocity)/(2*maxAcceleration);
    }

    /**
     * @implNote Call to set variables needed before output loop
     * @param desiredPos dp
     * @param lastPos lp
     * @param time currentTime
     */
    public void setDesiredPos(double desiredPos,double lastPos,double time) {
        this.desiredPos = desiredPos;
        this.lastPos = lastPos;
        this.lastTime = time;
        this.lastVel = 0;
    }

    /**
     *
     * @return power of motor
     */
    public double output(double currentPos, double currentTime) {
        double posChange = currentPos-lastPos;
        double direction = Math.signum(desiredPos - currentPos);
        double posError = Math.abs(desiredPos - currentPos);
        double timeElapsed = currentTime-lastTime;

        double currentVelocity = (posChange/timeElapsed)*2;
        double velChange = Math.abs(currentVelocity-lastVel);

        double currentAcceleration = velChange/timeElapsed;

        double realMaxVelocity = Functions.clamp(direction*Math.sqrt(currentAcceleration*desiredPos)
                ,-maxVelocity,
                maxVelocity);
        double acceleratePos = (realMaxVelocity * realMaxVelocity) / (2 * currentAcceleration);

        if (posError > 0) {
            if (posError <= acceleratePos) {
                return -1 * direction;
            } else {
                return direction;
            }
        }


        lastTime = currentTime;
        lastPos = currentPos;
        lastVel = currentVelocity;

        return 0.0;
    }
}

