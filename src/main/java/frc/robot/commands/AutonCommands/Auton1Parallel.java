package frc.robot.commands.AutonCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import java.util.function.DoubleSupplier;
import frc.robot.commands.armCommands.*;
import frc.robot.commands.driveCommands.*;
import frc.robot.commands.lightingCommands.*;
import frc.robot.subsystems.Arm;

/**
 *
 */
public class Auton1Parallel extends ParallelCommandGroup {

    public Auton1Parallel() {

    }

    public Auton1Parallel(Arm.Mode Mode) {

        addCommands(new cmdSetArmMode(Mode));

    }

    public Auton1Parallel(Arm.Mode Mode, double targetDistance, double driveSpeed,
            boolean driveStraight) {
        // if drive straight false ==> it will strafe
        this(Mode);

        if (driveStraight) {
            // addCommands(new cmdDriveStraight(targetDistance,driveSpeed));
        } else {
            // addCommands(new cmdStrafe(targetDistance, driveSpeed));
        }
    }

}
