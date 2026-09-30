package org.firstinspires.ftc.teamcode.vision;

import static org.firstinspires.ftc.teamcode.vision.VisionConstants.TAG_WIDTH;

import android.util.Size;

import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagLibrary;
import org.firstinspires.ftc.vision.apriltag.AprilTagPoseRaw;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagSingleDetection;

import org.ejml.simple.SimpleMatrix;

import java.util.ArrayList;

public class Vision {
    public VisionPortal vision_portal;
    public AprilTagProcessor april_tag;
    private ArrayList<AprilTagSingleDetection> detections;

    public Vision(HardwareMap hardwareMapCool) {
        // NOTE: The values/configs here are very finicky, don't change them unless absolutely necessary
        // comment form EonCuber28: ok 👍 :) (as of Mar 25 2026, i have changed some things due to a guide i found)
        try {
            VisionConstants.initConstants();
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

    private double blueHiveAngle = -1;
    private double redHiveAngle = -1;

    public double[] getRobotPoseFromTag(AprilTagSingleDetection tag){
        // TODO: simplify to raw arithmatic for efficiency
        // 0.Gather raw tag data
        TagPivotPoint tagPivot = VisionConstants.idPivotMap.get(tag.id);
        AprilTagPoseRaw tagRaw = tag.rawPose;
        SimpleMatrix t_cv = new SimpleMatrix(3,1,true, tagRaw.x,tagRaw.y,tagRaw.z);
        SimpleMatrix R_cv = VisionConstants.FTC2Simple(tagRaw.R);
        // 1.Convert raw taf data from openCV to FTC world
        //R = C*R_cv*C^T
        //t = C*t_cv
        SimpleMatrix R = VisionConstants.CV2FTC.mult(R_cv).mult(VisionConstants.CV2FTCinv);
        SimpleMatrix t = VisionConstants.CV2FTC.mult(t_cv);
        // 2.Find camera relative tag pivot
        //p = t+R*a
        SimpleMatrix p = t.plus(R.mult(VisionConstants.armVector));
        // 3.Level tag pivot to field plane using camera pitch and world vectors
        //r = L*R*e_1
        SimpleMatrix r = VisionConstants.camPitchRot.mult(R).mult(VisionConstants.e_1);
        // 4.Create transform to take anything form camera space and converts into pivot point space
        //Ψ = atan2(-r_y,r_x) (+π if tag is flipped)
        //R_cam->Piv = R_z(Ψ)*L
        double Ψ = Math.atan2(-r.get(1,0),r.get(0,0));
        if (tagPivot.flippedTag()) Ψ+=Math.PI; // if the tag is flipped, then we add pi to rotate it by 90deg
        double Ψsin = Math.sin(Ψ); // reduces redundant calculations
        double Ψcos = Math.cos(Ψ);
        SimpleMatrix R_zΨ = new SimpleMatrix(new double[][]{
            {Ψcos,-Ψsin, 0},
            {Ψsin, Ψcos, 0},
            {0,    0,    1}});
        SimpleMatrix R_cam2Piv = R_zΨ.mult(VisionConstants.camPitchRot);
        // 5.Find camera position
        //c = 0-R_cam->Piv*p
        //X = c_x+T_pivx
        //Y = c_y+T_pivy
        SimpleMatrix c = R_cam2Piv.mult(p).negative();
        double x = c.get(0,0)+tagPivot.x();
        double y = c.get(1,0)+tagPivot.y();
        // 6. get camera field heading and hive tilt
        //f = R_cam->Piv*e_2
        //q = R_cam->Piv*R*e_2
        //h = atan2(f_y,f_x) (cam field heading)
        //φ = atan2(q_z,√(q_x^2+q_y^2)) (that tags hive angle)
        SimpleMatrix f = R_cam2Piv.mult(VisionConstants.e_2);
        SimpleMatrix q = R_cam2Piv.mult(R).mult(VisionConstants.e_2);
        double h = Math.atan2(f.get(1,0),f.get(0,0));
        double φ = Math.atan2(q.get(2,0),Math.sqrt(
            Math.pow(q.get(0,0),2) + 
            Math.pow(q.get(0,1),2)));
        if (tagPivot.isOnBlueHive()) blueHiveAngle+=φ;
        else redHiveAngle+=φ;
        return new double[]{x,y,h};
    }

    public double[] getCameraPose(){
        blueHiveAngle = -1;
        redHiveAngle = -1;
        double[] poseSum = new double[3];
        double[] currentDeteciton;
        int detectionCount = detections.size();
        for (AprilTagSingleDetection tag : detections){
            currentDeteciton = getRobotPoseFromTag(tag);
            poseSum[0]+=currentDeteciton[0];
            poseSum[1]+=currentDeteciton[1];
            poseSum[2]+=currentDeteciton[2];
        }
        poseSum[0]/=detectionCount;
        poseSum[1]/=detectionCount;
        poseSum[2]/=detectionCount;
        if (blueHiveAngle != -1) blueHiveAngle/=detectionCount;
        if (redHiveAngle != -1) redHiveAngle/=detectionCount;
        return poseSum;
    }
    public double[] getRobotPose(double turretAngle){
        // TODO: redo cam->robot pose math
        double[] camPose = getCameraPose();
        double[] camTurretVec = {
            VisionConstants.turretRadius*Math.cos(turretAngle),
            VisionConstants.turretRadius*Math.sin(turretAngle)};
        double[] camToRobotVector = {
            -(VisionConstants.TurretCenterOffsetX+camTurretVec[0]),
            -(VisionConstants.TurretCenterOffsetY+camTurretVec[1])};
        double robotHeading = camPose[2]+turretAngle+VisionConstants.turretStartToRobotForwardDelta;
        // rotate camToRobotVector by full robot angle
        camToRobotVector[0] = camToRobotVector[0]*Math.cos(robotHeading) + camToRobotVector[1]*Math.sin(robotHeading);
        camToRobotVector[0] = camToRobotVector[0]*Math.sin(robotHeading) + camToRobotVector[1]*Math.cos(robotHeading);
        // compose output
        double[] out = {
            camPose[0]+camToRobotVector[0],
            camPose[1]+camToRobotVector[1],
            robotHeading};
        return out;
    }
    public Pose getPedroPose(double turretAngle){
        double[] roboPos = getRobotPose(turretAngle);
        return new Pose(roboPos[0], roboPos[1], roboPos[2]);
    }

    public boolean update(){
        // returns weather or not there have been any new detections since last update
        boolean hasNewDetections = !(april_tag.getFreshDetections()==null);
        detections.clear();
        for (AprilTagDetection detection : april_tag.getDetections()){
            if (detection instanceof AprilTagSingleDetection){ // filter out cluster detections that have no IDs to ensure that we have only apriltags with IDs.
                detections.add((AprilTagSingleDetection)detection);
            }
        }
        return hasNewDetections;
    }

    public double getRedHiveAngle(){
        return redHiveAngle;
    }
    public double getBlueHiveAngle(){
        return blueHiveAngle;
    }
}
