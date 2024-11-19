package org.whitneyrobotics.ftc.teamcode.Libraries.Navigation._DEPRECIATED_PurePursuit;


import com.acmerobotics.dashboard.config.Config;
import org.whitneyrobotics.ftc.teamcode.Libraries.Controllers.ControlConstants;

@Config
public class PurePursuitRobotConstants {

    public final static double MAX_ACCELERATION = 1500;

    public static double SWERVE_KP = 0;
    public static double SWERVE_KV = 1/1100;
    public static double SWERVE_KA = 0;//.00001;

    public static double STRAFE_KP = 0, STRAFE_KV = 0, STRAFE_KA = 0;

    public final static double hKP = 0, hKI = 0, hKD = 0;
    public final static ControlConstants STRAFE_HEADING_CONSTANTS = new ControlConstants(hKP, hKI, hKD);

}
