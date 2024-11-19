package org.whitneyrobotics.ftc.teamcode.Libraries.Utilities;

/**
 * Immutable tuple holding three objects of various types
 * @param <A> First object type
 * @param <B> Second object type
 * @param <C> Third object type
 */

public class Triple<A, B, C> {
    public final A a;
    public final B b;
    public final C c;

    public Triple(A a, B b, C c){
        this.a = a;
        this.b = b;
        this.c = c;
    }
}
