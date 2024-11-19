package org.whitneyrobotics.ftc.teamcode.Libraries.Controllers;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.whitneyrobotics.ftc.teamcode.Libraries.Utilities.Functions;

public class PIDControlledMotor {
    public final double MAX_VELOCITY;
    private final DcMotorEx motor;

    public double lastPower = 0.0d;
    private double brakePower = 1.0d;
    private double targetVelocity = 0.0d;
    public PIDControllerNew controller;

    public static PIDCoefficientsNew defaultCoefficients = new PIDCoefficientsNew(1,0,0);

    public PIDControlledMotor(DcMotorEx motor, double maxVelocity){
        this(motor, maxVelocity, defaultCoefficients);
    }

    public PIDControlledMotor(DcMotorEx motor, double maxVelocity, PIDCoefficientsNew pidCoefficients){
        MAX_VELOCITY = maxVelocity;
        this.motor = motor;
        controller = new PIDControllerNew(pidCoefficients);
        controller.reset();
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void setDirection(DcMotorSimple.Direction direction){
        motor.setDirection(direction);
    }

    public void reset() {
        controller.reset();
    }

    public void reloadCoefficients(PIDCoefficientsNew coefficients){
        controller = new PIDControllerNew(coefficients);
    }

    public void reloadCoefficients(double kP, double kI, double kD){
        controller = new PIDControllerNew(kP,kI,kD);
    }

    public void brake(double brakePower){
        this.brakePower = brakePower;
    }

    public DcMotorEx getMotor() {
        return motor;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setTargetVelocity(double targetVelocity){
        this.targetVelocity = Functions.clamp(targetVelocity, -MAX_VELOCITY, MAX_VELOCITY);
        controller.setTargetWithoutReset(targetVelocity);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void update(){
        controller.calculate(motor.getVelocity(AngleUnit.RADIANS));
        double output = controller.getOutput();
        lastPower += Math.signum(output) * Functions.map(Math.abs(output), 0,MAX_VELOCITY,0,1);
        lastPower = Functions.clamp(lastPower, -1, 1);
        motor.setPower(lastPower * brakePower);
    }
}
