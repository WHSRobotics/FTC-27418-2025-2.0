package org.whitneyrobotics.ftc.teamcode.Libraries.Geometry;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import org.whitneyrobotics.ftc.teamcode.Libraries.Geometry.LinAlg.Vector;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;

public class ReadOnlyPosition implements Iterable<Double>{
    protected double xPos;
    protected double yPos;

    public ReadOnlyPosition(double x, double y) {
        xPos = x;
        yPos = y;
    }

    public void scale(double scaleFactor) {
        xPos = xPos* scaleFactor;
        yPos = yPos*scaleFactor;
    }

    public void addX(double x) {
        xPos += x;
    }

    public void addY(double y) {
        yPos += y;
    }

    public double getMagnitude() {
        return Math.hypot(xPos, yPos);
    }

    public double getX() {
        return xPos;
    }

    public double getY() {
        return yPos;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Vector asVector(){
        return new Vector(xPos,yPos);
    }

    @Override
    public String toString(){
        return "(" + xPos + ", " + yPos + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReadOnlyPosition position = (ReadOnlyPosition) o;
        return Double.compare(position.xPos, xPos) == 0 && Double.compare(position.yPos, yPos) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(xPos, yPos);
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @NonNull
    @Override
    public Iterator<Double> iterator() {
        return new Iterator<Double>() {
            public int pointer;
            @Override
            public boolean hasNext() {
                return pointer <= 1;
            }

            @Override
            public Double next() {
                switch (pointer) {
                    case 0:
                        pointer++;
                        return xPos;
                    case 1:
                        pointer++;
                        return yPos;
                    default:
                        throw new NoSuchElementException("Point does not have more than 2 dimensions");
                }
            }
        };
    }

    /**
     * Performs the given action for each element of the {@code Iterable}
     * until all elements have been processed or the action throws an
     * exception.  Unless otherwise specified by the implementing class,
     * actions are performed in the order of iteration (if an iteration order
     * is specified).  Exceptions thrown by the action are relayed to the
     * caller.
     *
     * @param action The action to be performed for each element
     * @throws NullPointerException if the specified action is null
     * @implSpec <p>The default implementation behaves as if:
     * <pre>{@code
     *     for (T t : this)
     *         action.accept(t);
     * }</pre>
     * @since 1.8
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void forEach(@NonNull Consumer<? super Double> action) {
        action.accept(xPos);
        action.accept(yPos);
    }
}
