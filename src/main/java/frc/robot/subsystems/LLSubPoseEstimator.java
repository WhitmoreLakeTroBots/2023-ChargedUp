package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMath;
//import edu.wpi.first.apriltag.*;

import java.io.IOException;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

/**
 *
 */
public class LLSubPoseEstimator extends SubsystemBase {
    private double currTime = 0.0;
    private double lastPrintTime = 0.0;
    Pose3d robotFieldPose = null;
    public static NetworkTableInstance inst = null;
    public NetworkTable llTable = null;
    public String limelight_back = "limelight-back";
    public NetworkTableEntry botpose = null;
    public NetworkTableEntry botpose_wpiblue = null;
    public NetworkTableEntry botpose_wpired = null;
    public NetworkTableEntry camerapose_targetspace = null;
    public NetworkTableEntry targetpose_cameraspace = null;
    public NetworkTableEntry targetpose_robotspace = null;
    public NetworkTableEntry botpose_targetspace = null;
    public NetworkTableEntry camerapose_robotspace = null;
    public NetworkTableEntry tid = null;
    public NetworkTableEntry tv = null;
    public double [] NullTransform = {-99.0, -99.0, -99.0, 0.0, 0.0, 0.0};
    public final String strPose = "x= %3.2f y=%3.2 z=%3.2";


    /*  botpose	Robot transform in field-space. Translation (X,Y,Z) Rotation(Roll,Pitch,Yaw), total latency (cl+tl)
botpose_wpiblue	Robot transform in field-space (blue driverstation WPILIB origin). Translation (X,Y,Z) Rotation(Roll,Pitch,Yaw), total latency (cl+tl)
botpose_wpired	Robot transform in field-space (red driverstation WPILIB origin). Translation (X,Y,Z) Rotation(Roll,Pitch,Yaw), total latency (cl+tl)
camerapose_targetspace	3D transform of the camera in the coordinate system of the primary in-view AprilTag (array (6))
targetpose_cameraspace	3D transform of the primary in-view AprilTag in the coordinate system of the Camera (array (6))
targetpose_robotspace	3D transform of the primary in-view AprilTag in the coordinate system of the Robot (array (6))
botpose_targetspace	3D transform of the robot in the coordinate system of the primary in-view AprilTag (array (6))
camerapose_robotspace	3D transform of the camera in the coordinate system of the robot (array (6))
tid
*/

    // default camera position
    private final Transform3d cam11_2_robotTransform3d = new Transform3d(new Translation3d(0, 0, .45),
            new Rotation3d(Math.toRadians(0), Math.toRadians(0), Math.toRadians(9)));

    public int grid = 0;
    public int column = 0;

    public double diffX = 0;
    public double diffY = 0;

    public double calcDiffX;
    public double calcDiffY;

    // public List<PhotonTrackedTarget> tags;

    public LLSubPoseEstimator() {

        inst = NetworkTableInstance.getDefault();

        llTable = inst.getTable("limelight-back");
        tid = llTable.getEntry("tid");
        targetpose_cameraspace = llTable.getEntry("targetpose_cameraspace");
        //targetpose_robotspace = llTable.getEntry("targetpose_robotspace");


    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        getLimeLightData();
    }
    public void getLimeLightData() {

         botpose = llTable.getEntry("botpose");

         /*botpose_wpiblue = llTable.getEntry("botpose_wpiblue");
         botpose_wpired = llTable.getEntry("botpose_wpired");
         camerapose_targetspace = llTable.getEntry("camerapose_targetspace");
         targetpose_cameraspace = llTable.getEntry("targetpose_cameraspace");
                  botpose_targetspace = llTable.getEntry("botpose_targetspace");
         camerapose_robotspace = llTable.getEntry("camerapose_robotspace");


        //If Limelight has a target insight
         tv = llTable.getEntry("tv"); 	//Whether the limelight has any valid targets (0 or 1)

        currTime = RobotMath.getTime();
        if ((currTime - lastPrintTime ) > 1.0) {
            lastPrintTime = currTime;
            //System.err.println (formatPose(camerapose_targetspace.getDoubleArray(NullTransform)));
        }
*/
    }
    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run when in simulation

    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public int getFiducialId() {
        return (int)tid.getInteger(-99);
    }

