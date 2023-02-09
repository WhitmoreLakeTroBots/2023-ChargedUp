package frc.robot.subsystems;

import frc.robot.commands.*;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
//import edu.wpi.first.apriltag.*;

import java.io.IOException;

import org.photonvision.PhotonCamera;
import org.photonvision.PhotonUtils;
import frc.robot.Constants.AprilTagConstants;
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
    Pose3d robotPose = null;
    PhotonCamera cam11 = new PhotonCamera("photon11");
    Transform3d cam11_2_robotTransform3d = new Transform3d (new Translation3d(0,0,.45),new Rotation3d(0,0,0));
    // The parameter for loadFromResource() will be different depending on the game.
    AprilTagFieldLayout aprilTagFieldLayout = null;


    private double m_cam11_x = 0.0;
    private double m_cam11_y = 0.0;
    private double m_cam11_z = 0.0;

    private int m_tag_ID = 0;
    private double m_field_x = 0.0;
    private double m_field_y = 0.0;
    private double m_field_z = 0.0;


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
                robotPose = PhotonUtils.estimateFieldToRobotAprilTag(
                        results.getBestTarget().getBestCameraToTarget(),
                        bestTagPose.get(),
                        cam11_2_robotTransform3d);

                m_field_x = robotPose.getX();
                m_field_y = robotPose.getY();
                m_field_z = robotPose.getZ();
            } 
            m_cam11_x = results.getBestTarget().getBestCameraToTarget().getX();
            m_cam11_y = results.getBestTarget().getBestCameraToTarget().getY();
            m_cam11_z = results.getBestTarget().getBestCameraToTarget().getZ();
            
        }

    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run when in simulation

    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    

    private void NullThePose() {
        robotPose = null;

    }

    private Optional<Pose3d> getRobotPose() {
        if (robotPose == null) {
            return Optional.empty();
        } else {
            return Optional.of(robotPose);
        }
    }

    public int getFiducialId () {
        return m_tag_ID;
    }

    public double getCameraX() {
        if (robotPose == null) {
            return -99;
        } else {
            return m_cam11_x;
        }

    }

    public double getCameraY() {
        if (robotPose == null) {
            return -99;
        } else {
            return m_cam11_y;
        }

    }

    public double getCameraZ() {
        if (robotPose == null) {
            return -99;
        } else {
            return m_cam11_z;
        }
    }


    public double getFieldX (){
        return m_field_x;
    }

    public double getFieldY (){
        return m_field_y;
    }
    
    public double getFieldZ (){
        return m_field_z;
    }
}
