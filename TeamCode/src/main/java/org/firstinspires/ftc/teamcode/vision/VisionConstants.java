package org.firstinspires.ftc.teamcode.vision;

import java.util.HashMap;
import java.util.Map;

public class VisionConstants {
    // vision constants
    public static final double TAG_WIDTH = 3.25;
    public static final double CAM_OFFSET_X = 1.5;
    public static final double CAM_OFFSET_Y = 8;
    public static final double[] TERMINAL_ANGLE_VECTOR = {1,0}; // this is for vision, and the main way we make sure that our vision code and pedro talk nicely together, NO TOUCHY.

    public static final double armLength = 14.267;
    public static final double armNormal = 1.445;

    public static final double cameraPitch = 0; // in degrees


    public static final Map<Integer,TagPivotPoint> idPivotMap = new HashMap<>();

    public static void initMap(){
        // this is where we put all the pivot point data for all the tags
        // TODO: revise all of these points
        double pivotHeight = 43.95; // TODO: remeasure using actual field

        double redHiveX = 60;
        double redHiveY = 72;

        double blueHiveX = 84;
        double blueHiveY = 72;
        // RED
        idPivotMap.put(30, new TagPivotPoint(redHiveX-6.5,redHiveY,pivotHeight));
        idPivotMap.put(31, new TagPivotPoint(redHiveX-2.75,redHiveY,pivotHeight));
        idPivotMap.put(32, new TagPivotPoint(redHiveX+2.75,redHiveY,pivotHeight));
        idPivotMap.put(33, new TagPivotPoint(redHiveX+6.5,redHiveY,pivotHeight));
        // audience side
        idPivotMap.put(34, new TagPivotPoint(redHiveX-6.5,redHiveY,pivotHeight));
        idPivotMap.put(35, new TagPivotPoint(redHiveX-2.75,redHiveY,pivotHeight));
        idPivotMap.put(36, new TagPivotPoint(redHiveX+2.75,redHiveY,pivotHeight));
        idPivotMap.put(37, new TagPivotPoint(redHiveX+6.5,redHiveY,pivotHeight));
        // BLUE
        idPivotMap.put(38, new TagPivotPoint(blueHiveX-6.5,blueHiveY,pivotHeight));
        idPivotMap.put(39, new TagPivotPoint(blueHiveX-2.75,blueHiveY,pivotHeight));
        idPivotMap.put(40, new TagPivotPoint(blueHiveX+2.75,blueHiveY,pivotHeight));
        idPivotMap.put(41, new TagPivotPoint(blueHiveX+6.5,blueHiveY,pivotHeight));
        // audience side
        idPivotMap.put(42, new TagPivotPoint(blueHiveX-6.5,blueHiveY,pivotHeight));
        idPivotMap.put(43, new TagPivotPoint(blueHiveX-2.75,blueHiveY,pivotHeight));
        idPivotMap.put(44, new TagPivotPoint(blueHiveX+2.75,blueHiveY,pivotHeight));
        idPivotMap.put(45, new TagPivotPoint(blueHiveX+6.5,blueHiveY,pivotHeight));
    }
}

class TagPivotPoint{
    private double x;
    private double y;
    private double z;

    public TagPivotPoint(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double x(){
        return x;
    }
    public double y(){
        return y;
    }
    public double z(){
        return z;
    }
}