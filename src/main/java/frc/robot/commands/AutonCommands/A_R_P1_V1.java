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
public class A_R_P1_V1 extends SequentialCommandGroup {


    public A_R_P1_V1() {

        addCommands(new cmdSetArmMode(Arm.Mode.DELIVERHIGH, true));
       // addCommands(new Auton1Parallel(Arm.deliveryRotPos,Arm.deliveryExtendPos));

        addCommands(new cmdSetGripperPos(Gripper.openPos,true));
        addCommands(new cmdSetArmMode(Arm.Mode.INTAKE, false));
        addCommands(new cmdDriveStraight(20, 0.5, 0)); //dist in inches
        //addCommands(new Auton1Parallel(Arm.Mode.INTAKE,10,0.3,true));
    }


}
