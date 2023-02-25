package frc.robot.commands.armCommands;

import java.util.concurrent.DelayQueue;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.RobotMath;

public class cmdSetGripperPos extends CommandBase {

    private double targPos = 0;
    private double stagPos = 1.0;
    private double stagPower = 1.0;
    private double startTime = 0;
    
    private double timeOut = 0.5; // in seconds

    private double endTime = 0;
    private boolean bdone = false;

    private boolean bWait = false;

    public cmdSetGripperPos(double targetPos, boolean wait) {
        targPos = targetPos;
        bWait = wait;

        // m_subsystem = subsystem;
        // addRequirements(m_subsystem);

    }

    // if fixedDist = false => stagPosition is suposed to recieve the percantage to
    // be traversed in stag, in 0.xx format
    public cmdSetGripperPos(double targetPos, double stagPosition, double stagPow, boolean fixedDist) {
        targPos = targetPos;
        bWait = true;

        // if fixedDist = false => stagPosition is suposed to recieve the percantage to
        // be traversed in stag, in 0.xx format
        if (fixedDist) {
            // fixed distance is a specific distance from the target when to start stagging
            stagPos = stagPosition;
        } else {
            stagPos = -1;
        }
        stagPower = stagPow;
        // m_subsystem = subsystem;
        // addRequirements(m_subsystem);

    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        if (stagPos == -1) {
            double pos = RobotContainer.getInstance().m_Gripper.getGripPos();

            stagPos = (Math.abs(pos - targPos)) * stagPos;
        }
        RobotContainer.getInstance().m_Gripper.setGripPos(targPos, stagPos, stagPower);
        startTime = RobotMath.getTime();
        endTime = startTime + timeOut;
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
       if(bWait){

       
        double curTime = RobotMath.getTime();
        if (curTime >= endTime) {
            bdone = true;
            end(false);
        }
    }

        else{
            bdone = true;
            end(false);
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
       // RobotContainer.getInstance().m_Gripper.stop();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return bdone;
    }

    @Override
    public boolean runsWhenDisabled() {
        return false;

    }
}
