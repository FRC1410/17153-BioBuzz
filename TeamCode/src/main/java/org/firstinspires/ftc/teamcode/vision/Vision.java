package org.firstinspires.ftc.teamcode.vision;

import static org.firstinspires.ftc.teamcode.vision.VisionConstants.CAM_OFFSET_X;
import static org.firstinspires.ftc.teamcode.vision.VisionConstants.CAM_OFFSET_Y;
import static org.firstinspires.ftc.teamcode.vision.VisionConstants.TAG_WIDTH;
import static org.firstinspires.ftc.teamcode.vision.VisionConstants.TERMINAL_ANGLE_VECTOR;

import android.util.Size;

import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagLibrary;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagSingleDetection;

import java.util.ArrayList;

public class Vision {
    public VisionPortal vision_portal;
    public AprilTagProcessor april_tag;
    private ArrayList<AprilTagSingleDetection> detections;

    public Vision(HardwareMap hardwareMapCool) {
        // NOTE: The values/configs here are very finicky, don't change them unless absolutely necessary
        // comment form EonCuber28: ok 👍 :) (as of Mar 25 2026, i have changed some things due to a guide i found)
        try {
            VisionConstants.initMap();
            // this defines all the wanted keys for us
            AprilTagLibrary.Builder tagLib = new AprilTagLibrary.Builder();
            for (int tagID : new int[]{0}){
                tagLib.addTag(tagID, "TagID"+tagID, TAG_WIDTH, DistanceUnit.INCH);
            }
            // This is the camera data, and where we get said data.
            this.april_tag = new AprilTagProcessor.Builder()
                    .setTagLibrary(tagLib.build())
                    .setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11)
                    .setDrawAxes(true)
                    .setDrawCubeProjection(true)
                    .setDrawTagOutline(true)
                    .setDrawTagID(true)
                    .setOutputUnits(DistanceUnit.INCH, AngleUnit.RADIANS) // in radians to work better with java trig stuff
                    .build();
            // This is the connection to the physical camera, including the id
            VisionPortal.Builder vision_portal_builder = new VisionPortal.Builder();
            vision_portal_builder.setCamera(hardwareMapCool.get(WebcamName.class,"WEBCAM_ID")); // TODO: put in IDs class
            vision_portal_builder.setCameraResolution(new Size(640, 480));
            vision_portal_builder.addProcessor(april_tag);

            vision_portal = vision_portal_builder.build();
            update();
        } catch (Exception e) {
            throw new RuntimeException("Camera init failed: " + e.getMessage());
        }
    }

    private double[] shiftPosFromCamOffset(double[] ogPos){
        if (CAM_OFFSET_Y == 0 && CAM_OFFSET_X == 0) return ogPos.clone(); // idk just felt like it
        // this is the complicated part where we start our toes a little bit more into vector math (scary ik)
        // the first step is to calculate the robot facing vector, we just gotta rotate the zero vector by the robot angle
        double[] facingVect = {
                TERMINAL_ANGLE_VECTOR[0]*Math.cos(ogPos[2])- TERMINAL_ANGLE_VECTOR[1]*Math.sin(ogPos[2]),
                TERMINAL_ANGLE_VECTOR[0]*Math.sin(ogPos[2])+ TERMINAL_ANGLE_VECTOR[1]*Math.cos(ogPos[2])};
        double length = Math.sqrt(facingVect[0]*facingVect[0]+facingVect[1]*facingVect[1]); // simple pythagoras (not so scary)
        // we calculate F hat and P hat, as our robot local space matrix constructors, we just gonna normalise the vector just in case.
        double[] F_hat = {
                facingVect[0]/length,
                facingVect[1]/length};
        double[] P_hat = {
                -F_hat[1],
                F_hat[0]};
        // now we calculate the world based offset
        double[] worldOffset = {
                CAM_OFFSET_X*F_hat[0]+CAM_OFFSET_Y*P_hat[0],
                CAM_OFFSET_X*F_hat[1]+CAM_OFFSET_Y*P_hat[1]};
        // and apply the offset to a copy of input (better for adaptability)
        double[] out = ogPos.clone();
        out[0] += worldOffset[0];
        out[1] += worldOffset[1];
        return out;
    }

    public double[] getRobotPoseFromTag(AprilTagSingleDetection tag){
        return null; // TODO
    }

    public double[] getRobotPos(){
        return null; // TODO
    }
    public Pose getPedroPose(){
        double[] roboPos = getRobotPos();
        return new Pose(roboPos[0], roboPos[1], roboPos[2]);
    }

    public void update(){
        detections.clear();
        for (AprilTagDetection detection : april_tag.getDetections()){
            if (detection instanceof AprilTagSingleDetection){ // filter out cluster detections that have no IDs to ensure that we have only apriltags with IDs.
                detections.add((AprilTagSingleDetection)detection);
            }
        }
    }

    Pose lastPose = null;
    public boolean hasNewPos(){
        Pose newPose = getPedroPose();
        if (lastPose == null){
            lastPose = newPose;
            return true;
        }
        // because PP pose objects don't have in-built equals, we manually check each value
        if (newPose.x() != lastPose.x() ||
                newPose.y() != lastPose.y() ||
                newPose.heading() != lastPose.heading()){
            lastPose = newPose;
            return true;
        }
        lastPose = newPose;
        return false;
    }
}
