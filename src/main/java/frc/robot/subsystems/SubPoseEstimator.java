package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
//import edu.wpi.first.apriltag.*;

import java.io.IOException;

import org.photonvision.PhotonCamera;
import org.photonvision.PhotonUtils;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;

import java.util.Optional;

/**
 *
 */
public class SubPoseEstimator extends SubsystemBase {
    Pose3d robotFieldPose = null;
    private final Pose3d nullPose = new Pose3d(new Translation3d(-99, -99, -99), new Rotation3d(0.0, 0.0, 0.0));
    private final PhotonCamera cam11 = new PhotonCamera("photon12");

    // default camera position
    private final Transform3d cam11_2_robotTransform3d = new Transform3d(new Translation3d(0, 0, .45),
            new Rotation3d(Math.toRadians(87.3), Math.toRadians(0), Math.toRadians(9)));
    // The parameter for loadFromResource() will be different depending on the game.
    private AprilTagFieldLayout aprilTagFieldLayout = null;

    private double m_cam11_x = 0.0;
    private double m_cam11_y = 0.0;
    private double m_cam11_z = 0.0;

    private int m_tag_ID = 0;
    private double m_field_x = 0.0;
    private double m_field_y = 0.0;
    private double m_field_z = 0.0;

    private double m_field_rollRad = 0.0;
    private double m_field_yawRad = 0.0;
    private double m_field_pitchRad = 0.0;

    private Boolean m_HasTargets = false;

    public int grid = 0;
    public int column = 0;

    public SubPoseEstimator() {

        try {
            aprilTagFieldLayout = AprilTagFieldLayout
                    .loadFromResource(AprilTagFields.k2023ChargedUp.m_resourceFile);
        } catch (IOException e) {
            System.err.println("Could not load april tag field layout");
            System.out.println(e);
        }

    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run

        var results = cam11.getLatestResult();

        if (results.hasTargets()) {
            m_tag_ID = results.getBestTarget().getFiducialId();
            Optional<Pose3d> bestTagPose = aprilTagFieldLayout.getTagPose(m_tag_ID);

            if (bestTagPose.isPresent()) {
                m_HasTargets = results.hasTargets();
                robotFieldPose = PhotonUtils.estimateFieldToRobotAprilTag(
                        results.getBestTarget().getBestCameraToTarget(),
                        bestTagPose.get(),
                        cam11_2_robotTransform3d);

                m_field_x = robotFieldPose.getX();
                m_field_y = robotFieldPose.getY();
                m_field_z = robotFieldPose.getZ();
                m_field_rollRad = robotFieldPose.getRotation().getX();
                m_field_yawRad = robotFieldPose.getRotation().getZ();
                m_field_pitchRad = robotFieldPose.getRotation().getY();

                m_cam11_x = results.getBestTarget().getBestCameraToTarget().getX();
                m_cam11_y = results.getBestTarget().getBestCameraToTarget().getY();
                m_cam11_z = results.getBestTarget().getBestCameraToTarget().getZ();

            } else {
                NullThePose();
            }
        } else {
            NullThePose();
        }
    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run when in simulation

    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    private void NullThePose() {
        robotFieldPose = nullPose;
        m_field_x = robotFieldPose.getX();
        m_field_y = robotFieldPose.getY();
        m_field_z = robotFieldPose.getZ();
        m_tag_ID = 0;

        m_field_rollRad = robotFieldPose.getRotation().getX();
        m_field_yawRad = robotFieldPose.getRotation().getZ();
        m_field_pitchRad = robotFieldPose.getRotation().getY();

        m_cam11_x = 0.0;
        m_cam11_y = 0.0;
        m_cam11_z = 0.0;
        m_HasTargets = false;
    }

    public Pose3d getRobotFieldPose() {
        return robotFieldPose;
    }

    public int getFiducialId() {
        return m_tag_ID;
    }

    public double getCameraX() {
        return m_cam11_x;
    }

    public double getCameraY() {
        return m_cam11_y;
    }

    public double getCameraZ() {
        return m_cam11_z;
    }

    public double getFieldX() {
        return m_field_x;
    }

    public double getFieldY() {
        return m_field_y;
    }

    public double getFieldZ() {
        return m_field_z;
    }

    public double getFieldRollRad() {
        return m_field_rollRad;
    }

    public double getFieldYawRad() {
        return m_field_yawRad;
    }

    public double getFieldPitchRad() {
        return m_field_pitchRad;
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
