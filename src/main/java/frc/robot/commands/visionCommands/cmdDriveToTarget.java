package frc.robot.commands.visionCommands;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.RobotMath;
import frc.robot.subsystems.SubPoseEstimator;

/**
 *
 */
public class cmdDriveToTarget extends CommandBase {
    private Pose3d targetPose;
    private Pose3d diffPose;

    private double power = 0.15;
    private boolean bDone = false;

    public cmdDriveToTarget(SubPoseEstimator.targetPoses tPose, double speed) {

        targetPose = tPose.getPose();
        power = speed;
        // targetHeading =
        // RobotContainer.getInstance().m_subGyro.getNormaliziedNavxAngle();

        // m_subsystem = subsystem;
        // addRequirements(m_subsystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        bDone = false;
        RobotContainer.getInstance().m_driveTrain.resetEncoders();
        // Figure out initial drive based on pose.
        RobotContainer.getInstance().m_driveTrain.enableGoToPos();

    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        if (RobotContainer.getInstance().m_Estimator.getRobotFieldPose().getZ() > 0) {

            // converting to inches from pos value before entering drive train
            // Pos may be reversed, currently driving the wrong direction
            diffPose = targetPose.relativeTo(RobotContainer.getInstance().m_Estimator.getRobotFieldPose());
            RobotContainer.getInstance().m_driveTrain.cmdGoToPos(RobotMath.metersToInches(diffPose.getX()),
                    RobotMath.metersToInches(diffPose.getY()), Math.toDegrees(diffPose.getRotation().getZ()), power);
        }
        if (RobotContainer.getInstance().m_driveTrain.isComplete()) {
            RobotContainer.getInstance().m_driveTrain.StopDrive();
            bDone = true;
            end(false);
        }
        RobotContainer.getInstance().m_driveTrain.gyroHeading = RobotContainer.getInstance().m_subGyro
                .getNormaliziedNavxAngle();

    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        RobotContainer.getInstance().m_driveTrain.disableGoToPos();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return bDone;
    }

    @Override
    public boolean runsWhenDisabled() {

        return false;

    }
}
