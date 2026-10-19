package org.firstinspires.ftc.teamcode.Util;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

public class Constants {
    public interface Turret{
        public static final Servo.Direction HOOD_DIRECTION = Servo.Direction.FORWARD;
        public static final DcMotorSimple.Direction TURRET_DIRECTION = DcMotorSimple.Direction.FORWARD;
        public static final DcMotorSimple.Direction TURRET_SPIN_DIRECTION = DcMotorSimple.Direction.FORWARD;
        public static final double TICKS_PER_THROUGH_BORE_REVOLUTION = 8192;
        public static final double TICKS_PER_TURRET_REVOLUTION = TICKS_PER_THROUGH_BORE_REVOLUTION * 5;
        public static final double TURRET_MAX_SPEED = TICKS_PER_THROUGH_BORE_REVOLUTION * 40;


    }
}
