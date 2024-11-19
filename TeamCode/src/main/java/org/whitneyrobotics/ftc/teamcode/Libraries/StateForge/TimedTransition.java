package org.whitneyrobotics.ftc.teamcode.Libraries.StateForge;

public class TimedTransition implements TransitionCondition {
    private double startTime = 0;
    private final double time;

    /**
     * Note that the time will always be final for this timed transition.
     * @param time Time to wait, in seconds
     */
    public TimedTransition(double time){
        this.time = time;
    }

    public void startTimer() {
        startTime = System.nanoTime();
    }

    public boolean timerStarted(){
        return startTime != 0;
    }

    public void reset(){
        startTime = 0;
    }

    @Override
    public boolean shouldTransition() {
        return (System.nanoTime() - startTime)/1E9 >= time;
    }
}
