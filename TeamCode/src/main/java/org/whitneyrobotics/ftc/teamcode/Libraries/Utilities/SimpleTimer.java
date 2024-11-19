package org.whitneyrobotics.ftc.teamcode.Libraries.Utilities;

//import com.qualcomm.ftccommon.DbgLog;

/**
 * Simplified Timer Class
 *
 * @see Timer
 */

public class SimpleTimer
{

    public double expirationTime; //in seconds
    public double startTime;

    public SimpleTimer()
    {

    }

    public void set(double timerDuration)
    {
        double currentTime = System.nanoTime() / 1E9; //time in seconds
        startTime = currentTime;
        expirationTime = currentTime + timerDuration;
    }

    public void clear(){
        expirationTime = (double) 0;
    }

    public double getTime(){
        return (double) System.nanoTime() / 1E9 - startTime;
    }

    public double getTimeElapsed(){
        return getTime()-expirationTime;
    }

    public boolean isExpired()
    {
        //DbgLog.msg("whs isExpired entered");
        double currentTime = System.nanoTime() / 1E9;//(double) System.currentTimeMillis() / 1000; //time in seconds
        //DbgLog.msg("whs currentTime found");
        if(expirationTime < currentTime)
        {
            //DbgLog.msg("already expired");
        }
        return (currentTime > expirationTime);
    }

}
