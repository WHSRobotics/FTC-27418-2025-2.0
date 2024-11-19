package org.whitneyrobotics.ftc.teamcode.Libraries.Utilities;

/**
 * Immutable tuple holding two objects of various types
 * @param <A> First object type
 * @param <B> Second object type
 */
public class Pair<A,B> {
    private final A a;
    private final B b;
    public Pair(A a, B b){
        this.a = a;
        this.b = b;
    }
}
