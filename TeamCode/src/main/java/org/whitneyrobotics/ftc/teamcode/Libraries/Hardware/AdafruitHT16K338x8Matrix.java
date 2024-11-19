package org.whitneyrobotics.ftc.teamcode.Libraries.Hardware;

import com.qualcomm.robotcore.hardware.DigitalChannelController;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchDevice;
import com.qualcomm.robotcore.hardware.configuration.annotations.DeviceProperties;
import com.qualcomm.robotcore.hardware.configuration.annotations.I2cDeviceType;
import com.qualcomm.robotcore.util.TypeConversion;

@DeviceProperties(xmlTag = "HT16K33", name="Adafruit 8x8 Matrix", description = "8x8 Single color LED Matrix")
@I2cDeviceType
public class AdafruitHT16K338x8Matrix extends I2cDeviceSynchDevice<I2cDeviceSynch> {

    DigitalChannelController c = null;
    public enum Register {
        FIRST(0),
        ENABLE(0x21),
        STANDBY(0x20),
        ROW_DRIVER_OUTPUT(0xA0),
        INT_OUTPUT_ACTIVE_LOW(0xA1),
        INT_OUTPUT_ACTIVE_HIGH(0xA3),
        DISPLAY_OFF(0x80),
        DISPLAY_ON(0x81),
        DISPLAY_BLINK_2HZ(0x83),
        DISPLAY_BLINK_1HZ(0x85),
        DISPLAY_BLINK_0_5HZ(0x87),
        LAST(DISPLAY_BLINK_0_5HZ.bVal);

        public final int bVal;
        Register(int bVal){
            this.bVal = bVal;
        }
    }

    private final int HIGH = 0X1;
    private final int LOW = 0x0;

    public enum VoltageState {
        HIGH(0x1),
        LOW(0x0);

        public final int val;
        VoltageState(int val){
            this.val = val;
        }
    }

    protected void setOptimalReadWindow(){
        // Sensor registers are read repeatedly and stored in a register. This method specifies the
        // registers and repeat read mode
        I2cDeviceSynch.ReadWindow readWindow = new I2cDeviceSynch.ReadWindow(
                Register.FIRST.bVal,
                Register.LAST.bVal - Register.FIRST.bVal + 1,
                I2cDeviceSynch.ReadMode.REPEAT);
        this.deviceClient.setReadWindow(readWindow);
    }

    protected void writeShort(Register reg, short val){
        this.deviceClient.write(reg.bVal, TypeConversion.shortToByteArray(val));
    }

    protected short readShort(Register reg){
        return TypeConversion.byteArrayToShort(deviceClient.read(reg.bVal,2));
    }

    public short getManufacturerIdRaw(){
        return 0x0;
    }

    private static final int DEFAULT_ADDRESS = 0x70;

    public AdafruitHT16K338x8Matrix(I2cDeviceSynch deviceClient){
        this(deviceClient,DEFAULT_ADDRESS);
    }

    public AdafruitHT16K338x8Matrix(I2cDeviceSynch deviceClient, int address){
        super(deviceClient, true);
        this.deviceClient.setI2cAddress(new I2cAddr(Math.max(Math.min(address, 0x77),0x70)));

        super.registerArmingStateCallback(false);
        this.deviceClient.engage();
    }

    /**
     * Actually carries out the initialization of the instance.
     *
     * @return Whether the initialization was successful or not
     */
    @Override
    protected boolean doInitialize() {
        return false;
    }

    /**
     * Returns an indication of the manufacturer of this device.
     *
     * @return the device's manufacturer
     */
    @Override
    public Manufacturer getManufacturer() {
        return Manufacturer.Adafruit;
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
        return "Adafruit HT16K33 8x8 LED Matrix";
    }
}
