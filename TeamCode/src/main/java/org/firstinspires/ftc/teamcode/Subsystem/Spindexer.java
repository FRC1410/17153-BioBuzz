package org.firstinspires.ftc.teamcode.Subsystem;

import static org.firstinspires.ftc.teamcode.Util.ID.SPINDEXER_ID;

import org.firstinspires.ftc.teamcode.Util.RobotStates.*;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
public class Spindexer {
    private Servo dexter;

    public void init(HardwareMap hardwareMap){
        this.dexter = hardwareMap.get(Servo.class, SPINDEXER_ID);
    }

}
