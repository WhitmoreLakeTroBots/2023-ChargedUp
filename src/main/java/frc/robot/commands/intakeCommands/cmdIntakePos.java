package frc.robot.commands.intakeCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Intake;

public class cmdIntakePos extends CommandBase {

    private boolean bDone = true;
    private boolean bWait = false;
    private double target;

    public cmdIntakePos(double ntarget, boolean wait) {
        target = ntarget;
        bWait = wait;
        // m_subsystem = subsystem;
        // addRequirements(m_subsystem);

    }
    // if fixedDist = false => stagPosition is suposed to recieve the percantage to
    // be traversed in stag, in 0.xx format

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        RobotContainer.getInstance().m_Intake.setIntakeRotPos(target);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        if(bWait){
            if(RobotContainer.getInstance().m_Intake.getIsInPos()){
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
