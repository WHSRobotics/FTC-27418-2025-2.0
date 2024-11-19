package org.whitneyrobotics.ftc.teamcode.Extensions.GamepadEx;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.whitneyrobotics.ftc.teamcode.Libraries.JSON.RobotDataUtil;
import org.whitneyrobotics.ftc.teamcode.Libraries.JSON.WHSRobotData;

public class RumbleEffects {
    public static Gamepad.RumbleEffect endgame = new Gamepad.RumbleEffect.Builder()
            .addStep(1,1,500)
            .addStep(0,0,500)
            .addStep(1,1,500)
            .addStep(0,0,500)
            .addStep(1,1,1000)
            .build();

    public static Gamepad.RumbleEffect matchEnd = new Gamepad.RumbleEffect.Builder()
            .addStep(1,1,250)
            .addStep(0,0,250)
            .addStep(1,1,250)
            .addStep(0,0,250)
            .addStep(1,1,250)
            .addStep(0,0,250)
            .addStep(0.4,0.4,500)
            .addStep(0,0,500)
            .addStep(0.6,0.6,500)
            .addStep(0,0,500)
            .addStep(0.8,0.8,500)
            .addStep(0,0,500)
            .addStep(1,1,500)
            .addStep(0,0,500)
            .addStep(1,1,500)
            .addStep(0,0,500)
            .addStep(1,1,2000)
            .build();

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void doStuff() {
        WHSRobotData.heading = 2;
        RobotDataUtil.load(WHSRobotData.class);
    }
}
