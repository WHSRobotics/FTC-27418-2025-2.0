package org.whitneyrobotics.ftc.teamcode.Libraries.Navigation._DEPRECIATED_PurePursuit;

public class FollowerConstants {

    // Follower
    private double lookaheadDistance;
    private boolean backwards;

    public FollowerConstants(double lookaheadDistance, boolean backwards) {
        this.lookaheadDistance = lookaheadDistance;
        this.backwards = backwards;
    }

    public double getLookaheadDistance() {
        return lookaheadDistance;
    }

    public void setLookaheadDistance(double lookaheadDistance) {
        this.lookaheadDistance = lookaheadDistance;
    }

    public boolean backwards(){
        return backwards;
    }

}
