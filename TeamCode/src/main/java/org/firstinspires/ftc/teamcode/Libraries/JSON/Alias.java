// Refactored by: Christopher Gholmieh
// Package:
package org.firstinspires.ftc.teamcode.Libraries.JSON;

// Imports:
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;


// Interface:
@Retention(RetentionPolicy.RUNTIME)
public @interface Alias {
    // Methods:
    String value();
}