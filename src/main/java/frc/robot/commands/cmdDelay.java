package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotMath;

public class cmdDelay extends CommandBase {

    public boolean bdone = false;
    public double startTime = 0;
    public double endTime = 0;
    public double delayTime = 0;

    public cmdDelay(double seconds) {
        delayTime = seconds;
    }

    @Override
    public void initialize() {
        bdone = false;
        startTime = RobotMath.getTime();
        endTime = startTime + delayTime;
        System.err.println("Delay for a bit");
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {

        if (RobotMath.getTime() >= endTime) {
            bdone = true;
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        bdone = true;
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