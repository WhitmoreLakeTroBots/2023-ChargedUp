package frc.robot.commands.intakeCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.RobotMath;

public class cmdIntakeEject extends CommandBase {

    private boolean bDone = false;
    private double delay = 0.5;
    private double endTime;

    public cmdIntakeEject() {

        // m_subsystem = subsystem;
        // addRequirements(m_subsystem);

    }
    // if fixedDist = false => stagPosition is suposed to recieve the percantage to
    // be traversed in stag, in 0.xx format

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        bDone = false;
        RobotContainer.getInstance().m_Intake.startIntake();
        endTime = RobotMath.getTime() + delay;
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        if(RobotMath.getTime() > endTime){
            RobotContainer.getInstance().m_Intake.stopIntake();
            bDone = true;
            end(false);
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
