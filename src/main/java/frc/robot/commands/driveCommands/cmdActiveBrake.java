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



/**
 *
 */
public class cmdActiveBrake extends CommandBase {
    private double targetPosition = 0; // inches
    private double power = 0.05;
    private double targetHeading = 0;
    private boolean bDone = false;
    private double overshootValue = 0;
    private WL_Spark.IdleMode idleMode = WL_Spark.IdleMode.kBrake;

    

    public cmdActiveBrake() {

        
        
        

        // m_subsystem = subsystem;
        // addRequirements(m_subsystem);

       
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        bDone = false;
        RobotContainer.getInstance().m_driveTrain.resetEncoders();
        targetHeading = RobotContainer.getInstance().m_subGyro.getNormaliziedNavxAngle();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {

        RobotContainer.getInstance().m_driveTrain.activeBrake(power);

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