    public String getCamPoseString (){
        return formatPose(camerapose_targetspace.getDoubleArray(NullTransform));
    }
    public String formatPose (double [] data){
        return ("hello !:)");
        //return String.format (strPose, data[0], data[1], data[2]);

    }
    public double getCameraX() {
        return targetpose_cameraspace.getDoubleArray(NullTransform)[0];

    }

    public double getCameraY() {
        return targetpose_cameraspace.getDoubleArray(NullTransform)[1];
    }

    public double getCameraZ() {
        return targetpose_cameraspace.getDoubleArray(NullTransform)[2];
    }

    public double getFieldX() {
        return botpose.getDoubleArray(NullTransform)[0];
    }

    public double getFieldY() {
        return botpose.getDoubleArray(NullTransform)[1];
    }

    public double getFieldZ() {
        return botpose.getDoubleArray(NullTransform)[3];
    }

    public double getFieldRollRad() {
        return botpose.getDoubleArray(NullTransform)[5];
    }

    public double getFieldYawRad() {
        return botpose.getDoubleArray(NullTransform)[6];
    }

    public double getFieldPitchRad() {
        return botpose.getDoubleArray(NullTransform)[4];
    }


    public double getDistanceFromTagInInches (int tagID) {
        return this.getDistanceFromTagInInches();
    }

    public double getDistanceFromTagInInches(){
        double distance = -99.0;

        try{
             //if (tv.getInteger(0) == 1){
                //We have a value to work with
                distance = targetpose_cameraspace.getDoubleArray(NullTransform)[2];
            // }

            if(distance != -99.0){
                return RobotMath.metersToInches(distance);
            }
            else{return -99;}
        }
        catch(Exception e){return -99;}
    }

    public Pose3d getRobotFieldPose() {
        robotFieldPose = new Pose3d(
            botpose.getDoubleArray(NullTransform)[0],
            botpose.getDoubleArray(NullTransform)[1],
            botpose.getDoubleArray(NullTransform)[2],
            new Rotation3d(0.0, 0.0, 0.0)
            );

        return robotFieldPose;
    }

    public enum targetPoses {

        NULLPOSE("Null pose", -99, -99, new Pose3d(new Translation3d(-99, -99, -99), new Rotation3d(0.0, 0.0, 0.0))),
        TAGID1("April Tag 1", 1, 1, new Pose3d(new Translation3d(14.4, 1.70, 0.5), new Rotation3d(0.0, 0.0, 0.0))),

        BLUE_GAME_PIECE_1("blue game 1", 1, 1, new Pose3d(new Translation3d(7.061, 0.914, 0.0), new Rotation3d(0.0, 0.0, 0.0))),
        BLUE_GAME_PIECE_2("blue game 2", 1, 1, new Pose3d(new Translation3d(7.061, 2.134, 0.0), new Rotation3d(0.0, 0.0, 0.0))),
        BLUE_GAME_PIECE_3("blue game 3", 1, 1, new Pose3d(new Translation3d(7.061, 3.404, 0.0), new Rotation3d(0.0, 0.0, 0.0))),
        BLUE_GAME_PIECE_4("blue game 4", 1, 1, new Pose3d(new Translation3d(7.061, 4.572, 0.0), new Rotation3d(0.0, 0.0, 0.0))),

        RED_GAME_PIECE_1("red game 1", 1, 1, new Pose3d(new Translation3d(9.448, 0.914, 0.0), new Rotation3d(0.0, 0.0, 0.0))),
        RED_GAME_PIECE_2("red game 2", 1, 1, new Pose3d(new Translation3d(9.448, 2.134, 0.0), new Rotation3d(0.0, 0.0, 0.0))),
        RED_GAME_PIECE_3("red game 3", 1, 1, new Pose3d(new Translation3d(9.448, 3.404, 0.0), new Rotation3d(0.0, 0.0, 0.0))),
        RED_GAME_PIECE_4("red game 4", 1, 1, new Pose3d(new Translation3d(9.448, 4.572, 0.0), new Rotation3d(0.0, 0.0, 0.0)));


        private final String name;
        private final Pose3d pose;
        private final int grid;
        private final int column;

        public Pose3d getPose() {
            return pose;
        }

        public String getName() {
            return name;
        }

        public Pose3d getPoseByPos(int grid, int column) {
            return pose;

        }

        targetPoses(String name, int grid, int column, Pose3d pose) {
            this.name = name;
            this.pose = pose;
            this.grid = grid;
            this.column = column;
        }
    }
}
