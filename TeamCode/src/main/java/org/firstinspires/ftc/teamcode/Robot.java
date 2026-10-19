package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystem.Turret;
import org.firstinspires.ftc.teamcode.Util.ControlScheme;


@TeleOp
public class Robot extends OpMode {
    private final ControlScheme controlScheme = new ControlScheme();

    private final Turret turret = new Turret();

    @Override
    public void init() {
        controlScheme.initDriver(gamepad1);
        turret.init(hardwareMap);

    }

    @Override
    public void loop() {
        this.turret.setTargetSpinPosition(Math.atan2(controlScheme.SPIN_TURRET_Y.get(), controlScheme.SPIN_TURRET_X.get()) / 360);
    }
}
