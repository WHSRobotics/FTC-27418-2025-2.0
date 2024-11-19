package org.whitneyrobotics.ftc.teamcode.Extensions.TelemetryPro.AutoSetupTesting;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.hardware.rev.Rev2mDistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.VoltageUnit;
import org.whitneyrobotics.ftc.teamcode.Extensions.GamepadEx.GamepadEx;
import org.whitneyrobotics.ftc.teamcode.Libraries.Filters.Filter;

public class Tests {

    public static class Warning extends AssertionError {
        public Warning(String msg) { super(msg); }
    }

    public static void assertTrue(Boolean b) throws AssertionError {if (!b) throw new AssertionError("Assertion failed"); }
    public static void assertFalse(Boolean b) throws AssertionError {if (b) throw new AssertionError("Assertion failed"); }
    public static void assertNull(Object o) throws AssertionError {if (o != null) throw new AssertionError("Assertion failed"); }
    public static void assertNotNull(Object o) throws AssertionError {if (o == null) throw new AssertionError("Assertion failed"); }
    /**
     * Asserts if two values are the same.
     * @param d1
     * @param d2
     * @throws AssertionError if the two values are not equal
     */
    public static void assertEquals(double d1, double d2) throws AssertionError{
        if (d1 != d2) throw new AssertionError(String.format("%s is not equal to %s",d1,d2));
    }

    /**
     * Asserts if two values are the within 7 decimal points of each other.
     * @param d1
     * @param d2
     * @throws AssertionError if the two values are not almost equal
     */
    public static void assertAlmostEquals(double d1, double d2) throws AssertionError{
        if (Math.abs(d1-d2) > 1e-7) throw new AssertionError(String.format("%s is not almost equal to %s",d1,d2));
    }

    /**
     * Asserts if two decimal points are within 3 decimal points of each other.
     * @param d1
     * @param d2
     * @throws AssertionError if the two values are not weakly equal
     */
    public static void assertWeakEquality(double d1, double d2) throws AssertionError{
        if (Math.abs(d1-d2) > 1e-3) throw new AssertionError(String.format("%s is not weakly equal to %s",d1,d2));
    }

    /**
     * Asserts if two decimal points are within 3 decimal points of each other.
     * @param d1
     * @param d2
     * @throws AssertionError if the two values are not almost equal
     */
    public static void assertVariableEquality(double d1, double d2, int significantFigures) throws AssertionError{
        if (Math.abs(d1 - d2) > Math.pow(1,-significantFigures)) throw new AssertionError(String.format("%s is not equal to %s by %s sig figs",d1,d2, significantFigures));
    }

    public static void assertGamepadSetup(GamepadEx gamepad, String label) throws AssertionError {
        if (gamepad.hasError) throw new AssertionError(label + " was not setup correctly. Ensure it is plugged in to the DS.");
    }

    public static void assertDistanceInRange(Rev2mDistanceSensor sensor, DistanceUnit unit, double min, double max) throws AssertionError{
        assertDistanceInRange(sensor, unit, min, max,null);
    }

    public static void assertDistanceInRange(Rev2mDistanceSensor sensor, DistanceUnit unit, double min, double max, Filter filter) throws AssertionError{
        assertNotNull(sensor);
        double measurement = sensor.getDistance(unit);
        if (filter != null) {
            filter.calculate(sensor.getDistance(unit));
            measurement = filter.getOutput();
        }
        if(measurement < Math.abs(min) || measurement > Math.abs(max)) throw new AssertionError(String.format("%s was not in expected range of %s-%s. Actual: %s", sensor.getDeviceName(), Math.abs(min), Math.abs(max),measurement));
    }

    public static void assertBatteryCharged(LynxModule hub) {
        assertNotNull(hub);
        double voltage = hub.getInputVoltage(VoltageUnit.VOLTS);
        if(voltage < 12){
            throw new AssertionError(String.format("Battery is in poor-condition. Charge as soon as possible. Voltage: %s", voltage));
        }
        if (voltage < 12.5){
            throw new Warning(String.format("Battery is sub-optimal. Charge as soon as possible. Voltage :%s", voltage));
        }
    }

}
