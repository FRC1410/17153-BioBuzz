package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.Util.ControlScheme.*;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystem.Spindexer;
import org.firstinspires.ftc.teamcode.Util.ControlScheme;
import org.firstinspires.ftc.teamcode.Util.RobotStates;

@TeleOp
public class Robot extends OpMode {
    private final Spindexer dexter = new Spindexer();

    @Override
    public void init() {
        ControlScheme.initDriver(gamepad1);
        ControlScheme.initOperator(gamepad2);
        this.dexter.init(hardwareMap);
    }

    @Override
    public void loop() {
        if(DEXTER_P1.get()){
            this.dexter.gotoPos(RobotStates.SpindexerStates.P1);
        }
        if(DEXTER_P2.get()){
            this.dexter.gotoPos(RobotStates.SpindexerStates.P2);
        }
        if(DEXTER_P3.get()){
            this.dexter.gotoPos(RobotStates.SpindexerStates.P3);
        }
        if(DEXTER_P4.get()){
            this.dexter.gotoPos(RobotStates.SpindexerStates.P4);
        }
        if(DEXTER_TOGGLE.get()){

        }
    }
}
