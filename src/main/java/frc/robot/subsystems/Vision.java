package frc.robot.subsystems;

import frc.robot.commands.*;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.apriltag.*;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonUtils;
import frc.robot.Constants.AprilTagConstants;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.Spark;

/**
 *
 */
public class Vision extends SubsystemBase {

    public final int NoTargetVisible = -99;
    private PhotonCamera aprilTagCamera1 = null;
    private boolean hasTargets = false;
    private int bestTargetID = NoTargetVisible;
    private String camName = "";
    private double tagYawDegrees = 0;
    private double tagPitchDegrees = 0;
    private double rangeFromTagMeters = 0;
    private Transform3d bestCamera2Target = null;
    private Transform3d emptyTransform = null;
    private Transform3d camToRobotTransform3d = null;
    /**
    *
    */
    public Vision() {

    }


    public Transform3d getCamToRobotTransform3d (){
        return camToRobotTransform3d;
    }
    public void init(String camName, Transform3d camToRobotTransform) {

        aprilTagCamera1 = new PhotonCamera(camName);
        camToRobotTransform3d = camToRobotTransform;
    }

    public double getTagYawDegrees() { // x error
        return tagYawDegrees;
    }

    public double getTagYawRadians() {
        return Math.toRadians(getTagYawDegrees());
    }

    public double getTagPitchDegrees() { // y error
        return tagPitchDegrees;
    }

    public double getTagPitchRadians() {
        return Math.toRadians(tagPitchDegrees);
    }

    public int getTagId() {
        return bestTargetID;
    }

    public double getRangeFromTagMeters() {
        double range = -99.0;
        /*
         * range = PhotonUtils.calculateDistanceToTargetMeters(
         * AprilTagConstants.cameraHeightMeters,
         * //AprilTagConstants.targetRangeMeters,
         * aprilTagCamera1.getLatestResult().getBestTarget().getBestCameraToTarget().
         * AprilTagConstants.cameraPitchRadians,
         * getTagPitchRadians());
         */
        return rangeFromTagMeters;
    }

    public boolean hasTargets () {
        return hasTargets;
    }
    @Override
    public void periodic() {
        // This method will be called once per scheduler run

        if (camName == "") {
            return;
        }

        var result = aprilTagCamera1.getLatestResult();
        hasTargets = result.hasTargets();

        if (hasTargets) {
            bestTargetID = result.getBestTarget().getFiducialId();
            rangeFromTagMeters = result.getBestTarget().getBestCameraToTarget().getX();
            tagPitchDegrees = result.getBestTarget().getPitch();
            tagYawDegrees = result.getBestTarget().getYaw();
            bestCamera2Target = result.getBestTarget().getBestCameraToTarget();
            
        } else {

            // clear things up... I cant see any tags

            hasTargets = false;
            bestTargetID = NoTargetVisible;
            rangeFromTagMeters = -99;
            tagPitchDegrees = -99;
            tagYawDegrees = -99;
            bestCamera2Target = emptyTransform;
        }

    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run when in simulation

    }

    public Transform3d getCamToTargetTransform3d() {
        return bestCamera2Target;
    }
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

}
