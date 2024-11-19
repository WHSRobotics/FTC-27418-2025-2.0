package org.whitneyrobotics.ftc.teamcode.Libraries.Geometry;

import android.os.Build;

import androidx.annotation.RequiresApi;

/**
 * Class for storing positions on the field
 *
 * @see Coordinate - Alternative class, with heading
 */

@RequiresApi(api = Build.VERSION_CODES.N)
public class Position extends ReadOnlyPosition{

    public Position(double x, double y) {
        super(x,y);
    }

    public void setX(double x) {
        xPos = x;
    }

    public void setY(double y) {
        yPos = y;
    }
}
