package frc.robot.commands.AutonCommands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import java.util.function.DoubleSupplier;
import frc.robot.commands.armCommands.*;
import frc.robot.commands.driveCommands.*;
import frc.robot.commands.lightingCommands.*;

/**
 *
 */
public class Auton1Parallel extends ParallelCommandGroup {


    public Auton1Parallel(){

    }

        public Auton1Parallel(double targetRotPos, double targetExtendPos) {

        addCommands(new cmdSetArmRotPos(targetRotPos, 30, .15, false));
        addCommands(new cmdSetArmExtendPos(targetExtendPos,30,0.15,false));

    }

    public Auton1Parallel(double targetRotPos, double targetExtendPos, double targetDistance, double driveSpeed, boolean driveStraight) {
//if drive straight false ==> it will strafe
    this(targetRotPos,targetExtendPos);

    if(driveStraight){
        addCommands(new cmdDriveStraight(targetDistance,driveSpeed));
    }
    else{
        addCommands(new cmdStrafe(targetDistance, driveSpeed));
    }
    }

    

}
