package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.CommandBase;


import frc.robot.subsystems.DriveTrain;


/**
 *
 */
public class cmdTurn extends CommandBase {


    private final DriveTrain m_driveTrain;
    private double m_left_throttle;
    private double m_right_throttle;
    private double m_heading_degree;
 

    public cmdTurn(double left_throttle, double right_throttle, double heading_degree, DriveTrain subsystem) {

        m_left_throttle = left_throttle;
        m_right_throttle = right_throttle;
        m_heading_degree = heading_degree;

        m_driveTrain = subsystem;
        addRequirements(m_driveTrain);

    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public boolean runsWhenDisabled() {

        return false;


    }
}
