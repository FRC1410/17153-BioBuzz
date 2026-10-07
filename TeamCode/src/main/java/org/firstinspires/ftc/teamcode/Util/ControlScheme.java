package org.firstinspires.ftc.teamcode.Util;

import com.qualcomm.robotcore.hardware.Gamepad;
import java.util.function.Supplier;

public class ControlScheme {

    //Drivetrain
    public static Supplier<Float> DRIVE_STRAFE;
    public static Supplier<Float> DRIVE_FB;
    public static Supplier<Float> DRIVE_ROTATE;
    public static Supplier<Boolean> DRIVE_SLOW_MODE;

    //Intake
    public static Supplier<Float> INTAKE_IN;
    public static Supplier<Float> INTAKE_OUT;

    //Storage
    public static Supplier<Boolean> DEXTER_TOGGLE;
    public static Supplier<Boolean> DEXTER_P1;
    public static Supplier<Boolean> DEXTER_P2;
    public static Supplier<Boolean> DEXTER_P3;
    public static Supplier<Boolean> DEXTER_P4;

    public static void initDriver(Gamepad gamepad1) {
        DRIVE_STRAFE = () -> gamepad1.left_stick_x;
        DRIVE_FB = () -> gamepad1.left_stick_y;
        DRIVE_ROTATE = () -> gamepad1.right_stick_x;
        DRIVE_SLOW_MODE = () -> gamepad1.a;
        INTAKE_IN = () -> gamepad1.right_trigger;
        INTAKE_OUT = () -> gamepad1.left_trigger;
    }

    public static void initOperator(Gamepad gamepad2) {

        DEXTER_P1 = () -> gamepad2.dpad_left;
        DEXTER_P2 = () -> gamepad2.dpad_down;
        DEXTER_P3 = () -> gamepad2.dpad_right;
        DEXTER_P4 = () -> gamepad2.dpad_up;
        DEXTER_TOGGLE = () -> gamepad2.right_bumper;
    }
}