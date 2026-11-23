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

    public static Supplier<Float> FEED;

    //SUSAN
    public static Supplier<Boolean> SUSAN_MANUAL_ONE;
    public static Supplier<Boolean> SUSAN_MANUAL_TWO;
    public static Supplier<Boolean> SUSAN_MANUAL_THREE;

    public static Supplier<Float> SUSAN_ADJUST;
    public static Supplier<Double> SHOOT_TURRET;
    public static Supplier<Double> SPIN_TURRET_X;
    public static Supplier<Double> SPIN_TURRET_Y;

    public static Supplier<Boolean> TURRET_HOOD_LOW;
    public static Supplier<Boolean> TURRET_HOOD_HALF;
    public static Supplier<Boolean> TURRET_HOOD_HIGH;

    public static void initDriver(Gamepad gamepad1) {
        DRIVE_STRAFE = () -> gamepad1.left_stick_x;
        DRIVE_FB = () -> gamepad1.left_stick_y;
        DRIVE_ROTATE = () -> gamepad1.right_stick_x;
        DRIVE_SLOW_MODE = () -> gamepad1.b;
        DRIVE_SLOW_MODE = () -> gamepad1.a;
        INTAKE_IN = () -> gamepad1.right_trigger;
        INTAKE_OUT = () -> gamepad1.left_trigger;
    }

    public static void initOperator(Gamepad gamepad2) {
        SHOOT_TURRET  = () -> (double) gamepad2.right_trigger;
        SPIN_TURRET_X = () -> (double) gamepad2.right_stick_x;
        SPIN_TURRET_Y = () -> (double) gamepad2.right_stick_y;
        TURRET_HOOD_LOW = () -> (boolean) gamepad2.a;
        TURRET_HOOD_HALF = () -> (boolean) gamepad2.b;
        TURRET_HOOD_HIGH = () -> (boolean) gamepad2.x;
    }
}
