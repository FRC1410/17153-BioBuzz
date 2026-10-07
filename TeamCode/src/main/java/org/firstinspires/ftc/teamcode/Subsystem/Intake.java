package org.firstinspires.ftc.teamcode.Subsystem;

import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.FORWARD;
import static org.firstinspires.ftc.teamcode.Util.IDs.INTAKE_MOTOR_ID;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {
    private DcMotorEx intake1;

    public void init(HardwareMap hardwareMap) {
        this.intake1 = hardwareMap.get(DcMotorEx.class, INTAKE_MOTOR_ID);

        this.intake1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        this.intake1.setDirection(FORWARD);

        this.intake1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public void runIntake(double motorSpeed) {
        this.intake1.setVelocity(motorSpeed * 3000);
    }

}
