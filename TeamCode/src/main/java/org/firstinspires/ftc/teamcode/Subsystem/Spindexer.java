package org.firstinspires.ftc.teamcode.Subsystem;

import static org.firstinspires.ftc.teamcode.Util.ID.SPINDEXER_ID;

import org.firstinspires.ftc.teamcode.Util.RobotStates;
import org.firstinspires.ftc.teamcode.Util.RobotStates.SpindexerStates;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
public class Spindexer {
    private DcMotorEx dexter;
    private RobotStates.SpindexerStates dexerState = SpindexerStates.NEUTRAL;

    public void init(HardwareMap hardwareMap){
        this.dexter = hardwareMap.get(DcMotorEx.class, SPINDEXER_ID);
    }
    public void run(RobotStates.SpindexerStates state){
        switch (state){
            case NEUTRAL:
                this.dexter.setPower(0);
                break;
            case ROTATING:
                this.dexter.setPower(.9);
                break;
        }
    }
    public void gotoPos(RobotStates.SpindexerStates state){
        switch (state){
            case P1:
                this.dexter.setPower(1);
                break;
            case P2:
                this.dexter.setPower(1);
                break;
            case P3:
                this.dexter.setPower(1);
                break;
            case P4:
                this.dexter.setPower(1);
                break;    
        }
    }

}
