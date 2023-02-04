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
    Vision camera11 = null;
    // Vision camera12 = null;

    // The parameter for loadFromResource() will be different depending on the game.
    AprilTagFieldLayout aprilTagFieldLayout = null;

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

        if (camera11.hasTargets()) {
            Optional<Pose3d> bestTagPose = aprilTagFieldLayout.getTagPose(camera11.getTagId());

            if (bestTagPose.isPresent()) {
                robotPose = PhotonUtils.estimateFieldToRobotAprilTag(
                        camera11.getCamToTargetTransform3d(),
                        bestTagPose.get(),
                        camera11.getCamToRobotTransform3d());

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

    public void initiaizeCameras() {
        // create the vision cameras here.

        Vision camera11 = new Vision();
        // Vision camera12 = new Vision();
        // camera11.init("camera11",new Transform3d (.02,0,.45,new Rotation3d(0,0,0)));

        camera11.init("camera11", new Transform3d(new Translation3d(-0.20, 0.0, 0.45),
                new Rotation3d(0, 0, 0)));

    }

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

    public Optional<Double> getX() {
        if (robotPose == null) {
            return Optional.empty();
        } else {
            return Optional.of(robotPose.getX());
        }

    }

    public Optional<Double> getY() {
        if (robotPose == null) {
            return Optional.empty();
        } else {
            return Optional.of(robotPose.getY());
        }

    }

    public Optional<Double> getZ() {
        if (robotPose == null) {
            return Optional.empty();
        } else {
            return Optional.of(robotPose.getZ());
        }
    }

}
