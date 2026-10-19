package org.firstinspires.ftc.teamcode.Subsystem;

import static org.firstinspires.ftc.teamcode.Util.ID.SPINDEXER_ID;
import static org.firstinspires.ftc.teamcode.Util.Tuning.*;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Util.RobotStates;
import org.firstinspires.ftc.teamcode.Util.RobotStates.SpindexerStates;

import com.pedropathing.controllers.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;
public class Spindexer {
    private DcMotorEx dexter;
private RobotStates.SpindexerStates dexterState;

    public void init(HardwareMap hardwareMap){
        this.dexter = hardwareMap.get(DcMotorEx.class, SPINDEXER_ID);
        this.dexter.setPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION, new PIDFCoefficients(DEXTER_P, DEXTER_I, DEXTER_D, 0));
    }
    public void run(RobotStates.SpindexerStates state){
        this.dexterState = state;
        this.dexter.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        if(state == SpindexerStates.NEUTRAL){
            state = SpindexerStates.ROTATING;
        }else{
            state = SpindexerStates.NEUTRAL;
        }
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
        this.dexterState = state;
        switch (state){
            case P1:
                System.out.println("1");
                this.dexter.setTargetPosition(0);
                this.dexter.setPower(.9);
                this.dexter.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                this.dexter.setPower(0);
                break;
            case P2:
                System.out.println("2");
                this.dexter.setTargetPosition(1);
                this.dexter.setPower(.9);
                this.dexter.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                this.dexter.setPower(0);
                break;
            case P3:
                System.out.println("3");
                this.dexter.setTargetPosition(2);
                this.dexter.setPower(.9);
                this.dexter.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                this.dexter.setPower(0);
                break;
            case P4:
                System.out.println("4");
                this.dexter.setTargetPosition(3);
                this.dexter.setPower(.9);
                this.dexter.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                this.dexter.setPower(0);
                break;
            default:
                System.out.println("KFC");
        }
    }
    public void addTelemetry(Telemetry telemetry) {
        telemetry.addData("State", this.dexterState);
        telemetry.addData("Busy", this.dexter.isBusy());
    }

}
