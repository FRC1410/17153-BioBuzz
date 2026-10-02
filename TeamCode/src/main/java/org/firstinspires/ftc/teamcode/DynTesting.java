package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.dynamite.DynOpMode;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Disabled
@TeleOp(name="DYN Testing")
public class DynTesting extends DynOpMode{
    @Override
    public boolean loadFromUSB() {
        return true;
    }
    @Override
    public String getScriptPath() {
        return "Main.dyn";
    }

    Follower pedroFollowsYou;
    @Override
    public Follower buildFollower() {
        pedroFollowsYou = Constants.create(hardwareMap);
        return pedroFollowsYou;
    }

    @Override
    public void onInit() {

    }

    @Override
    public void onLoop() {

    }
    
}