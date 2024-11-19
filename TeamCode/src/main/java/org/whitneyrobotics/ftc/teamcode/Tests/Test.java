package org.whitneyrobotics.ftc.teamcode.Tests;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Test {
    /**
     * Give the test a name!
     * @return Test Name
     */
    String name();

    /**
     * Describe what the test does
     * @return Test Description
     */
    String description() default "";

    /**
     * Have your test auto-terminate after x amount of seconds. Only works on OpModeEx
     * @return Duration of time to wait before termination.
     */
    int autoTerminateAfterSeconds() default -1;
}
