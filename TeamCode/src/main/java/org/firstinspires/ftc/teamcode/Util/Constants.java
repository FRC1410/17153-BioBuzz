package org.firstinspires.ftc.teamcode.Util;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;

public class Constants {
    // vision constants
    public static final double TAG_WIDTH = 6.25;
    public static final double CAM_OFFSET_X = 1.5;
    public static final double CAM_OFFSET_Y = 8;
    public static final double[] TERMINAL_ANGLE_VECTOR = {1,0}; // this is for vision, and the main way we make sure that our vision code and pedro talk nicely together, NO TOUCHY.
    // all it does is tell the apriltag system what pedro thinks the angle 0 is.



    //Susan
    public static final double SUSAN_POS_1 = 0;
    public static final double SUSAN_POS_2 = 4860;
    public static final double SUSAN_POS_3 = 2658;
    public static final double SUSAN_LIFT_POS_UP = 0.4;
    public static final double SUSAN_LIFT_POS_DOWN = 1;

    //Hood Servo Positions
    public static final double HOOD_POS_1 = 0.0;
    public static final double HOOD_POS_2 = 0.1;
    public static final double HOOD_POS_3 = 0.2;
    public static final double HOOD_POS_4 = 0.3;
    public static final double HOOD_POS_5 = 0.4;

    //ball color ranges
    public static int PURPLE_LOWER_RED = 115;
    public static int PURPLE_UPPER_RED = 150;

    public static int PURPLE_LOWER_GREEN = 50;
    public static int PURPLE_UPPER_GREEN = 90;

    public static int PURPLE_LOWER_BLUE = 90;
    public static int PURPLE_UPPER_BLUE = 120;

    public static int GREEN_LOWER_RED = 115;
    public static int GREEN_UPPER_RED = 140;

    public static int GREEN_LOWER_GREEN = 200;
    public static int GREEN_UPPER_GREEN = 255;

    public static int GREEN_LOWER_BLUE = 115;
    public static int GREEN_UPPER_BLUE = 140;

    //Controller

    public static double MATCH_HALF_TIME = 10.0; //Seconds
}


