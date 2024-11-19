package org.whitneyrobotics.ftc.teamcode.Libraries.Hardware;

import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareDevice;

/**
 * Wrapper class for the REV Digital LED Indicator
 */
public class DigitalLedIndicator implements HardwareDevice {
    public enum LEDColor {
        RED, GREEN
    }

    private final DigitalChannel rChannel;
    private final DigitalChannel gChannel;
    private final DigitalChannel[] devices;

    public DigitalLedIndicator(DigitalChannel red, DigitalChannel green){
        this.rChannel = red;
        this.gChannel = green;

        rChannel.setMode(DigitalChannel.Mode.OUTPUT);
        rChannel.setState(true); //high to disable

        gChannel.setMode(DigitalChannel.Mode.OUTPUT);
        gChannel.setState(true);

        devices = new DigitalChannel[] {rChannel, gChannel};
    }

    public void setLit(boolean red, boolean green){
        rChannel.setState(!red);
        gChannel.setState(!green);
    }

    public void off(){
        rChannel.setState(true);
        gChannel.setState(true);
    }

    public void setLit(LEDColor color, boolean on){
        devices[color.ordinal()].setState(!on);
    }

    /**
     * Returns an indication of the manufacturer of this device.
     *
     * @return the device's manufacturer
     */
    @Override
    public Manufacturer getManufacturer() {
        return rChannel.getManufacturer();
    }

    /**
     * Returns a string suitable for display to the user as to the type of device.
     * Note that this is a device-type-specific name; it has nothing to do with the
     * name by which a user might have configured the device in a robot configuration.
     *
     * @return device manufacturer and name
     */
    @Override
    public String getDeviceName() {
        return "REV Digital LED Indicator (wrapper)";
    }

    /**
     * Get connection information about this device in a human readable format
     *
     * @return connection info
     */
    @Override
    public String getConnectionInfo() {
        return rChannel.getConnectionInfo();
    }

    /**
     * Version
     *
     * @return get the version of this device
     */
    @Override
    public int getVersion() {
        return rChannel.getVersion();
    }

    /**
     * Resets the device's configuration to that which is expected at the beginning of an OpMode.
     * For example, motors will reset the their direction to 'forward'.
     */
    @Override
    public void resetDeviceConfigurationForOpMode() {
        rChannel.resetDeviceConfigurationForOpMode();
        gChannel.resetDeviceConfigurationForOpMode();
    }

    /**
     * Closes this device
     */
    @Override
    public void close() {
        rChannel.close();
        gChannel.close();
    }
}
