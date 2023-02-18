package frc.robot.commands.driveCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.RobotMath;
import frc.robot.hardware.WL_Spark;
import frc.robot.subsystems.DriveTrain;

import java.util.function.DoubleSupplier;


/**
 *
 */
public class cmdDriveStraight extends CommandBase {
    private double targetPosition = 0; // inches
    private double power = 0.15;
    private double targetHeading = 0;
    private boolean bDone = false;
    private double overshootValue = 0;
    private WL_Spark.IdleMode idleMode = WL_Spark.IdleMode.kBrake;

    

    public cmdDriveStraight(double targetDistance, double speed) {

        targetPosition = targetDistance;
        power = speed;
        targetHeading = 0;


        // m_subsystem = subsystem;
        // addRequirements(m_subsystem);
    }


    public cmdDriveStraight(double targetDistance, double speed, double heading) {

        targetPosition = targetDistance;
        power = speed;
        targetHeading = heading;


        // m_subsystem = subsystem;
        // addRequirements(m_subsystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        bDone = false;
        RobotContainer.getInstance().m_driveTrain.resetEncoders();
        RobotContainer.getInstance().m_driveTrain.doDrive(power, 0, 0, .4);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {

       /*  double headingDelta = RobotMath.calcTurnRate(RobotContainer.getInstance().m_subGyro.getNormaliziedNavxAngle(),
                targetHeading, RobotContainer.getInstance().m_driveTrain.kp_driveStraightGyro);
        */
        double headingDelta = 0;
        
        RobotContainer.getInstance().m_driveTrain.doDrive(power, 0, headingDelta, .4);
        if (RobotContainer.getInstance().m_driveTrain.getDistanceTraveledInches() >= targetPosition  ) {
            bDone = true;
            // end(false);
            RobotContainer.getInstance().m_driveTrain.StopDrive();
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
