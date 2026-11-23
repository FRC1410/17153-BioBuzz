package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystem.Turret;
import org.firstinspires.ftc.teamcode.Util.ControlScheme;
import org.firstinspires.ftc.teamcode.Vision.Vision;


import org.firstinspires.ftc.teamcode.Subsystem.Drivetrain;
import org.firstinspires.ftc.teamcode.Util.ControlScheme;
import org.firstinspires.ftc.teamcode.Util.Toggle;

@TeleOp
public class Robot extends OpMode {
    private final Drivetrain drivetrain = new Drivetrain();
    private final ControlScheme controlScheme = new ControlScheme();

    private final Turret turret = new Turret();
    private Vision vision;

    @Override
    public void init() {
        ControlScheme.initDriver(gamepad1);
        ControlScheme.initOperator(gamepad2);

        this.drivetrain.init(hardwareMap);
        this.vision = new Vision(hardwareMap);
        telemetry.update();
        turret.init(hardwareMap, vision, telemetry);
    }

    @Override
    public void loop() {
        this.drivetrain.mechanumDrive(
                ControlScheme.DRIVE_STRAFE.get(),
                ControlScheme.DRIVE_FB.get(),
                ControlScheme.DRIVE_ROTATE.get(),
                ControlScheme.DRIVE_SLOW_MODE.get()
        );
//        this.turret.setTargetSpinPosition(
//                (Math.atan2(controlScheme.SPIN_TURRET_Y.get(), controlScheme.SPIN_TURRET_X.get())+Math.PI)
//                /(2*Math.PI)); // bring into range of 0-1 to align with
//        this.turret.goToTargetPos();
        this.turret.visionLoop();
        this.turret.goToTargetPos();
    }
}