package org.firstinspires.ftc.teamcode.Util;

public class RobotStates {
    public enum Drivetrain {
        FULL_SPEED,
        HALF_SPEED
    }

    public interface Turret{
        enum TurretSpeeds {
            FULL,
            HALF,
            STOP,
        }
        enum HoodStates {
            UP,
            HALFWAY,
            LOW,
        }
    }
}
