package frc.robot.commands.AutonCommands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.commands.armCommands.*;
import frc.robot.commands.driveCommands.*;
import frc.robot.commands.intakeCommands.cmdIntakePos;
import frc.robot.commands.lightingCommands.*;
import frc.robot.commands.visionCommands.*;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Gripper;
import frc.robot.subsystems.Lighting.lightPattern;

/**
 *
 */
public class Auto_Charge7_V1 extends SequentialCommandGroup {

    public Auto_Charge7_V1() {
        addCommands(new cmdResetGyro());
        //sets intake to deliver high
        //addCommands(new cmdUpdateBaseColor(lightPattern.RAINBOWLAVA));
        addCommands(new cmdIntakePos(Arm.Mode.DELIVERHIGH, true));
        //addCommands(new cmdUpdateBaseColor(lightPattern.BLUEGREEN));
        addCommands(new cmdSetArmMode(Arm.Mode.DELIVERHIGH, true));
       // addCommands(new Auton1Parallel(Arm.deliveryRotPos,Arm.deliveryExtendPos));

        //addCommands(new cmdUpdateBaseColor(lightPattern.CONFETTI));
        addCommands(new cmdSetGripperPos(Gripper.openPos,true));
        //addCommands(new cmdUpdateBaseColor(lightPattern.LAWNGREEN));
        addCommands(new cmdSetArmMode(Arm.Mode.INTAKE, true));
        addCommands(new cmdSetGripperPos(Gripper.coneClosePos, false));
        
        //addCommands(new cmdIntakePos(Intake.inPos, true));
        //addCommands(new cmdUpdateBaseColor(lightPattern.RAINBOW));
        addCommands(new cmdVisionDriveDistance(7,93,
            120,0,.45));
        //addCommands(new cmdActiveBrake());
        addCommands(new cmdActiveBalance());
    }


}
