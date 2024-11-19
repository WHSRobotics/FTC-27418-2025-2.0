package org.whitneyrobotics.ftc.teamcode.Libraries.Utilities;

public class NanoStopwatch {
    public NanoStopwatch(){
        reset();
    };
    private long time;
    public void reset(){
        time = System.nanoTime();
    }
    public double seconds(){
        return (System.nanoTime()-time) / 1E9;
    }

}
