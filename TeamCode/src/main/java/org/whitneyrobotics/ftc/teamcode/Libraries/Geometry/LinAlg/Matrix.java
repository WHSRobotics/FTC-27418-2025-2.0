package org.whitneyrobotics.ftc.teamcode.Libraries.Geometry.LinAlg;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.HashMap;
import java.util.Random;
import java.util.function.Function;
import java.util.function.UnaryOperator;

@RequiresApi(api = Build.VERSION_CODES.N)
public class Matrix {
    public final int rows;
    public final int columns;

    protected static Random generator = new Random();

    public static void seed(long seed){
        generator.setSeed(seed);
    }

    protected final double[][] matrix;

    public Matrix(double[][] matrix){
        validate(matrix);
        rows = matrix.length;
        columns = matrix[0].length;
        this.matrix = matrix;
    }

    public static Matrix copy(Matrix m){
        Matrix copy = new Matrix(new double[m.rows][m.columns]);
        for (int i = 0; i<m.rows; i++){
            for (int j = 0; j<m.columns; j++){
                copy.matrix[i][j] = m.matrix[i][j];
            }
        }
        return copy;
    }

    public static Matrix zeroMatrix(int rows, int columns){
        return new Matrix(new double[rows][columns]);
    }

    public static Matrix fill(int rows, int columns, double value){
        Matrix result = new Matrix(new double[rows][columns]);
        for (int i = 0; i<rows; i++){
            for (int j = 0; j<columns; j++){
                result.matrix[i][j] = value;
            }
        }
        return result;
    }

    public static Matrix ones(int rows, int columns){
        return fill(rows, columns, 1);
    }

    public static Matrix identityMatrix(int rows, int columns){
        Matrix result = new Matrix(new double[rows][columns]);
        for (int i = 0; i<rows; i++){
            for (int j = 0; j<columns; j++){
                if (i == j) result.matrix[i][j] = 1;
            }
        }
        return result;
    }

    public static Matrix identityMatrix(int size){
        return identityMatrix(size, size);
    }

    public static Matrix randomMatrix(int rows, int columns){
        Matrix result = new Matrix(new double[rows][columns]);
        for (int i = 0; i<rows; i++){
            for (int j = 0; j<columns; j++){
                result.matrix[i][j] = generator.nextDouble();
            }
        }
        return result;
    }

    /**
     * A validation method for the matrix. Asserts that there is at least 1 row and 1 column, and that the rows are of uniform length.
     * @param matrix The matrix to validate.
     * @throws IllegalArgumentException if the matrix has less than 1 row or 1 columnm or does not have uniform length
     */
    private void validate(double[][] matrix){
        if(matrix.length < 1) throw new IllegalArgumentException("Matrices must have at least 1 row.");
        int columns = matrix[0].length;
        if (columns < 1) throw new IllegalArgumentException("Matrices must have at least 1 column.");
        for(double[] row : matrix){
            if(row.length != columns) throw new IllegalArgumentException("Matrix does not have uniform length");
        }
    }

    public double[][] getMatrix(){
        return matrix;
    }

    public double get(int row, int column){
        return matrix[row][column];
    }

    public static double dot(double[] vector1, double[] vector2){
        double result = 0.0d;
        for(int i = 0; i<vector1.length; i++){
            result += vector1[i] * vector2[i];
        }
        return result;
    }

    public Matrix multiplyByScalar(double scalar){
        double[][] result = new double[rows][columns];
        for(int i = 0; i<rows; i++){
            for(int j = 0; j<columns; j++){
                result[i][j] = matrix[i][j] * scalar;
            }
        }
        return new Matrix(result);
    }

    public Matrix divideByScalar(double scalar){
        return multiplyByScalar(1/scalar);
    }

    public Matrix multiply(Matrix b){
        if(columns != b.rows) return null;
        double[][] result = new double[rows][b.columns];
        for(int row = 0; row<result.length; row++){
            for(int col = 0; col<result[row].length; col++){
                //iterate through the COLUMNS of b
                for (int i = 0; i<b.rows; i++){
                    result[row][col] += matrix[row][i] * b.matrix[i][col];
                }
            }
        }
        return new Matrix(result);
    }

    public Matrix add(Matrix b){
        if(columns != b.columns && rows != b.rows) return null;
        double[][] result = new double[columns][rows];
        for(int i = 0; i<rows; i++){
            for(int j=0; j<columns; j++){
                result[i][j] = matrix[i][j] + b.matrix[i][j];
            }
        }
        return new Matrix(result);
    }

    public Matrix subtract(Matrix b){
        return this.add(b.multiplyByScalar(-1));
    }

    public static Matrix multiply(Matrix a, Matrix b){
        return a.multiply(b);
    }

    public Matrix applyLambda(Function<Double, Double> function){
        double[][] newMatrix = new double[rows][columns];
        for(int i = 0; i<rows; i++){
            for(int j = 0; j<columns; j++){
                newMatrix[i][j] = function.apply(matrix[i][j]);
            }
        }
        return new Matrix(newMatrix);
    }

