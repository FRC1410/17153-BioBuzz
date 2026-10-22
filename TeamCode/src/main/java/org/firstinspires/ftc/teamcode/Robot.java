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
    public void doTelemetry() {
//        this.hoodServo.hoodTelem(telemetry);
//        this.shooter.addTelemetry(telemetry);
        this.dexter.addTelemetry(telemetry);
//        this.drivetrain.drivetrainData(telemetry);
        telemetry.update();
    }

    @Override
    public void loop() {
        if(DEXTER_P1.get()){
            System.out.println("HI");
            this.dexter.gotoPos(RobotStates.SpindexerStates.P1);
        }
        if(DEXTER_P2.get()){
            System.out.println("HEWO");
            this.dexter.gotoPos(RobotStates.SpindexerStates.P2);
        }
        if(DEXTER_P3.get()){
            System.out.println("WEEEE");
            this.dexter.gotoPos(RobotStates.SpindexerStates.P3);
        }
        if(DEXTER_P4.get()){
            System.out.println("WA");
            this.dexter.gotoPos(RobotStates.SpindexerStates.P4);
        }
        if(DEXTER_TOGGLE.get()){
            this.dexter.run();
        }
        doTelemetry();
    }
}
