package org.whitneyrobotics.ftc.teamcode.Libraries.Utilities.UnitConversion;

public enum DistanceUnit {
    METER(1/0.0254),
    CM(1/2.54),
    MM(1/25.4),
    INCH(1),
    FOOT(12),
    YARD(36),
    TILE_WIDTH(23.5);
    public final double conversionFactor;
    DistanceUnit(double conversionFactorToInches){
        this.conversionFactor = conversionFactorToInches;
    }

    public double convert(double value, DistanceUnit unit){
        return value * (this.conversionFactor / unit.conversionFactor);
    }

    public double  toInches(double value){
        return value * this.conversionFactor;
    }

}
