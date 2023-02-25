package frc.robot.commands.AutonCommands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.commands.armCommands.*;
import frc.robot.commands.driveCommands.*;
import frc.robot.commands.intakeCommands.cmdIntakeEject;
import frc.robot.commands.intakeCommands.cmdIntakePos;
import frc.robot.commands.intakeCommands.cmdStartIntake;
import frc.robot.commands.intakeCommands.cmdStopIntake;
import frc.robot.commands.lightingCommands.*;


import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Gripper;
import frc.robot.subsystems.Lighting.lightPattern;

/**
 *
 */
public class A_B_P1_V2 extends SequentialCommandGroup {


    public A_B_P1_V2() {
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
        addCommands(new cmdDriveStraight(30, 0.2, 0)); //dist in inches
        //addCommands(new Auton1Parallel(Arm.Mode.INTAKE,10,0.3,true));

        addCommands(new cmdDriveStraight(86, 0.3, 0)); //dist in inches
        addCommands(new cmdIntakePos(Arm.Mode.DELIVERLOW, false));

        addCommands(new cmdDriveStraight(30, 0.2, 0)); //dist in inches
        //strafe to infront of cube 
        addCommands(new cmdStrafe(22, -0.3, 0)); //dist in inches
        //lower intake 
        addCommands(new cmdIntakePos(Arm.Mode.DELIVERLOW, true));
        //start intake
        addCommands(new cmdStartIntake());
        //drive 20 inches
        addCommands(new cmdDriveStraight(40, 0.2, 0)); //dist in inches
        //transfer cube to gripper
        addCommands(new cmdStopIntake());
        addCommands(new cmdIntakePos(Arm.Mode.TRANSFERPOS, true));
        //eject from intake to gripper
        addCommands(new cmdIntakeEject());
        //close gripper on cube
        addCommands(new cmdSetGripperPos(Gripper.cubeClosePos,true));
        //put intake away
        addCommands(new cmdSetArmMode(Arm.Mode.INTAKE, false));
        //strafe back
        //addCommands(new cmdStrafe(20, 0.2, 0)); //dist in inches
        //drive to commmunity
        addCommands(new cmdDriveStraight(30, -0.2, 0)); //dist in inches
        addCommands(new cmdDriveStraight(126, -0.5, 0)); //dist in inches
        //extend intake 
        addCommands(new cmdSetArmMode(Arm.Mode.DELIVERHIGH, false));
        //drive to delivery
        addCommands(new cmdDriveStraight(30, -0.2, 0)); //dist in inches
        //strafe to position
        addCommands(new cmdStrafe(18, -0.2, 0)); //dist in inches
        //extend arm
        addCommands(new cmdSetArmMode(Arm.Mode.DELIVERHIGH, true));
        //deliver
        addCommands(new cmdSetGripperPos(Gripper.openPos,true));
        // go to intake 
        addCommands(new cmdSetArmMode(Arm.Mode.INTAKE, true));

    }


}
