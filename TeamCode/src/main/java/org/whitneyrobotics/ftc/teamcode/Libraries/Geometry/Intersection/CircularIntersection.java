package org.whitneyrobotics.ftc.teamcode.Libraries.Geometry.Intersection;

import org.whitneyrobotics.ftc.teamcode.Libraries.Geometry.Position;
import org.whitneyrobotics.ftc.teamcode.Libraries.Utilities.Functions;

public class CircularIntersection {
    private Position center;
    public double radius;

    public CircularIntersection(Position center, double radius){
        setCircle(center, radius);
    }

    public CircularIntersection(double centerX, double centerY, double radius){
        setCircle(centerX,centerY,radius);
    }

    public void setCircle(Position center, double radius){
        this.center = center;
        this.radius = radius;
    }

    public void setCircle(double centerX, double centerY, double radius){
        this.center = new Position(centerX,centerY);
        this.radius = radius;
    }

    public Intersection checkForIntersection(Position point){
        double distance = Functions.distanceFormula(point.getX(),point.getY(),center.getX(),center.getY());
        Intersection i = new Intersection(distance <= radius, distance);
        return i;
    }


    public Intersection checkForIntersection(double centerX, double centerY){
        return checkForIntersection(new Position(centerX,centerY));
    }

    public Position getCenter(){return center;}

    public double getRadius(){return radius;}
}
