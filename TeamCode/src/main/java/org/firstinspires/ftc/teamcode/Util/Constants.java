package org.firstinspires.ftc.teamcode.Util;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

public class Constants {
    public interface Turret{
        public static final Servo.Direction HOOD_DIRECTION = Servo.Direction.FORWARD;
        public static final DcMotorSimple.Direction TURRET_DIRECTION = DcMotorSimple.Direction.FORWARD;
        public static final DcMotorSimple.Direction TURRET_SPIN_DIRECTION = DcMotorSimple.Direction.FORWARD;

    }
}
