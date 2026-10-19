package org.firstinspires.ftc.teamcode.Util;

import com.qualcomm.robotcore.hardware.Gamepad;
import java.util.function.Supplier;

public class ControlScheme {
    public Supplier<Float> DRIVE_STRAFE;
    public Supplier<Float> DRIVE_FB;
    public Supplier<Float> DRIVE_ROTATE;
    public Supplier<Boolean> DRIVE_SLOW_MODE;

    public Supplier<Double> SHOOT_TURRET;
    public Supplier<Double> SPIN_TURRET_X;
    public Supplier<Double> SPIN_TURRET_Y;

    public Supplier<Boolean> TURRET_HOOD_LOW;
    public Supplier<Boolean> TURRET_HOOD_HALF;
    public Supplier<Boolean> TURRET_HOOD_HIGH;

    public void initDriver(Gamepad gamepad1) {
        DRIVE_STRAFE = () -> gamepad1.left_stick_x;
        DRIVE_FB = () -> gamepad1.left_stick_y;
        DRIVE_ROTATE = () -> gamepad1.right_stick_x;
        DRIVE_SLOW_MODE = () -> gamepad1.b;
    }

    public void initOperator(Gamepad gamepad2) {
        SHOOT_TURRET  = () -> (double) gamepad2.right_trigger;
        SPIN_TURRET_X = () -> (double) gamepad2.right_stick_x;
        SPIN_TURRET_Y = () -> (double) gamepad2.right_stick_y;
        TURRET_HOOD_LOW = () -> (boolean) gamepad2.a;
        TURRET_HOOD_HALF = () -> (boolean) gamepad2.b;
        TURRET_HOOD_HIGH = () -> (boolean) gamepad2.x;
    }

}
