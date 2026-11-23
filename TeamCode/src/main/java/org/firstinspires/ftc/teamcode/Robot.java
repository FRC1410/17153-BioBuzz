package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystem.Turret;
import org.firstinspires.ftc.teamcode.Util.ControlScheme;
import org.firstinspires.ftc.teamcode.Vision.Vision;


@TeleOp
public class Robot extends OpMode {
    private final ControlScheme controlScheme = new ControlScheme();

    private final Turret turret = new Turret();
    private Vision vision;

    @Override
    public void init() {
        controlScheme.initDriver(gamepad1);
        vision = new Vision(hardwareMap);
        turret.init(hardwareMap, vision, telemetry);
    }

    @Override
    public void loop() {
//        this.turret.setTargetSpinPosition(
//                (Math.atan2(controlScheme.SPIN_TURRET_Y.get(), controlScheme.SPIN_TURRET_X.get())+Math.PI)
//                /(2*Math.PI)); // bring into range of 0-1 to align with
//        this.turret.goToTargetPos();
        this.turret.visionLoop();
        this.turret.goToTargetPos();
    }
}
