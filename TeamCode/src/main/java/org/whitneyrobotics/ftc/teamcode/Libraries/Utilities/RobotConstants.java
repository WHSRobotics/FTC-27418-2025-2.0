package org.whitneyrobotics.ftc.teamcode.Libraries.Utilities;

//import com.acmerobotics.dashboard.config.Config;

import com.acmerobotics.dashboard.config.Config;

import org.whitneyrobotics.ftc.teamcode.Libraries.Controllers.ControlConstants;
@Config
public class RobotConstants {
    //Drivetrain
    public final static double DEADBAND_DRIVE_TO_TARGET = 5;
    public final static double DEADBAND_ROTATE_TO_TARGET = 1.5;
    public final static double drive_min = .15;//.1245;
    public final static double drive_max = 0.9;//.6;
    public final static double rotate_min = 0.3;
    public final static double rotate_max = 1;

    public static double DRIVE_KP = 3.54;
    public static double DRIVE_KI = 0.000009;
    public static double DRIVE_KD = 0.47;//1.02;

    public static ControlConstants DRIVE_CONSTANTS = new ControlConstants(DRIVE_KP,DRIVE_KI,DRIVE_KD);

    public static double ROTATE_KP = 1.05;
    public static double ROTATE_KI = 0.00086;
    public static double ROTATE_KD = 0.09;
    public static ControlConstants ROTATE_CONSTANTS = new ControlConstants(ROTATE_KP,ROTATE_KI,ROTATE_KD);

    public static double DEADBAND_SLIDE_TO_TARGET = 15;
    public static double slide_min = 0.2;
    public static double slide_max = 1;

    public static double SLIDE_KP = 10;
    public static double SLIDE_KI = 0.0075;
    public static double SLIDE_KD = 0.0165;
    public final static ControlConstants SLIDE_CONSTANTS = new ControlConstants(SLIDE_KP,SLIDE_KI,SLIDE_KD);

    public static double STRAFE_ROTATE_KP = 0.1;
    public static double STRAFE_ROTATE_KI = 0;
    public static double STRAFE_ROTATE_KD = 0;
    public final static ControlConstants STRAFE_ROTATE_CONSTANTS = new ControlConstants(STRAFE_ROTATE_KP,STRAFE_ROTATE_KI,STRAFE_ROTATE_KD);

    public static double DEADBAND_STRAFE_TO_TARGET = 10;
    public static double strafe_min = 0;
    public static double strafe_max = 0;

    public static double STRAFE_KP = 0.1;
    public static double STRAFE_KI = 0;
    public static double STRAFE_KD = 0;
    public final static ControlConstants STRAFE_CONSTANTS = new ControlConstants(STRAFE_KP,STRAFE_KI,STRAFE_KD);

    //Outtake
    public final static double OUTTAKE_MAX_VELOCITY = 2120;
    public final static ControlConstants.FeedforwardFunction flywheelKF = (double currentPosition, double currentVelocity) -> 1/OUTTAKE_MAX_VELOCITY;
    public static double FLYWHEEL_KP = 8.6;
    public static double FLYWHEEL_KI = 0.00091;
    public static double FLYWHEEL_KD = 0.86;
    public final static ControlConstants FLYWHEEL_CONSTANTS = new ControlConstants(FLYWHEEL_KP,FLYWHEEL_KI,FLYWHEEL_KD, flywheelKF);

    public final static double rotateTestAngle = 180;
    public final static boolean rotateOrientation = true;

    public static double carouselMaxRPM = 3060;
    public final static double CAROUSEL_VELOCITY_SLOW = 2120;
    public static double CAROUSEL_SLOW_TIME = 0.8;
    public final static double CAROUSEL_VELOCITY_FAST = 4000;
    public static ControlConstants.FeedforwardFunction carouselkF = (double target, double currentVelocity) -> target/carouselMaxRPM/*target/carouselMaxRPM*/;
    public static double CAROUSEL_KP = 0.9;
    public static double CAROUSEL_KI = 0;
    public static double CAROUSEL_KD = 0.02;
    public static ControlConstants CAROUSEL_CONSTANTS = new ControlConstants(CAROUSEL_KP,CAROUSEL_KI,CAROUSEL_KD,carouselkF);

    public static void updateConstants(){
        DRIVE_CONSTANTS = new ControlConstants(DRIVE_KP,DRIVE_KI,DRIVE_KD);
        ROTATE_CONSTANTS = new ControlConstants(ROTATE_KP,ROTATE_KI,ROTATE_KD);
        CAROUSEL_CONSTANTS = new ControlConstants(CAROUSEL_KP,CAROUSEL_KI,CAROUSEL_KD,carouselkF);
    }
}