    public Matrix applyLambda(UnaryOperator<Double> function){
        double[][] newMatrix = new double[rows][columns];
        for(int i = 0; i<rows; i++){
            for(int j = 0; j<columns; j++){
                newMatrix[i][j] = function.apply(matrix[i][j]);
            }
        }
        return new Matrix(newMatrix);
    }

    public Vector toColumnVector(){
        double x1 = matrix[0][0];
        double[] x = new double[rows-1];
        for (int i = 1; i<rows; i++){
            x[i-1] = matrix[i][0];
        }
        return new Vector(x1, x);
    }

    /**
     * Returns a hash code value for the object. This method is
     * supported for the benefit of hash tables such as those provided by
     * {@link HashMap}.
     * <p>
     * The general contract of {@code hashCode} is:
     * <ul>
     * <li>Whenever it is invoked on the same object more than once during
     *     an execution of a Java application, the {@code hashCode} method
     *     must consistently return the same integer, provided no information
     *     used in {@code equals} comparisons on the object is modified.
     *     This integer need not remain consistent from one execution of an
     *     application to another execution of the same application.
     * <li>If two objects are equal according to the {@code equals(Object)}
     *     method, then calling the {@code hashCode} method on each of
     *     the two objects must produce the same integer result.
     * <li>It is <em>not</em> required that if two objects are unequal
     *     according to the {@link Object#equals(Object)}
     *     method, then calling the {@code hashCode} method on each of the
     *     two objects must produce distinct integer results.  However, the
     *     programmer should be aware that producing distinct integer results
     *     for unequal objects may improve the performance of hash tables.
     * </ul>
     * <p>
     * As much as is reasonably practical, the hashCode method defined by
     * class {@code Object} does return distinct integers for distinct
     * objects. (This is typically implemented by converting the internal
     * address of the object into an integer, but this implementation
     * technique is not required by the
     * Java&trade; programming language.)
     *
     * @return a hash code value for this object.
     * @see Object#equals(Object)
     * @see System#identityHashCode
     */

    @Override
    public int hashCode() {
        int sum = 0;
        for(double[] row : matrix){
            for(double d: row) sum += d;
        }
        return sum*(rows+columns);
    }

    /**
     * Returns a string representation of the object. In general, the
     * {@code toString} method returns a string that
     * "textually represents" this object. The result should
     * be a concise but informative representation that is easy for a
     * person to read.
     * It is recommended that all subclasses override this method.
     * <p>
     * The {@code toString} method for class {@code Object}
     * returns a string consisting of the name of the class of which the
     * object is an instance, the at-sign character `{@code @}', and
     * the unsigned hexadecimal representation of the hash code of the
     * object. In other words, this method returns a string equal to the
     * value of:
     * <blockquote>
     * <pre>
     * getClass().getName() + '@' + Integer.toHexString(hashCode())
     * </pre></blockquote>
     *
     * @return a string representation of the object.
     */
    @NonNull
    @Override
    public String toString() {
        String res = String.format("%sX%s Matrix",rows,columns);
        for(double[] row : matrix){
            res += "\n[";
            for(double cell : row){
                res += " " + cell;
            }
            res += " ]";
        }
        return res;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * <p>
     * The {@code equals} method implements an equivalence relation
     * on non-null object references:
     * <ul>
     * <li>It is <i>reflexive</i>: for any non-null reference value
     *     {@code x}, {@code x.equals(x)} should return
     *     {@code true}.
     * <li>It is <i>symmetric</i>: for any non-null reference values
     *     {@code x} and {@code y}, {@code x.equals(y)}
     *     should return {@code true} if and only if
     *     {@code y.equals(x)} returns {@code true}.
     * <li>It is <i>transitive</i>: for any non-null reference values
     *     {@code x}, {@code y}, and {@code z}, if
     *     {@code x.equals(y)} returns {@code true} and
     *     {@code y.equals(z)} returns {@code true}, then
     *     {@code x.equals(z)} should return {@code true}.
     * <li>It is <i>consistent</i>: for any non-null reference values
     *     {@code x} and {@code y}, multiple invocations of
     *     {@code x.equals(y)} consistently return {@code true}
     *     or consistently return {@code false}, provided no
     *     information used in {@code equals} comparisons on the
     *     objects is modified.
     * <li>For any non-null reference value {@code x},
     *     {@code x.equals(null)} should return {@code false}.
     * </ul>
     * <p>
     * The {@code equals} method for class {@code Object} implements
     * the most discriminating possible equivalence relation on objects;
     * that is, for any non-null reference values {@code x} and
     * {@code y}, this method returns {@code true} if and only
     * if {@code x} and {@code y} refer to the same object
     * ({@code x == y} has the value {@code true}).
     * <p>
     * Note that it is generally necessary to override the {@code hashCode}
     * method whenever this method is overridden, so as to maintain the
     * general contract for the {@code hashCode} method, which states
     * that equal objects must have equal hash codes.
     *
     * @param obj the reference object with which to compare.
     * @return {@code true} if this object is the same as the obj
     * argument; {@code false} otherwise.
     * @see #hashCode()
     * @see HashMap
     */
    @Override
    public boolean equals(@Nullable Object obj) {
        if(!(obj instanceof Matrix)) return false;
        return this.hashCode() == obj.hashCode();
    }
}
