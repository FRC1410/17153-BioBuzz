package org.firstinspires.ftc.teamcode.Util;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

public class Constants {
    public static final double GEAR_RATIO = 1 / 18.88;
    public static final double WHEEL_RADIUS = 1.5; // In inches
    public static final double WHEEL_CIRCUMFERENCE = 2 * Math.PI * WHEEL_RADIUS; // In inches
    public static final double TRACK_WIDTH = 15.238; // In inches
    public static final double ROBOT_WEIGHT = 20; // In pounds
    public static final double TICKS_PER_REVOLUTION = 28; // Encoder count per revolution
    public static final double ENCODER_COUNT_PER_WHEEL_REVOLUTION = TICKS_PER_REVOLUTION * GEAR_RATIO;
    public static final double ENCODER_COUNT_PER_INCH = (TICKS_PER_REVOLUTION * GEAR_RATIO) / WHEEL_CIRCUMFERENCE;
    public static final double MAX_RPM = 6000; // Revolutions per minute
    public static final double MAX_MOTOR_WHEEL_VELOCITY =
            (MAX_RPM / 60) * GEAR_RATIO * WHEEL_CIRCUMFERENCE;
    public interface Turret{
        public static final Servo.Direction HOOD_DIRECTION = Servo.Direction.FORWARD;
        public static final DcMotorSimple.Direction TURRET_DIRECTION = DcMotorSimple.Direction.FORWARD;
        public static final DcMotorSimple.Direction TURRET_SPIN_DIRECTION = DcMotorSimple.Direction.FORWARD;
        public static final double TICKS_PER_THROUGH_BORE_REVOLUTION = 8192;
        public static final double TICKS_PER_TURRET_REVOLUTION = TICKS_PER_THROUGH_BORE_REVOLUTION * 5;
        public static final double TURRET_MAX_SPEED = TICKS_PER_THROUGH_BORE_REVOLUTION * 40;


    }
    public static final RevHubOrientationOnRobot.LogoFacingDirection LOGO_FACING_DIRECTION =
            RevHubOrientationOnRobot.LogoFacingDirection.UP;
    public static final RevHubOrientationOnRobot.UsbFacingDirection USB_FACING_DIRECTION =
            RevHubOrientationOnRobot.UsbFacingDirection.LEFT;
    public static RevHubOrientationOnRobot HUB_ORIENTATION =
            new RevHubOrientationOnRobot(LOGO_FACING_DIRECTION, USB_FACING_DIRECTION);
}


