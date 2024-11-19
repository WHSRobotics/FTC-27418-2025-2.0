package org.whitneyrobotics.ftc.teamcode.Libraries.Navigation._DEPRECIATED_PurePursuit.strafetotarget;

import org.whitneyrobotics.ftc.teamcode.Libraries.Controllers.PIDController;
import org.whitneyrobotics.ftc.teamcode.Libraries.Geometry.Coordinate;
import org.whitneyrobotics.ftc.teamcode.Libraries.Geometry.Position;
import org.whitneyrobotics.ftc.teamcode.Libraries.Navigation._DEPRECIATED_PurePursuit.StrafeWaypoint;
import org.whitneyrobotics.ftc.teamcode.Libraries.Navigation._DEPRECIATED_PurePursuit.motion.RateLimiter;
import org.whitneyrobotics.ftc.teamcode.Libraries.Navigation._DEPRECIATED_PurePursuit.PurePursuitRobotConstants;
import org.whitneyrobotics.ftc.teamcode.Libraries.Utilities.Functions;
//import org.whitneyrobotics.ftc.teamcode.subsys.Drivetrain;

import java.util.ArrayList;

public class StrafeFollower {

    StrafePath path;

    public int lastClosestPointIndex = 0;
    public int lastClosestHeadingIndex = 0;
    private int lastIndex = 0;
    private double currentTValue = 0;

    public boolean conditionMet = false;

    private double[] currentTargetWheelVelocities = {0.0, 0.0, 0.0, 0.0};
    private double[] lastTargetWheelVelocities = {0.0, 0.0, 0.0, 0.0};

    private double lastTime;
    private RateLimiter targetVelocityRateLimiter;

    private double kP = PurePursuitRobotConstants.STRAFE_KP;
    private double kV = PurePursuitRobotConstants.STRAFE_KV;
    private double kA = PurePursuitRobotConstants.STRAFE_KA;

    private double trackWidth = 18;
    private double wheelBase = 18;

    PIDController headingController = new PIDController(PurePursuitRobotConstants.STRAFE_HEADING_CONSTANTS);


    private boolean inProgress;

    public StrafeFollower (StrafePath path){
        this.path = path;
        targetVelocityRateLimiter = new RateLimiter(PurePursuitRobotConstants.MAX_ACCELERATION, 0);
        lastTime = System.nanoTime()/1E9;
    }

