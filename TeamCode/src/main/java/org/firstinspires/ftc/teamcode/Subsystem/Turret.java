package org.firstinspires.ftc.teamcode.Subsystem;

import static org.firstinspires.ftc.teamcode.Util.Constants.Turret.*;
import static org.firstinspires.ftc.teamcode.Util.IDs.Turret.*;
import static org.firstinspires.ftc.teamcode.Util.RobotStates.Turret.TurretSpeeds.*;
import static org.firstinspires.ftc.teamcode.Util.Tuning.Turret.*;
import static org.firstinspires.ftc.teamcode.Util.RobotStates.Turret.*;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Util.PIDController;
import org.firstinspires.ftc.teamcode.Vision.Vision;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

public class Turret {
    private DcMotorEx spinMotor;
    private DcMotorEx shootMotor;
    private Servo hoodServo;
    private final PIDController spinController = new PIDController(TURRET_SPIN_P, TURRET_SPIN_I, TURRET_SPIN_D);
    private final PIDController aimController = new PIDController(TURRET_AIM_P, TURRET_AIM_I, TURRET_AIM_D);
    private double targetPos;

    private double correction = 0;

    private Vision vision;

    private Telemetry telemetry;

    public void init(HardwareMap hardwareMap, Vision vision, Telemetry _telemetry) {
        spinMotor = hardwareMap.get(DcMotorEx.class, TURRET_SPIN_MOTOR_ID);
        shootMotor = hardwareMap.get(DcMotorEx.class, TURRET_MOTOR_ID);

        spinMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        shootMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        spinMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        shootMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        spinMotor.setDirection(TURRET_SPIN_DIRECTION);
        shootMotor.setDirection(TURRET_DIRECTION);

        spinMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        shootMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        hoodServo = hardwareMap.get(Servo.class, HOOD_SERVO_ID);

        hoodServo.setDirection(HOOD_DIRECTION);

        this.vision = vision;

        telemetry = _telemetry;

        targetPos = 0;
    }

    /**
     * Set the speed of the turret spin
     * @param speed speed of turret in turret revolutions per second
     */
    public void spinTurret(double speed) {
        spinMotor.setVelocity(-speed * TICKS_PER_TURRET_REVOLUTION);
    }

    /**
     * Set the speed of the shooter spin
     * @param speed speed of shooter (1-0)
     */
    public void shootTurret(double speed) {
        shootMotor.setPower(speed);
        telemetry.addData("Target Shooter Speed: ", speed);
    }

    /**
     * Set the speed to the shooting motor
     * @param speed speed (FULL, HALF, or STOP)
     */
    public void shootTurret(TurretSpeeds speed) {
        switch (speed){
            case FULL -> shootMotor.setPower(1);
            case HALF -> shootMotor.setPower(0.5);
            case STOP -> shootMotor.setPower(0);
        }
    }

    /**
     * Sets the target position for the turret
     * @param _targetPos the target position, in turret revolutions
     */
    public void setTargetSpinPosition(double _targetPos) {
        targetPos = _targetPos;
    }

    /**
     * Gets the target position
     * @return target position in turret revolutions
     */
    public double getTargetPos() {
        return targetPos;
    }

    /**
     * Gets the position of the spinning motor
     * @return position of spinning motor in turret revolutions
     */
    public double getSpinPos() {
        return ((double) spinMotor.getCurrentPosition() / TICKS_PER_TURRET_REVOLUTION);
    }

    /**
     * Have the spin motor go to the set target position
     */
    public void goToTargetPos() {
        spinController.setIntegral(Math.min(spinController.getIntegral(), TICKS_PER_TURRET_REVOLUTION * 3));
        double calc = spinController.calculate(targetPos, spinMotor.getCurrentPosition() / TICKS_PER_TURRET_REVOLUTION);

        telemetry.addData("Calculation: ", calc);

        this.spinMotor.setPower(Math.max(-0.5, Math.min(0.5, -calc)));
    }

    /**
     * Sets the position for the servo to go to
     * @param state state for the hood to go to (HIGH, HALF, or LOW)
     */
    public void setHoodServoPos(HoodStates state) {
        switch (state) {
            case UP -> hoodServo.setPosition(1);
            case HALFWAY -> hoodServo.setPosition(0.5);
            case LOW  -> hoodServo.setPosition(0);
        }
    }

    /**
     * Sets the correction from drivetrain
     * @param correction the amount to correct by
     */
    public void setCorrection(double correction) {
        this.correction = correction;
    }

    /**
     * Runs a vision loop to target april tags
     */
    public void visionLoop(){
        AprilTagDetection detection = vision.getDetection();
        if (detection == null) {
            telemetry.addData("Error", "Correcting by " + this.correction);
            setTargetSpinPosition((getSpinPos() + correction) % 1);
            return;
        } else if (detection.ftcPose == null) {
            telemetry.addData("Error", "Error");
            return;
        }
        double error = detection.ftcPose.bearing;
        telemetry.addData("Error", error);

        double calc = aimController.calculate(0, error);

        setTargetSpinPosition((getSpinPos() + calc) % 1);
    }
}