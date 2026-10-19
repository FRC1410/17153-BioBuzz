package org.firstinspires.ftc.teamcode.Util;

public class RobotStates {
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
