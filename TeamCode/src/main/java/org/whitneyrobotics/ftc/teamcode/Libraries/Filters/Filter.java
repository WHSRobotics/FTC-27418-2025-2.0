package org.whitneyrobotics.ftc.teamcode.Libraries.Filters;

public interface Filter {
    void calculate(double newState);
    double getOutput();
}
