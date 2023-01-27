package frc.robot.commands.driveCommands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.RobotMath;

import java.util.function.DoubleSupplier;

/**
 *
 */
public class cmdTurnByGyro extends CommandBase {

    private double targetHeading = 0;
    private boolean bDone = false;
    private double power = 0.15;
    private boolean clockwise = true;
    private double tol = 3;

    public cmdTurnByGyro(double heading, double speed, boolean turnLeft) {
        targetHeading = heading;
        power = speed;
        clockwise = !turnLeft;


    }

    public cmdTurnByGyro(double heading, double speed, boolean turnLeft, double Tol) {
        targetHeading = heading;
        power = speed;
        clockwise = !turnLeft;
        tol = Tol;

    }
    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        bDone = false;
        power = Math.abs(power);
        if(!clockwise){
            power = -1*power;
        }
        RobotContainer.getInstance().m_driveTrain.doDrive(0, 0, power, 1);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        
        if(RobotMath.isInRange(RobotContainer.getInstance().m_subGyro.getNormaliziedNavxAngle(), targetHeading, tol)){
            bDone = true;
            end(false);
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