    public double[] calculateMotorPowers(Coordinate currentCoord, double[] currentBackVelocities, double frontRightVelocity, double currentAngularVelocity) {
        double[] currentWheelVelocities = {currentBackVelocities[1] - (frontRightVelocity - currentBackVelocities[0]), frontRightVelocity, currentBackVelocities[0], currentBackVelocities[1]};

        boolean tFound = false;
        for (int i = lastIndex; i < path.size() - 1; i++) {
            Double nextTValue = calculateT(path.getCoordinateAtIndex(i), path.getCoordinateAtIndex(i + 1), path.followerConstants.getLookaheadDistance(), currentCoord);

            if (!tFound && !nextTValue.isNaN() && (nextTValue + i) > (currentTValue + lastIndex)) {
                tFound = true;
                currentTValue = nextTValue;
                lastIndex = i;
            }
        }

        Position calculatedTStartPoint = path.getCoordinateAtIndex(lastIndex);
        Position calculatedTEndPoint = path.getCoordinateAtIndex(lastIndex+1);
        Position lookaheadPoint = Functions.Positions.add(calculatedTStartPoint, Functions.Positions.scale(currentTValue, Functions.Positions.subtract(calculatedTEndPoint, calculatedTStartPoint)));

        int indexOfClosestPoint = calculateIndexOfClosestPoint(path.getWaypoints(), currentCoord);
        int indexOfClosestHeading = calculateIndexOfClosestHeading(currentCoord);

        Position vectorToLookaheadPoint = Functions.Positions.subtract(lookaheadPoint, currentCoord);
        vectorToLookaheadPoint = Functions.field2body(vectorToLookaheadPoint, currentCoord);
        double angleToLookaheadPoint = Math.toDegrees(Math.atan2(vectorToLookaheadPoint.getY(), vectorToLookaheadPoint.getX()));

        headingController.calculate(path.getAngularVelocityAtIndex(indexOfClosestHeading)- currentAngularVelocity);
        double headingFeedback = headingController.getOutput();

        currentTargetWheelVelocities = calculateTargetWheelVelocities(path.getTangentialVelocityAtIndex(indexOfClosestPoint), angleToLookaheadPoint, path.getAngularVelocityAtIndex(indexOfClosestHeading));

        double deltaTime = System.nanoTime() / 1E9 - lastTime;
        double[] targetWheelAccelerations = new double[4];
        for (int i = 0; i < targetWheelAccelerations.length; i++) {
            targetWheelAccelerations[i] = (currentTargetWheelVelocities[i] - lastTargetWheelVelocities[i]) / deltaTime;
        }
        if (indexOfClosestPoint != path.size() - 1) {
            double[] feedBack = {currentTargetWheelVelocities[0] - currentWheelVelocities[0], currentTargetWheelVelocities[1] - currentWheelVelocities[1], currentTargetWheelVelocities[2] - currentWheelVelocities[2], currentTargetWheelVelocities[3] - currentWheelVelocities[3]};
            for (int i = 0; i < feedBack.length; i++) {
                feedBack[i] *= kP;
            }

            double[] feedForwardVel = {kV * currentTargetWheelVelocities[0], kV * currentTargetWheelVelocities[1], kV * currentTargetWheelVelocities[2], kV * currentTargetWheelVelocities[3]};
            double[] feedForwardAccel = {kA * targetWheelAccelerations[0], kA * targetWheelAccelerations[1], kA * targetWheelAccelerations[2], kA * targetWheelAccelerations[3]};
            double[] feedForward = {feedForwardVel[0] + feedForwardAccel[0], feedForwardVel[1] + feedForwardAccel[1], feedForwardVel[2] + feedForwardAccel[2], feedForwardVel[3] + feedForwardAccel[3]};
            double[] motorPowers = {Functions.constrain(feedBack[0] + feedForward[0] - headingFeedback, -1, 1), Functions.constrain(feedBack[1] + feedForward[1] + headingFeedback, -1, 1), Functions.constrain(feedBack[2] + feedForward[2] - headingFeedback, -1, 1), Functions.constrain(feedBack[3] + feedForward[3] + headingFeedback, -1, 1)};
            lastTargetWheelVelocities = currentTargetWheelVelocities;
            inProgress = true;
            return motorPowers;
        } else {
            inProgress = false;
        }

        return new double[] {0.0, 0.0, 0.0, 0.0};
    }


    private double calculateT(Position lineStart, Position lineEnd, double lookaheadDistance, Coordinate currentCoord) {
        // constants used throughout the method
        Position d = Functions.Positions.subtract(lineEnd, lineStart);
        Position f = Functions.Positions.subtract(lineStart, currentCoord);
        double r = lookaheadDistance;

        double a = Functions.Positions.dot(d, d);
        double b = 2 * Functions.Positions.dot(f, d);
        double c = Functions.Positions.dot(f, f) - r * r;

        double discriminant = b * b - 4 * a * c;
        if (discriminant < 0) {
            // no intersection
        } else {
            // ray didn't totally miss sphere, so there is a solution to the equation.
            discriminant = Math.sqrt(discriminant);

            // either solution may be on or off the ray so need to test both
            // t1 is always the smaller value, because BOTH discriminant and a are nonnegative.
            double t1 = (-b - discriminant) / (2 * a);
            double t2 = (-b + discriminant) / (2 * a);

            // 3x HIT cases:
            //          -o->             --|-->  |            |  --|->
            // Impale(t1 hit,t2 hit), Poke(t1 hit,t2>1), ExitWound(t1<0, t2 hit),

            // 3x MISS cases:
            //       ->  o                     o ->              | -> |
            // FallShort (t1>1,t2>1), Past (t1<0,t2<0), CompletelyInside(t1<0, t2>1)

            if (t1 >= 0 && t1 <= 1) {
                // t1 is the intersection, and it's closer than t2 (since t1 uses -b - discriminant)
                // Impale, Poke
                return t1;
            }

            if (t2 >= 0 && t2 <= 1) {
                return t2;
            }
        }
        return Double.NaN;
    }


