package frc.robot.commands.AutonCommands;


import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.Arm;

/**
 *
 */
public class Auton1Parallel extends ParallelCommandGroup {

    public Auton1Parallel() {

    }

    public Auton1Parallel(Arm.Mode Mode) {

       // addCommands(new cmdSetArmMode(Mode));

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
