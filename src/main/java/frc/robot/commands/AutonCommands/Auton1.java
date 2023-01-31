package frc.robot.commands.AutonCommands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import java.util.function.DoubleSupplier;
import frc.robot.commands.armCommands.*;
import frc.robot.commands.driveCommands.*;
import frc.robot.commands.lightingCommands.*;

import frc.robot.commands.AutonCommands.Auton1Parallel;

import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Gripper;

/**
 *
 */
public class Auton1 extends SequentialCommandGroup {


    public Auton1() {

        addCommands(new cmdSetArmRotPos(Arm.carryRotPos, 30, .15, false));
        addCommands(new Auton1Parallel(Arm.deliveryRotPos,Arm.deliveryExtendPos));

        addCommands(new cmdSetGripperPos(Gripper.openPos));

        
        addCommands(new Auton1Parallel(Arm.carryRotPos,Arm.carryExtendPos,10,0.3,true));
    }


}
