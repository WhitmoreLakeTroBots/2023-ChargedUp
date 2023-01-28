package frc.robot.subsystems;

import frc.robot.commands.*;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.apriltag.*;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonUtils;
import frc.robot.Constants.AprilTagConstants;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.Spark;

/**
 *
 */
public class Vision extends SubsystemBase {
    private final PhotonCamera aprilTagCamera1 = new PhotonCamera("IMX219");

    /**
    *
    */
    public Vision() {

    }

    public double getTagYawDegrees() { // x error
        if (aprilTagCamera1.getLatestResult().getBestTarget() != null) {
            return aprilTagCamera1.getLatestResult().getBestTarget().getYaw();
        } else {
            return -1;
        }
    }

    public double getTagYawRadians() {
        if (aprilTagCamera1.getLatestResult().getBestTarget() != null) {
            return Math.toRadians(getTagYawDegrees());
        } else {
            return -1;
        }
    }

    public double getTagPitchDegrees() { // y error
        if (aprilTagCamera1.getLatestResult().getBestTarget() != null) {
            return aprilTagCamera1.getLatestResult().getBestTarget().getPitch();
        } else {
            return -1;
        }
    }

    public double getTagPitchRadians() {
        if (aprilTagCamera1.getLatestResult().getBestTarget() != null) {
            return Math.toRadians(getTagPitchDegrees());
        } else {
            return -1;
        }
    }

    public int getTagId() {
        if (aprilTagCamera1.getLatestResult().getBestTarget() != null) {
            return aprilTagCamera1.getLatestResult().getBestTarget().getFiducialId();
        } else {
            return -1;
        }
    }

    public double getRangeFromTagMeters() {
        double range = 0;
        range = PhotonUtils.calculateDistanceToTargetMeters(
                AprilTagConstants.cameraHeightMeters,
                AprilTagConstants.targetRangeMeters,
                AprilTagConstants.cameraPitchRadians,
                getTagPitchRadians());
        return range;
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run

    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run when in simulation

    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

}
