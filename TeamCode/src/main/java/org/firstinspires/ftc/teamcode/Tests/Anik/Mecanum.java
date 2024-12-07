package org.firstinspires.ftc.teamcode.Tests.Anik.Mecanum;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "driveTrain")
public class driveTrain extends OpMode {
    public DcMotor frontLeft, frontRight, backLeft, backRight;

    @Override
    public void init() {
        DcMotor frontLeft = hardwareMap.get(DcMotor.class,"frontLeft");
        DcMotor frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        DcMotor backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        DcMotor backRight = hardwareMap.get(DcMotor.class, "backRight");
    }

    @Override
    public void loop() {
        double lx = gamepad1.left_stick_x * 1.05;
        double ly = -gamepad1.left_stick_y;
        double rx = gamepad1.right_stick_x;

        double GCF = Math.max(Math.abs(lx) + Math.abs(ly) + Math.abs(rx), 1);

        frontLeft.setPower((lx + ly + rx)/GCF);
        frontRight.setPower((lx - ly + rx)/GCF);
        backLeft.setPower((lx + ly - rx)/GCF);
        backRight.setPower((lx - ly - rx)/GCF);

    }
}
