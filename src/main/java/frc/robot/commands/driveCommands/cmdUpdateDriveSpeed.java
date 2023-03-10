package frc.robot.commands.driveCommands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;


public class cmdUpdateDriveSpeed extends CommandBase {

    private double speed = 0.4;
   


    public cmdUpdateDriveSpeed(double nSpeed) {
        speed = nSpeed;


        // m_subsystem = subsystem;
        // addRequirements(m_subsystem);    

    }
     //if fixedDist = false => stagPosition is suposed to recieve the percantage to be traversed in stag, in 0.xx format
     

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        RobotContainer.getInstance().m_driveTrain.setMaxSpeed(speed);
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
        return true;
    }

    @Override
    public boolean runsWhenDisabled() {
        return false;

    }
}
