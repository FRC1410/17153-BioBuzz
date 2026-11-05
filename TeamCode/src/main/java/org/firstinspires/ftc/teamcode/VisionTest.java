package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.vision.Vision;
import org.firstinspires.ftc.teamcode.vision.VisionConstants;

@TeleOp(name="Vision test")
public class VisionTest extends OpMode{
    private Vision vision;
    @Override
    public void init() {
        VisionConstants.initConstants();
        vision = new Vision(hardwareMap);
    }

    double[] cameraPose = {-1,-1,-1};
    @Override
    public void loop() {
        if (vision.update()){
            cameraPose = vision.getCameraPose();
        }
        telemetry.addData("Camera X",cameraPose[0]);
        telemetry.addData("Camera Y",cameraPose[1]);
        telemetry.addData("Camera H",cameraPose[2]);
        telemetry.addData(" :- - - - -","");
        telemetry.addData("Red hive angle",vision.getRedHiveAngle());
        telemetry.addData("Blue hive angle",vision.getBlueHiveAngle());

        telemetry.update();
    }
}
