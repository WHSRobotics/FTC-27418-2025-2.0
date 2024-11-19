package org.firstinspires.ftc.teamcode.Libraries.Navigation.Pathfinder;

import org.firstinspires.ftc.teamcode.Libraries.Geometry.Position;

import java.util.ArrayList;

public class TileNode extends Position {

    private ArrayList<Position> inner;

    public TileNode(double x, double y) {
        super(x, y);
        inner = new ArrayList<>();
    }

}
