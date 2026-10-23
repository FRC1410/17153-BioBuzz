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
import com.qualcomm.robotcore.hardware.MotorControlAlgorithm;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;
public class Spindexer {
    private DcMotorEx dexter;
    private int pos;
    private int desiredPos;
private RobotStates.SpindexerStates dexterState;

    public void init(HardwareMap hardwareMap){
        this.dexter = hardwareMap.get(DcMotorEx.class, SPINDEXER_ID);
        this.dexter.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(DEXTER_P, DEXTER_I, DEXTER_D, 0.1, MotorControlAlgorithm.PIDF));
        this.dexter.setPositionPIDFCoefficients(DEXTER_P);
//        this.dexter.setZeroPowerBehavior();
    }
    public void run(){
        this.dexter.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        if(dexterState != SpindexerStates.NEUTRAL){
            dexterState = SpindexerStates.NEUTRAL;
        }else{
            dexterState = SpindexerStates.ROTATING;
        }
        switch (dexterState){
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
//        this.dexter.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        switch (state){
            case P1:
                System.out.println("1");
                this.dexter.setTargetPosition(0);
                desiredPos = this.dexter.getTargetPosition();
                this.dexter.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
                break;
            case P2:
                System.out.println("2");
                this.dexter.setTargetPosition(7);
                desiredPos = this.dexter.getTargetPosition();
                this.dexter.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
                break;
            case P3:
                System.out.println("3");
                this.dexter.setTargetPosition(14);
                desiredPos = this.dexter.getTargetPosition();
                this.dexter.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
                break;
            case P4:
                System.out.println("4");
                this.dexter.setTargetPosition(21);
                desiredPos = this.dexter.getTargetPosition();
                this.dexter.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
                break;
        }
            this.dexter.setPower(.9);
    }
    public void updatePos(){
        this.pos = this.dexter.getCurrentPosition();
        if(pos == desiredPos){
            this.dexter.setPower(0);
        }
    }
    public void addTelemetry(Telemetry telemetry) {
        telemetry.addData("State", this.dexterState);
        telemetry.addData("Busy", this.dexter.isBusy());
        telemetry.addData("POSITION", this.dexter.getCurrentPosition());
        telemetry.addData("DESIRED", this.dexter.getTargetPosition());
    }

}
