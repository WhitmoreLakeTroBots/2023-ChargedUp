package frc.robot.commands.driveCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.RobotMath;
import frc.robot.hardware.WL_Spark;
import frc.robot.subsystems.DriveTrain;

import java.util.function.DoubleSupplier;

/**
 *
 */
public class cmdActiveBalance extends CommandBase {
    private double targetPosition = 0; // inches
    private double power = 0.08; // was 0.025
    private double targetHeading = 0;
    private boolean bDone = false;
    private double overshootValue = 0;
    private WL_Spark.IdleMode idleMode = WL_Spark.IdleMode.kBrake;
    private final double pitchTol = 3;

    public cmdActiveBalance() {

        // m_subsystem = subsystem;
        // addRequirements(m_subsystem);

    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        bDone = false;
        RobotContainer.getInstance().m_driveTrain.resetEncoders();
        targetHeading = RobotContainer.getInstance().m_subGyro.getNormaliziedNavxAngle();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {

        double currHeading = RobotContainer.getInstance().m_subGyro.getNormaliziedNavxAngle();
        double currPitch = RobotContainer.getInstance().m_subGyro.getroll();
        double turnRate = RobotMath.calcTurnRate(currHeading, targetHeading,
                RobotContainer.getInstance().m_driveTrain.kp_driveStraightGyro);

        if (currPitch > pitchTol) {

            RobotContainer.getInstance().m_driveTrain.doDrive(power, 0, turnRate, 1);
        } else if (currPitch < -pitchTol) {

            RobotContainer.getInstance().m_driveTrain.doDrive(-power, 0, turnRate, 1);
        } else {
            RobotContainer.getInstance().m_driveTrain.StopDrive();
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        RobotContainer.getInstance().m_driveTrain.StopDrive();
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
