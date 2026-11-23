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

    public static Supplier<Boolean> CONTINUOUS_SERVO_TOGGLE;
    public static Supplier<Float> SHOOT;
    public static Supplier<Float> REVERSE;
    public static void initDriver(Gamepad gamepad1) {
        DRIVE_STRAFE = () -> gamepad1.left_stick_x;
        DRIVE_FB = () -> gamepad1.left_stick_y;
        DRIVE_ROTATE = () -> gamepad1.right_stick_x;
        DRIVE_SLOW_MODE = () -> gamepad1.a;
        INTAKE_IN = () -> gamepad1.right_trigger;
        INTAKE_OUT = () -> gamepad1.left_trigger;
    }

    public static void initOperator(Gamepad gamepad2) {

    }
}