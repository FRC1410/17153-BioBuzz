package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystem.Spindexer;
import org.firstinspires.ftc.teamcode.Util.ControlScheme;

@TeleOp
public class Robot extends OpMode {
    private final Spindexer dexter = new Spindexer();

    @Override
    public void init() {
        ControlScheme.initDriver(gamepad1);
        ControlScheme.initOperator(gamepad2);
        this.dexter.init(hardwareMap);
    }

    @Override
    public void loop() {

    }
}
