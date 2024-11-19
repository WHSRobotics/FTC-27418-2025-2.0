package org.whitneyrobotics.ftc.teamcode.Libraries.Geometry;

import android.os.Build;

import androidx.annotation.RequiresApi;

/**
 * Class for carrying coordinate values
 *
 * @see Position - Alternative class, without heading
 */
@RequiresApi(api = Build.VERSION_CODES.N)
public class Coordinate extends Position {

    private double orientation;

    public Coordinate(double xPosition, double yPosition, double orientationInput) {
        super(xPosition, yPosition);
        orientation = orientationInput;
    }

    public Coordinate(Position pos, double heading) {
        super(pos.getX(), pos.getY());
        orientation = heading;
    }


    public double getHeading() {
        return orientation;
    }

    public Position getPos() {
        Position pos = new Position(getX(), getY());
        return pos;
    }

    public void setHeading(double heading) {
        orientation = heading;
    }

    public void setPos(Position pos) {
        setX(pos.getX());
        setY(pos.getY());
    }

    public static Coordinate copy(Coordinate c){
        return new Coordinate(c.xPos,c.yPos, c.orientation);
    }

    @Override
    public String toString(){
        return "(" + xPos + ", " + yPos + " θ=" + orientation + ")";
    }
}