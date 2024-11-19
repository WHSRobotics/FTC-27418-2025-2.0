// Written by: Christopher Gholmieh
// Package:
package org.firstinspires.ftc.teamcode.Constants.Alliances;


// Enumeration:
public enum Alliances {
    // Blue - Alliance:
    BLUE_ALLIANCE("blue-alliance"),

    // Red - Alliance:
    RED_ALLIANCE("red-alliance");

    // Fields:
    public final String alliance_identifier;

    // Constructor:
    Alliances(String alliance_identifier) {
        this.alliance_identifier = alliance_identifier;
    }

    // Methods:
    public String get_alliance_identifier() {
        return alliance_identifier;
    }
}