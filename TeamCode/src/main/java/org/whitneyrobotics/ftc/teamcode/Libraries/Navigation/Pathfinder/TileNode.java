package org.whitneyrobotics.ftc.teamcode.Libraries.Navigation.Pathfinder;

import org.whitneyrobotics.ftc.teamcode.Libraries.Geometry.Position;

import java.util.ArrayList;

public class TileNode extends Position {

    private ArrayList<Position> inner;

    public TileNode(double x, double y) {
        super(x, y);
        inner = new ArrayList<>();
    }

}
