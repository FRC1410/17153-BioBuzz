package org.firstinspires.ftc.teamcode.vision;

import java.util.HashMap;
import java.util.Map;

import static org.firstinspires.ftc.teamcode.vision.TagPivotPoint.hiveType.*;

import org.ejml.simple.SimpleMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.MatrixF;

// this is the class where we do as much
// of the precalculated stuff as possible
// to reduce time during our already constrained
// op mode loop
public class VisionConstants {
    // this stuff is thread safe bc why not
    private static volatile boolean hasInited = false;
    public static synchronized boolean inited(){
        return hasInited;
    }
    // world constants Simple matrix can be a full 3x3 or a 3d vector. very usefull.
    public static final SimpleMatrix CV2FTC = new SimpleMatrix(new double[][]{
        {1, 0, 0}, // this matrix converts anything form
        {0, 0, 1}, // raw OpenCV data to FTC world data
        {0,-1, 0}});
    public static final SimpleMatrix CV2FTCinv = new SimpleMatrix(new double[][]{
        {1, 0, 0}, // this is the inverse of above
        {0, 0,-1},
        {0, 1, 0}});
    public static final SimpleMatrix e_1 = new SimpleMatrix(3,1,true, 1,0,0);
    public static final SimpleMatrix e_2 = new SimpleMatrix(3,1,true, 0,1,0);
    public static final SimpleMatrix e_3 = new SimpleMatrix(3,1,true, 0,0,1);

    public static SimpleMatrix camPitchRot;

    // known constants
    public static final double TAG_WIDTH = 3.25;
    public static final double CAM_OFFSET_X = 1.5;
    public static final double CAM_OFFSET_Y = 8;
    public static final double[] TERMINAL_ANGLE_VECTOR = {1,0}; // this is for vision, and the main way we make sure that our vision code and pedro talk nicely together, NO TOUCHY.

    public static final double armLength = 14.267;
    public static final double armNormal = 1.445;
    public static final SimpleMatrix armVector = new SimpleMatrix(3,1,true, 0,armNormal,armLength);

    public static final double cameraPitch = 45; // in degrees
    public static final double cameraPitchRad = Math.toRadians(cameraPitch);

    public static final Map<Integer,TagPivotPoint> idPivotMap = new HashMap<>();

    public static void initConstants(){
        camPitchRot = new SimpleMatrix(new double[][]{
        {1,                       0,                         0},
        {0, Math.cos(cameraPitchRad),-Math.sin(cameraPitchRad)},
        {0, Math.sin(cameraPitchRad), Math.cos(cameraPitchRad)}});
        // this is where we put all the pivot point data for all the tags
        // TODO: revise all of these points
        double pivotHeight = 43.95;

        double redHiveX = 60;
        double redHiveY = 72;

        double blueHiveX = 84;
        double blueHiveY = 72;
        // RED
        idPivotMap.put(30, new TagPivotPoint(RED,false,redHiveX-6.5,redHiveY,pivotHeight));
        idPivotMap.put(31, new TagPivotPoint(RED,false,redHiveX-2.75,redHiveY,pivotHeight));
        idPivotMap.put(32, new TagPivotPoint(RED,false,redHiveX+2.75,redHiveY,pivotHeight));
        idPivotMap.put(33, new TagPivotPoint(RED,false,redHiveX+6.5,redHiveY,pivotHeight));
        // audience side
        idPivotMap.put(34, new TagPivotPoint(RED,false,redHiveX-6.5,redHiveY,pivotHeight));
        idPivotMap.put(35, new TagPivotPoint(RED,false,redHiveX-2.75,redHiveY,pivotHeight));
        idPivotMap.put(36, new TagPivotPoint(RED,false,redHiveX+2.75,redHiveY,pivotHeight));
        idPivotMap.put(37, new TagPivotPoint(RED,false,redHiveX+6.5,redHiveY,pivotHeight));
        // BLUE
        idPivotMap.put(38, new TagPivotPoint(BLUE,false,blueHiveX-6.5,blueHiveY,pivotHeight));
        idPivotMap.put(39, new TagPivotPoint(BLUE,false,blueHiveX-2.75,blueHiveY,pivotHeight));
        idPivotMap.put(40, new TagPivotPoint(BLUE,false,blueHiveX+2.75,blueHiveY,pivotHeight));
        idPivotMap.put(41, new TagPivotPoint(BLUE,false,blueHiveX+6.5,blueHiveY,pivotHeight));
        // audience side
        idPivotMap.put(42, new TagPivotPoint(BLUE,false,blueHiveX-6.5,blueHiveY,pivotHeight));
        idPivotMap.put(43, new TagPivotPoint(BLUE,false,blueHiveX-2.75,blueHiveY,pivotHeight));
        idPivotMap.put(44, new TagPivotPoint(BLUE,false,blueHiveX+2.75,blueHiveY,pivotHeight));
        idPivotMap.put(45, new TagPivotPoint(BLUE,false,blueHiveX+6.5,blueHiveY,pivotHeight));
        hasInited = true;
    }
    // FTC to SimpleMatrix converter
    public static SimpleMatrix FTC2Simple(MatrixF in){
        SimpleMatrix out = new SimpleMatrix(in.numRows(),in.numCols());
        for (int r = 0; r < in.numRows(); r++){
            for (int c = 0; c < in.numCols(); c++){
                out.set(r,c, in.get(r,c));
            }
        }
        return out;
    }
}
