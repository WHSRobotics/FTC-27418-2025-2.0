package org.whitneyrobotics.ftc.teamcode.Libraries.Geometry;

import android.os.Build;

import androidx.annotation.RequiresApi;

public class ReadOnlyCoordinate extends ReadOnlyPosition{
    private double orientation;

    public ReadOnlyCoordinate(double xPosition, double yPosition, double orientationInput) {
        super(xPosition, yPosition);
        orientation = orientationInput;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public ReadOnlyCoordinate(Coordinate c){
        super(c.getX(), c.getY());
        orientation = c.getHeading();
    }

    public ReadOnlyCoordinate(ReadOnlyPosition pos, double heading) {
        super(pos.getX(), pos.getY());
        orientation = heading;
    }

    public double getHeading() {
        return orientation;
    }

    public ReadOnlyPosition getPos() {
        ReadOnlyPosition pos = new ReadOnlyPosition(getX(), getY());
        return pos;
    }
}
