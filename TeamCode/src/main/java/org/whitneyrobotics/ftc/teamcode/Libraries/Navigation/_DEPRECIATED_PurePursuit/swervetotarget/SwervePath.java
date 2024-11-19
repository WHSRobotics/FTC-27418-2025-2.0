package org.whitneyrobotics.ftc.teamcode.Libraries.Navigation._DEPRECIATED_PurePursuit.swervetotarget;

import org.whitneyrobotics.ftc.teamcode.Libraries.Geometry.Position;
import org.whitneyrobotics.ftc.teamcode.Libraries.Navigation._DEPRECIATED_PurePursuit.SwerveWaypoint;
import org.whitneyrobotics.ftc.teamcode.Libraries.Navigation._DEPRECIATED_PurePursuit.FollowerConstants;

import java.util.ArrayList;

public class SwervePath {
    private ArrayList<SwerveWaypoint> waypoints = new ArrayList<>();
    private FollowerConstants followerConstants;

    public SwervePath(ArrayList<SwerveWaypoint> waypoints, FollowerConstants followerConstants){
        this.waypoints = waypoints;
        this.followerConstants = followerConstants;
    }

    public SwervePath(ArrayList<SwerveWaypoint> waypoints, FollowerConstants followerConstants, SwervePathGenerationConstants swervePathGenConsts){
        this.waypoints = waypoints;
        this.followerConstants = followerConstants;
        this.swervePathGenConsts = swervePathGenConsts;
    }

    public ArrayList<SwerveWaypoint> getWaypoints() {
        return waypoints;
    }

    public void setWaypoints(ArrayList<SwerveWaypoint> waypoints) {
        this.waypoints = waypoints;
    }

    public double getFollowerConstants() {
        return followerConstants.getLookaheadDistance();
    }

    public FollowerConstants getAllFollowerConstants() { return followerConstants; }

    public Position getPositionAtIndex(int index){
        return waypoints.get(index).getPosition();
    }

    public double getTargetVelocityAtIndex(int index){
        return waypoints.get(index).getTangentialVelocity();
    }

    public int size() {
        return waypoints.size();
    }

    public boolean backwards(){
        return followerConstants.backwards();
    }

    // only used when reversing SwervePath
    public SwervePathGenerationConstants swervePathGenConsts;

}
