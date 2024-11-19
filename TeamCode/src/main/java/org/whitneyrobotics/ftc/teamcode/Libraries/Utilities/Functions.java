package org.whitneyrobotics.ftc.teamcode.Libraries.Utilities;

import static org.whitneyrobotics.ftc.teamcode.Libraries.Navigation._DEPRECIATED_PurePursuit.PathGenerator.generateSwervePath;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.acmerobotics.roadrunner.geometry.Pose2d;

import org.whitneyrobotics.ftc.teamcode.Libraries.Geometry.Coordinate;
import org.whitneyrobotics.ftc.teamcode.Libraries.Geometry.Position;
import org.whitneyrobotics.ftc.teamcode.Libraries.Navigation._DEPRECIATED_PurePursuit.SwerveWaypoint;
import org.whitneyrobotics.ftc.teamcode.Libraries.Navigation._DEPRECIATED_PurePursuit.FollowerConstants;
import org.whitneyrobotics.ftc.teamcode.Libraries.Navigation._DEPRECIATED_PurePursuit.swervetotarget.SwervePath;
import org.whitneyrobotics.ftc.teamcode.Libraries.Navigation._DEPRECIATED_PurePursuit.swervetotarget.SwervePathGenerationConstants;

import java.util.ArrayList;

/**
 * General purpose functions class
 */
@RequiresApi(api = Build.VERSION_CODES.N)
public class Functions {

    private Functions(){}
    public static <T> T requireNotNull(T arg){
        if (arg == null){
            throw new IllegalArgumentException("Null argument");
        }
        return arg;
    }

    public static int randInt(int min, int max){
        return (int)Math.floor(Math.random()*max + min);
    }

    public static double calculateDistance(Position current, Position target) {
        double distance;
        distance = Math.sqrt(Math.pow(target.getX() - current.getX(), 2) + Math.pow(target.getY() - current.getY(), 2));
        return distance;
    }

