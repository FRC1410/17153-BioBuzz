package org.firstinspires.ftc.teamcode.dynamite;

import com.pedropathing.api.Paths;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.dynamite.DYNCore.CommandException;
import org.firstinspires.ftc.teamcode.dynamite.DYNCore.commands.Command;
import org.firstinspires.ftc.teamcode.dynamite.DYNCore.variables.Variable;
import org.firstinspires.ftc.teamcode.dynamite.FTCInterface.FTCInterface;
import org.firstinspires.ftc.teamcode.dynamite.FTCInterface.GeneralMovement;

import java.util.ArrayList;
import java.util.Arrays;

public class PPInterface implements FTCInterface {
    private final HardwareMap hardwareMap;
    private final Follower pather;
    private boolean hasStartPosBeenSet;
    private final boolean processInRad;

    public PPInterface(Follower pather, HardwareMap hardwareMap, boolean processInRad){
        this.processInRad = processInRad;
        this.hardwareMap = hardwareMap;
        this.pather = pather;
    }

    @Override
    public void setStartPos(int line, double[] pos) {
        if (!hasStartPosBeenSet) {
            hasStartPosBeenSet = true;
            if (pos.length == 2) {
                pather.setPose(new Pose(pos[0], pos[1]));
            } else if (pos.length == 3) {
                pather.setPose(new Pose(pos[0], pos[1], pos[2]));
            }
        } else {
            throw new CommandException(line,"SetStartPose","Cannot set start pos when its already been set!");
        }
    }

    @Override
    public void runGeneralMove(int line, GeneralMovement move) {
        if (hasStartPosBeenSet) {
            switch (move.type) {
                case Bezier -> {
                    // assemble points
                    Pose[] midPoints = new Pose[move.bezTarget.length-1];
                    Pose endPose;
                    if (move.bezTarget[move.bezTarget.length-1].length == 3){
                        endPose = new Pose(
                                move.bezTarget[move.bezTarget.length-1][0],
                                move.bezTarget[move.bezTarget.length-1][1],
                                move.bezTarget[move.bezTarget.length-1][2]);
                    } else {
                        endPose = new Pose(
                                move.bezTarget[move.bezTarget.length-1][0],
                                move.bezTarget[move.bezTarget.length-1][1]);
                    }
                    for (int i = 0; i < move.bezTarget.length-1; i++){
                        double[] givenPose = move.bezTarget[i];
                        if (givenPose.length == 3){
                            midPoints[i] = new Pose(
                                    givenPose[0],
                                    givenPose[1],
                                    givenPose[2]);
                        } else {
                            midPoints[i] = new Pose(
                                    givenPose[0],
                                    givenPose[1]);
                        }
                    }
                    // make this as close to PP interaction as possible
                    // use PP
                    Pose currentPose = getRobotPose();
                    // build into a PathChain
                    ArrayList<Pose> poseList = new ArrayList<>();
                    poseList.add(currentPose);
                    poseList.addAll(Arrays.asList(midPoints));
                    poseList.add(endPose);
                    // do deg->rad processing
                    if (!processInRad) {
                        for (int i = 0; i < poseList.size(); i++) {
                            // convert to rad, because that's what PP uses
                            Pose oldPose = poseList.get(i);
                            double poseAngle = Math.toRadians(oldPose.heading());
                            poseList.set(i, new Pose(oldPose.x(), oldPose.y(), poseAngle));
                        }
                    }
                    Path plannedpath = Paths.curve(poseList.toArray(new Pose[0])).linear(currentPose.heading(),endPose.heading());
                    pather.follow(plannedpath);
                }
                case TurnTo -> {
                    Pose current = getRobotPose();
                    Pose target = new Pose(current.x(),current.y(),move.heading);
                    Path plannedPath = Paths.line(current,target).linear(current,target);
                    pather.follow(plannedPath);
                }
                case GoTo -> {
                    Pose endPose;
                    if (move.target.length == 3){
                        endPose = new Pose(
                                move.target[0],
                                move.target[1],
                                move.target[2]);
                    } else {
                        endPose = new Pose(
                                move.target[0],
                                move.target[1]);
                    }
                    if (!processInRad) endPose = new Pose(endPose.x(),endPose.y(),Math.toRadians(endPose.heading()));
                    Pose start = getRobotPose();;
                    Path plannedPath = Paths.line(start,endPose).linear(start,endPose);
                    submitRobotPath(plannedPath);
                }
                default -> throw new RuntimeException("Pedro Pathing does not support this kind of movement!");
            }
        } else {
            throw new CommandException(line,"Move","Cannot move robot until start pose has been set!");
        }
    }

