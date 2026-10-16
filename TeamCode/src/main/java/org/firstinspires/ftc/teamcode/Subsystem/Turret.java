package org.firstinspires.ftc.teamcode.Subsystem;

import static org.firstinspires.ftc.teamcode.Util.Constants.Turret.*;
import static org.firstinspires.ftc.teamcode.Util.IDs.Turret.*;

import com.pedropathing.controllers.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Turret {
    private DcMotorEx turretSpinMotor;
    private DcMotorEx turretMotor;
    private Servo hoodServo;
    private PIDController turretPIDController;
    private double targetSpinPosition;

    public void init(HardwareMap hardwareMap){
        this.hoodServo = hardwareMap.get(Servo.class, HOOD_SERVO_ID);
        this.hoodServo.setDirection(HOOD_DIRECTION);

        this.turretMotor = hardwareMap.get(DcMotorEx.class, TURRET_MOTOR_ID);
        this.turretSpinMotor = hardwareMap.get(DcMotorEx.class, TURRET_SPIN_MOTOR_ID);

        this.turretMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        this.turretSpinMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

        this.turretMotor.setDirection(TURRET_DIRECTION);
        this.turretSpinMotor.setDirection(TURRET_SPIN_DIRECTION);

        this.turretMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        this.turretSpinMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        this.turretMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.turretSpinMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void setSpinSpeed(double speed){
        turretSpinMotor.setVelocity(speed);
    }

    public double getSpinSpeed(){
        return turretSpinMotor.getVelocity();
    }

    public double getTargetSpinPosition(){
        return targetSpinPosition;
    }
     public void setTargetSpinPosition(double target){
        targetSpinPosition = target;
     }

     public double getSpinPosition(){
        return turretSpinMotor.getCurrentPosition();
     }

     public void goToTargetSpinPosition(){
        double PID = turretPIDController.calculate(targetSpinPosition, this.getSpinPosition());
        this.turretSpinMotor.setVelocity(PID);
     }

     public double getTurretSpeed(){
        return turretMotor.getVelocity();
     }

     public void setTurretSpeed(double speed){
        turretMotor.setVelocity(speed);
     }

     public void setHoodPosition(double pos){
        hoodServo.setPosition(pos);
     }




}