    public static int calculateIndexOfSmallestValue(double[] array) {
        double smallest = array[0];
        int posInArray = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i] < smallest) {
                smallest = array[i];
                posInArray = i;
            }
        }
        return posInArray;
    }

    public static double distanceFormula(double x1, double y1, double x2, double y2) {
        double distance = Math.sqrt(Math.pow(Math.abs(x1 - x2), 2) + Math.pow(Math.abs(y2 - y1), 2));
        return distance;
    }

    /**
     * Converts angles from 0-360 to -180-180
     */

    public static double normalizeAngle(double angle) {
        while (angle > 180 || angle < -180) {
            if (angle > 180) {
                angle = angle - 360;
            } else if (angle < -180) {
                angle = angle + 360;
            }
        }
        return angle;
    }

    public static double noramlizeAngleRadians (double angle) {
        while (angle > Math.PI || angle < -Math.PI) {
            if (angle > Math.PI) {
                angle = angle - (2 * Math.PI);
            } else if (angle < -Math.PI) {
                angle = angle + (2 * Math.PI);
            }
        }
        return angle;
    }

    public static Position transformCoordinates(double[][] dcm, Position vector) {
        Position transformedVector;

        double x = dcm[0][0] * vector.getX() + dcm[0][1] * vector.getY();
        double y = dcm[1][0] * vector.getX() + dcm[1][1] * vector.getY();

        transformedVector = new Position(x, y);
        return transformedVector;
    }

    public static double constrain(double input, double min, double max) {
        if (max >= input && input >= min) {
            return input;
        } else if (input > max) {
            return max;
        } else {
            return min;
        }
    }

    public static double cosd(double degree) {
        double rad = degree * Math.PI / 180;
        return Math.cos(rad);
    }

    public static double sind(double degree) {
        double rad = degree * Math.PI / 180;
        return Math.sin(rad);
    }

    public static double tand(double degree) {
        double rad = degree * Math.PI / 180;
        return Math.tan(rad);
    }

    public static double map(double x, double in_min, double in_max, double out_min, double out_max) {
        if (x <= in_min) {
            return out_min;
        }
        if (x >= in_max) {
            return out_max;
        }
        if (in_min >= in_max) {
            throw new IllegalArgumentException("in_min greater than in_max");
        }
        if (out_min >= out_max) {
            throw new IllegalArgumentException("out_min greater than out_max");
        }
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }

    public static double clamp(double x, double min, double max){
        return Math.min(Math.max(x,min),max);
    }

    public static Position body2field(Position bodyVector, Coordinate currentCoord) {
        Position fieldVector;
        double heading = currentCoord.getHeading();

        double[][] C_b2f = {{Functions.cosd(heading), -Functions.sind(heading)},
                {Functions.sind(heading), Functions.cosd(heading)}};

        fieldVector = Functions.transformCoordinates(C_b2f, bodyVector);
        return fieldVector;

    }

    public static Position field2body(Position fieldVector, Coordinate currentCoord) {
        Position bodyVector;
        double heading = currentCoord.getHeading();

        double[][] C_f2b = {{Functions.cosd(heading), Functions.sind(heading)},
                {-Functions.sind(heading), Functions.cosd(heading)}};
        bodyVector = Functions.transformCoordinates(C_f2b, fieldVector);
        return bodyVector;

    }



    public static Position front2back(Position frontVector) {
        Position backVector;
        double heading = 180;

        double[][] C_f2b = {{-1, 0},
                {0, -1}};
        backVector = Functions.transformCoordinates(C_f2b, frontVector);
        return backVector;
    }

    public static class Positions {
        public static Position add(Position pos1, Position pos2) {
            Position sum;

            double x = pos1.getX() + pos2.getX();
            double y = pos1.getY() + pos2.getY();

            sum = new Position(x, y);
            return sum;
        }

        public static Position subtract(Position pos1, Position pos2) {
            Position difference;

            double x = pos1.getX() - pos2.getX();
            double y = pos1.getY() - pos2.getY();

            difference = new Position(x, y);
            return difference;
        }

        public static Position scale(double scaleFactor, Position pos) {
            Position scaledPos;

            double x = scaleFactor * pos.getX();
            double y = scaleFactor * pos.getY();

            scaledPos = new Position(x, y);
            return scaledPos;
        }

        public static double dot(Position pos1, Position pos2) {
            double dotProduct = pos1.getX() * pos2.getX() + pos1.getY() * pos2.getY();
            return dotProduct;
        }

        //is this not dot product?
        public static double getCross3dMagnitude(Position pos1, Position pos2) {
            double z = pos1.getX() * pos2.getY() - pos1.getY() * pos2.getX();
            return z;
        }
    }

    public static ArrayList<Position> reversePath(ArrayList<Position> path){
        ArrayList<Position> output = new ArrayList<>();
        for(Position pos : path){
            output.add(0,pos);
        }
        return output;
    }

    public static SwervePath reversePath (SwervePath path){

        FollowerConstants outputFollowerConstants = path.getAllFollowerConstants();

        SwervePathGenerationConstants outputSwervePathGenConstants = path.swervePathGenConsts;

        ArrayList<Position> outputSwervePositions = new ArrayList<Position>();

        for (SwerveWaypoint waypoint : path.getWaypoints()){
            outputSwervePositions.add(0, waypoint.getPosition());
        }

        SwervePath outputPath = generateSwervePath(outputSwervePositions, outputFollowerConstants, outputSwervePathGenConstants);

        return outputPath;
    }

    public static ArrayList<Position> instantiatePath(Position... positions){
        ArrayList<Position> path = new ArrayList<>();
        for(int i = 0; i<positions.length; i++){ //just learned that varargs are not collections ;-;
            path.add(positions[i]);
        }
        return path;
    }

    public static Pose2d rotateVectorCounterclockwise(Pose2d orig, double theta){
        return new Pose2d(
                orig.getX() * Math.cos(theta) - orig.getY() * Math.sin(theta),
                orig.getX() * Math.sin(theta) + orig.getY() * Math.cos(theta),
                orig.getHeading()
        );
    }
}