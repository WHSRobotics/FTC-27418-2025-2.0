package org.whitneyrobotics.ftc.teamcode.Libraries.JSON;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
public @interface Alias {
    String value();
}