    public volatile boolean requestProcessed = false;
    public final Object PPactionLock = new Object();
    public volatile boolean positionRequest = false;
    public volatile boolean pathRequest = false;
    public volatile boolean hasRequest = false;
    public volatile Path requestedPath = null;
    public volatile double robotX = -1;
    public volatile double robotY = -1;
    public volatile double robotH = -1;
    public Pose getRobotPose(){
        synchronized (PPactionLock){
            // reset to defaults
            robotX = -1;
            robotY = -1;
            robotH = -1;
            // submit request
            pathRequest = false;
            requestProcessed = false;
            positionRequest = true;
            hasRequest = true;
            while (!requestProcessed){
                try {
                    PPactionLock.wait();
                } catch (InterruptedException e){
                    throw new RuntimeException(e);
                }
            }
            return new Pose(robotX,robotY,robotH);
        }
    }
    public void submitRobotPath(Path calculatedPath){
        synchronized (PPactionLock){
            // set values
            requestedPath = calculatedPath;
            // request action
            requestProcessed = false;
            positionRequest = false;
            pathRequest = true;
            hasRequest = true;
            // wait for PP to finish move
            while (!requestProcessed){
                try {
                    PPactionLock.wait();
                } catch (InterruptedException e){
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public volatile boolean requested = false;
    public volatile boolean jFuncProcessed = false;
    public final Object jFuncLock = new Object(); // bc ofc Android Studio says it should be final, not volatile
    public volatile String funcID = null;
    public volatile int ranLine;
    public volatile boolean wantOutput = false;
    public volatile Variable inVar = null;
    public volatile Variable outVar = null;
    @Override
    public Variable runJFunc(int line, boolean wantOutput, String ID) {
        // ensure that only one thread is actually using the related variables
        synchronized (jFuncLock){
            // set stuff
            funcID = ID;
            ranLine = line;
            this.wantOutput = wantOutput;
            inVar = null;
            outVar = null;
            // request processing
            jFuncProcessed = false;
            requested = true;
            // wait for lock release (aka: the function was run by the main thread)
            while (!jFuncProcessed) {
                try {
                    jFuncLock.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            return outVar;
        }
    }
    @Override
    public Variable runJFunc(int line, boolean wantOutput, String ID, Variable in) {
        synchronized (jFuncLock){
            // set stuff
            funcID = ID;
            ranLine = line;
            this.wantOutput = wantOutput;
            inVar = in;
            outVar = null;
            // request processing
            jFuncProcessed = false;
            requested = true;
            // wait for lock release
            while (!jFuncProcessed) {
                try {
                    jFuncLock.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            return outVar;
        }
    }

    @Override
    public void addData(String data) {
        // routed through Command's buffer: these are called from the DYN thread, and the
        // SDK Telemetry object may only be touched from the OpMode loop thread.
        Command.pushTelemLine(data);
    }

    @Override
    public void update() {
        Command.updateTelem();
    }

    @Override
    public HardwareMap getHardwareMap() {
        return hardwareMap;
    }

    private Thread DYNThread;
    @Override
    public void DYNSleep(long milliseconds){
        try {
            DYNThread.sleep(milliseconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void setDYNThread(Thread DYNThread){
        this.DYNThread = DYNThread;
    }
}