package frc.robot.commands.armCommands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Arm;



public class cmdSetArmMode extends CommandBase {

    private Arm.Mode nMode;

    private boolean bWait = false;
    private boolean bDone = false;
   


    public cmdSetArmMode(Arm.Mode pMode, boolean wait) {
        nMode = pMode;
        bWait = wait;


        // m_subsystem = subsystem;
        // addRequirements(m_subsystem);    

    }
     //if fixedDist = false => stagPosition is suposed to recieve the percantage to be traversed in stag, in 0.xx format
     

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        RobotContainer.getInstance().m_arm.setCurrentMode(nMode);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        if(bWait){
            if(RobotContainer.getInstance().m_arm.isInPos()){
                bDone = true;
            }
            else{
                bDone = false;
            }
        }
        else{
            bDone = true;
        }
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
