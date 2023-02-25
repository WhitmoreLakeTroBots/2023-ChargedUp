package frc.robot.commands.AutonCommands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.commands.armCommands.*;
import frc.robot.commands.driveCommands.*;
import frc.robot.commands.intakeCommands.cmdIntakePos;
import frc.robot.commands.lightingCommands.*;


import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Gripper;
import frc.robot.subsystems.Lighting.lightPattern;

/**
 *
 */
public class Auto_V1 extends SequentialCommandGroup {


    public Auto_V1() {
        //sets intake to deliver high
        addCommands(new cmdUpdateBaseColor(lightPattern.RAINBOWLAVA));
        addCommands(new cmdIntakePos(Arm.Mode.DELIVERHIGH, true));
        addCommands(new cmdUpdateBaseColor(lightPattern.BLUEGREEN));
        addCommands(new cmdSetArmMode(Arm.Mode.DELIVERHIGH, true));
       // addCommands(new Auton1Parallel(Arm.deliveryRotPos,Arm.deliveryExtendPos));

        addCommands(new cmdUpdateBaseColor(lightPattern.CONFETTI));
        addCommands(new cmdSetGripperPos(Gripper.openPos,true));
        addCommands(new cmdUpdateBaseColor(lightPattern.LAWNGREEN));
        addCommands(new cmdSetArmMode(Arm.Mode.INTAKE, true));
        
        //addCommands(new cmdIntakePos(Intake.inPos, true));
        addCommands(new cmdUpdateBaseColor(lightPattern.RAINBOW));
        addCommands(new cmdDriveStraight(148, 0.4, 0)); //dist in inches
        //addCommands(new Auton1Parallel(Arm.Mode.INTAKE,10,0.3,true));
    }


}