    private int calculateIndexOfClosestPoint(ArrayList<StrafeWaypoint> smoothedPath, Coordinate currentCoord) {
        // creates array in which we store the current distance to each point in our path

        double[] distances = new double[path.size()];

        for (int i = 0
        /*lastClosestPointIndex*/
        ; i < path.size(); i++) {
            distances[i] = Functions.Positions.subtract(path.getCoordinateAtIndex(i), currentCoord).getMagnitude();
        }

        // calculates the index of value in the array with the smallest value and returns that index
        lastClosestPointIndex = Functions.calculateIndexOfSmallestValue(distances);
        return lastClosestPointIndex;
    }

    public double[] calculateTargetTranslationalWheelVelocities(double targetVelocity, double angleToLookaheadPoint) {
        double targetVelocityX = targetVelocity * Functions.cosd(angleToLookaheadPoint);
        double targetVelocityY = targetVelocity * Functions.sind(angleToLookaheadPoint);
        double k = (trackWidth + wheelBase) / 2;

        double vFL = targetVelocityX - targetVelocityY;// - k * angleToLookaheadPoint;
        double vFR = targetVelocityX + targetVelocityY;// + k * angleToLookaheadPoint;
        double vBL = targetVelocityX + targetVelocityY;// - k * angleToLookaheadPoint;
        double vBR = targetVelocityX - targetVelocityY;// + k * angleToLookaheadPoint;

        return new double[]{vFL, vFR, vBL, vBR};
    }

    public double[] calculateTargetWheelVelocities(double targetVelocity, double angleToLookaheadPoint, double targetAngularVelocity) {
        targetAngularVelocity = Math.toRadians(targetAngularVelocity);
        double targetVelocityX = targetVelocity * Functions.cosd(angleToLookaheadPoint);
        double targetVelocityY = targetVelocity * Functions.sind(angleToLookaheadPoint);
        double k = (trackWidth + wheelBase) / 2;

        double vFL = targetVelocityX - targetVelocityY - k * targetAngularVelocity;
        double vFR = targetVelocityX + targetVelocityY + k * targetAngularVelocity;
        double vBL = targetVelocityX + targetVelocityY - k * targetAngularVelocity;
        double vBR = targetVelocityX - targetVelocityY + k * targetAngularVelocity;

        return new double[]{vFL, vFR, vBL, vBR};
    }

    private int calculateIndexOfClosestHeading(Coordinate currentCoord) {
        boolean closestHeadingFound = false;
        double currentHeading = currentCoord.getHeading();
        double[] headingDiffs = new double[path.size()];
        for (int i = lastClosestHeadingIndex; i < path.size()-1; i++) {
            if (!closestHeadingFound && Math.abs(currentHeading - path.getCoordinateAtIndex(i+1).getHeading()) < Math.abs(currentHeading - path.getCoordinateAtIndex(lastClosestHeadingIndex).getHeading())) {
                if (i < path.size() - 2) {
                    if (Math.abs(path.getCoordinateAtIndex(i+2).getHeading()- currentHeading) >= Math.abs(path.getCoordinateAtIndex(i+1).getHeading() - currentHeading)) {
                        conditionMet = true;
                        lastClosestHeadingIndex = i + 1;
                        closestHeadingFound = true;
                    }
                } else {
                    closestHeadingFound = true;
                }
            }
            //headingDiffs[i] = Math.abs(currentHeading - smoothedPath[i].getHeading());
        }
        //lastClosestHeadingIndex = Functions.calculateIndexOfSmallestValue(headingDiffs);
        return lastClosestHeadingIndex;
    }
}
