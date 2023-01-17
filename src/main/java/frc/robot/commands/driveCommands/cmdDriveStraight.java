// RobotBuilder Version: 5.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

// ROBOTBUILDER TYPE: Command.

package frc.robot.commands.driveCommands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.RobotMath;
import frc.robot.hardware.WL_Spark;
import frc.robot.subsystems.DriveTrain;

import java.util.function.DoubleSupplier;

// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

/**
 *
 */
public class cmdDriveStraight extends CommandBase {
private static double targetPosition = 0; //inches
private static double power = 0.15;
private static double targetHeading = 0;
private static boolean bDone = false;
private static double overshootValue = 0;
private WL_Spark.IdleMode idleMode = WL_Spark.IdleMode.kBrake;

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS
 
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

    public cmdDriveStraight(double targetDistance, double speed, double heading) {

        targetPosition = targetDistance;
        power = speed;
        targetHeading = heading;
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES

        // m_subsystem = subsystem;
        // addRequirements(m_subsystem);    

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        bDone = false;
        RobotContainer.getInstance().m_driveTrain.resetEncoders();
        RobotContainer.getInstance().m_driveTrain.doDrive(power,0,0,1);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
     
        double headingDelta = RobotMath.calcTurnRate(RobotContainer.getInstance().m_subGyro.getNormaliziedNavxAngle(), 
        targetHeading, RobotContainer.getInstance().m_driveTrain.kp_driveStraightGyro);

       
        RobotContainer.getInstance().m_driveTrain.doDrive(power, 0, headingDelta,1);
        if(targetPosition <= RobotContainer.getInstance().m_driveTrain.getDistanceTraveledInches()){
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
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DISABLED
        return false;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DISABLED
    }
}
