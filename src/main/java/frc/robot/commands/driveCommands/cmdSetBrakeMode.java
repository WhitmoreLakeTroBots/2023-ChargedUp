package frc.robot.commands.driveCommands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.hardware.WL_Spark;



public class cmdSetBrakeMode extends CommandBase {

    private WL_Spark.IdleMode nMode;

    private boolean bDone = false;
   


    public cmdSetBrakeMode(WL_Spark.IdleMode pMode) {
        nMode = pMode;
        


        // m_subsystem = subsystem;
        // addRequirements(m_subsystem);    

    }
     //if fixedDist = false => stagPosition is suposed to recieve the percantage to be traversed in stag, in 0.xx format
     

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        RobotContainer.getInstance().m_driveTrain.setMotorBrakeMode(nMode);
        bDone = false;
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        bDone = true;
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
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
