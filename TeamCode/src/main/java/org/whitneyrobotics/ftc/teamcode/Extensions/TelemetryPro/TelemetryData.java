package org.whitneyrobotics.ftc.teamcode.Extensions.TelemetryPro;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TelemetryData {
    boolean FTCDashboard() default true;
    LineItem.Color color() default LineItem.Color.WHITE;
}
