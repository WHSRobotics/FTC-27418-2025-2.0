package org.whitneyrobotics.ftc.teamcode.Libraries.Geometry.LinAlg;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import org.whitneyrobotics.ftc.teamcode.Libraries.Geometry.Coordinate;
import org.whitneyrobotics.ftc.teamcode.Libraries.Geometry.Position;

import java.util.function.Function;
import java.util.function.UnaryOperator;

@RequiresApi(api = Build.VERSION_CODES.N)
public class Vector extends Matrix {
    public Vector(double x1, double... x) {
        super(new double[1 + x.length][1]);
        matrix[0][0] = x1;
        for(int i = 0; i<x.length; i++){
            matrix[i+1][0] = x[i];
        }
    }

    public Vector(int rows, int columns){
        super(new double[rows][columns]);
    }

    public static Vector vectorFromMagnitudeAndAngle(double m, double theta){
        return new Vector(m * Math.cos(theta), m * Math.sin(theta));
    }

    public static Vector vectorFromPosition(Position p){
        return new Vector(p.getX(), p.getY());
    }

    public static Vector copy(Vector v){
        Vector copy = new Vector(v.rows, v.columns);
        for (int i = 0; i<v.rows; i++){
            copy.matrix[i][0] = copy.matrix[i][0];
        }
        return copy;
    }

    public static Vector zeroVector(int rows){
        return new Vector(rows, 1);
    }

    public Vector withNewElement(double... x){
        double[][] newMatrix = new double[rows+1][1];
        for(int i = 0; i<rows; i++){
            newMatrix[i][0] = matrix[i][0];
        }
        for (int i = 0; i<x.length; i++){
            newMatrix[rows+i][0] = x[i];
        }
        return new Matrix(newMatrix).toColumnVector();
    }

    public double get(int i){
        return matrix[i][0];
    }

    public double getMagnitude(){
        double sum = 0;
        for (int i = 0; i<rows; i++){
            sum += Math.pow(matrix[i][0],2);
        }
        return Math.sqrt(sum);
    }

    public Vector normalize(){
        return this.divideByScalar(this.getMagnitude()).toColumnVector();
    }

    public double getDirection(){
        if(rows != 2) throw new UnsupportedOperationException("Calculating direction in radians only works on vectors in 2-space.");
        double magnitude = getMagnitude();
        double x = matrix[0][0];
        double y = matrix[1][0];
        if(x > 0){
            if(y>0){
                return Math.acos(x/magnitude);
            } else {
                return (2*Math.PI) - Math.acos(x/magnitude);
            }
        } else {
            return Math.PI - Math.asin(y/magnitude);
        }
    }

    public Vector add(@NonNull Vector v){
        return super.add(v).toColumnVector();
    }

    public Vector subtract(@NonNull Vector v){
        return super.subtract(v).toColumnVector();
    }

    public Vector multiplyByScalar(double scalar){
        return super.multiplyByScalar(scalar).toColumnVector();
    }

    public Vector multiply(@NonNull Vector v){
        return super.multiply(v).toColumnVector();
    }

    public Vector divideByScalar(double scalar){
        return super.divideByScalar(scalar).toColumnVector();
    }

    /**
     * Rotates the vector given the angle, in radians
     * @param theta Angle to rotate, in radians (clockwise)
     * @return New vector
     */
    public Vector rotate(double theta){
        if(rows != 2) return null;
        return new Matrix(new double[][] {
                {Math.cos(theta), Math.sin(theta)},
                {-Math.sin(theta), Math.cos(theta)}
        }).multiply(this).toColumnVector();
    }

    public double dot(@NonNull Vector v){
        double[][] matrix = this.matrix;
        double[][] otherVector = v.matrix;
        double sum = 0;
        for(int i = 0; i<rows; i++){
            sum += matrix[i][0] * otherVector[i][0];
        }
        return sum;
    }

    public Vector cross(@NonNull Vector v){
        if(rows != 3 || v.rows != 3) return null;
        return new Vector(
                this.get(1) * v.get(2) - this.get(1) * v.get(2),
                v.get(2) * v.get(0) - this.get(0) * v.get(2),
                this.get(0) * v.get(1) - this.get(1) * v.get(0)
        );
    }

    /**
     * Casts the vector to a 2D position, using the first 2 rows of the vector.
     * @return Result of the casting operation. Returns null if the vector had less than 2 rows.
     */
    public Position to2DPosition(){
        if(rows < 2) return null;
        return new Position(matrix[0][0], matrix[1][0]);
    }

    public Coordinate toCoordinate(){
        if(rows < 3) return null;
        return new Coordinate(matrix[0][0], matrix[1][0], matrix[2][0]);
    }

    @NonNull
    @Override
    public String toString() {
        String res = String.format("%s-Element Vector",rows);
        for (double[] row : matrix) {
            res += row[0] + "";
        }
        return res;
    }

    public Vector applyLambda(Function<Double, Double> function){
        return super.applyLambda(function).toColumnVector();
    }

    public Vector applyLambda(UnaryOperator<Double> function){
        return super.applyLambda(function).toColumnVector();
    }

    public double aggregate(){
        return aggregateWithCoefficients(new double[rows]);
    }

    public double aggregateWithCoefficients(double... coefficients) {
        if (coefficients.length != rows) throw new IllegalArgumentException("Number of coefficients must match number of rows in vector.");
        double sum = 0;
        for (int i = 0; i<rows; i++){
            sum += matrix[i][0] * coefficients[i];
        }
        return sum;
    }

    public double getMax(){
        double max = Double.MIN_VALUE;
        for (int i = 0; i<rows; i++){
            if(matrix[i][0] > max) max = matrix[i][0];
        }
        return max;
    }

    public double getMaxAbs(){
        double max = 0;
        for (int i = 0; i<rows; i++){
            if(Math.abs(matrix[i][0]) > max) max = Math.abs(matrix[i][0]);
        }
        return max;
    }
}
