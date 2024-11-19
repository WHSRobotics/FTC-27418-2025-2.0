package org.whitneyrobotics.ftc.teamcode.Libraries.Geometry.Intersection;

import org.whitneyrobotics.ftc.teamcode.Libraries.Geometry.Position;

//more of perry's cursed creations
public class RectangleIntersection {
    private Position topLeft;
    private Position bottomRight ;

    public RectangleIntersection(Position topLeft,Position bottomRight){
        setCorners(topLeft,bottomRight);
    }

    public RectangleIntersection(double topLeftX, double topLeftY, double bottomRightX, double bottomRightY){
        setCorners(topLeftX, topLeftY, bottomRightX, bottomRightY);
    }

    public void setCorners(Position topLeft,Position bottomRight){
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    public void setCorners(double topLeftX, double topLeftY, double bottomRightX, double bottomRightY){
        this.topLeft = new Position(topLeftX,topLeftY);
        this.bottomRight = new Position(bottomRightX,bottomRightY);
    }

        //checks if X and Y are between shape boundaries
    public boolean checkForIntersection(Position point){
        //we check from the middle using absolute value instead of using comparisons in case if top left > bottom right
        double avgX = (topLeft.getX() + bottomRight.getX())/2;
        double avgY = (topLeft.getY() + bottomRight.getY())/2;
        return((Math.abs(point.getX() - avgX) <= Math.abs(topLeft.getX()-avgX)) && Math.abs(point.getY() - avgY) <= Math.abs(topLeft.getY()-avgY));
    }

    public boolean checkForIntersection(double x, double y){
        return checkForIntersection(new Position(x,y));
    }

    public Position getCornerOne(){return topLeft;}

    public Position getCornerTwo(){return bottomRight;}
}
