package frc.robot.commands.armCommands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

import java.util.function.DoubleSupplier;


public class cmdSetArmRotPos extends CommandBase {

    
    private double targPos = 0;
    private double stagPos = 1.0;
    private double stagPower = 1.0;


    public cmdSetArmRotPos(double targetPos) {
        targPos = targetPos;


        // m_subsystem = subsystem;
        // addRequirements(m_subsystem);    

    }
    
    //if fixedDist = false => stagPosition is suposed to recieve the percantage to be traversed in stag, in 0.xx format
    public cmdSetArmRotPos(double targetPos, double stagPosition, double stagPow, boolean fixedDist) {
        targPos = targetPos;
        //if fixedDist = false => stagPosition is suposed to recieve the percantage to be traversed in stag, in 0.xx format
        if(fixedDist){
            //fixed distance is a specific distance from the target when to start stagging
            stagPos = stagPosition;
        }
        else{
            stagPos = -1;
        }
        stagPower = stagPow;
        // m_subsystem = subsystem;
        // addRequirements(m_subsystem);    

        
    }
    
    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        if(stagPos == -1){
            double pos = RobotContainer.getInstance().m_arm.getArmRotPos();

            stagPos = (Math.abs(pos - targPos))*stagPos;
        }
        RobotContainer.getInstance().m_arm.setArmRotTargPos(targPos,stagPos,stagPower);
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
