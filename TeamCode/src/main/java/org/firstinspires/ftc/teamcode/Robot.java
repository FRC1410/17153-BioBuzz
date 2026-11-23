package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystem.Drivetrain;
import org.firstinspires.ftc.teamcode.Subsystem.Intake;
import org.firstinspires.ftc.teamcode.Util.ControlScheme;
import org.firstinspires.ftc.teamcode.Util.Toggle;

@TeleOp
public class Robot extends OpMode {
    private final Drivetrain drivetrain = new Drivetrain();

    private final Intake intake = new Intake();

    public void init() {
        ControlScheme.initDriver(gamepad1);
        ControlScheme.initOperator(gamepad2);
        this.drivetrain.init(hardwareMap);

        this.intake.init(hardwareMap);
    }

    public void doTelemetry() {
        this.drivetrain.drivetrainData(telemetry);
        telemetry.update();
    }

    @Override
    public void loop() {
        this.drivetrain.mechanumDrive(
                ControlScheme.DRIVE_STRAFE.get(),
                ControlScheme.DRIVE_FB.get(),
                ControlScheme.DRIVE_ROTATE.get(),
                ControlScheme.DRIVE_SLOW_MODE.get()
        );

        this.intake.runIntake(ControlScheme.INTAKE_IN.get() - ControlScheme.INTAKE_OUT.get());
    }
}