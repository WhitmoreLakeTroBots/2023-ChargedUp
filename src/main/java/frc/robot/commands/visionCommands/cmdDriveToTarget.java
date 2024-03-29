package frc.robot.commands.visionCommands;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.RobotMath;
import frc.robot.Constants.GearTrain;
import frc.robot.subsystems.SubPoseEstimator;

/**
 *
 */
public class cmdDriveToTarget extends CommandBase {
    private Pose3d targetPose;
    private Pose3d diffPose;

    private double power = 0.05;
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

        diffPose = targetPose.relativeTo(RobotContainer.getInstance().m_Estimator.getRobotFieldPose());
        RobotContainer.getInstance().m_driveTrain.cmdGoToPos(RobotMath.metersToInches(diffPose.getX()),
        RobotMath.metersToInches(diffPose.getY())
        ,0
        //, Math.toDegrees(diffPose.getRotation().getY()) * -1
        ,power);
        this.power = this.power*GearTrain.gearFactor; //after we changed gear ratios to keep autons same

    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        if (RobotContainer.getInstance().m_Estimator.getRobotFieldPose().getZ() > 0) {

            // converting to inches from pos value before entering drive train
            // Pos may be reversed, currently driving the wrong direction
          //   diffPose = targetPose.relativeTo(RobotContainer.getInstance().m_Estimator.getRobotFieldPose());
//diffPose = RobotContainer.getInstance().m_Estimator.getRobotFieldPose().relativeTo(targetPose);
            RobotContainer.getInstance().m_Estimator.diffX = diffPose.getX();
            RobotContainer.getInstance().m_Estimator.diffY = diffPose.getY();

            RobotContainer.getInstance().m_Estimator.calcDiffX = targetPose.getX() - RobotContainer.getInstance().m_Estimator.getRobotFieldPose().getX();
            RobotContainer.getInstance().m_Estimator.calcDiffY = targetPose.getY() - RobotContainer.getInstance().m_Estimator.getRobotFieldPose().getY();
           
       /*     RobotContainer.getInstance().m_driveTrain.cmdGoToPos(RobotMath.metersToInches(diffPose.getX()),
                    RobotMath.metersToInches(diffPose.getY())
                    ,0
                    //, Math.toDegrees(diffPose.getRotation().getY()) * -1
                    ,power);

                    */
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
