package frc.robot.commands.AutonCommands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.cmdDelay;
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
public class Auto_Charge7_V2 extends SequentialCommandGroup {

    public Auto_Charge7_V2() {
        addCommands(new cmdResetGyro());
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

        // drive over the charge station to get outside of the community
        addCommands(new cmdVisionDriveDistance(7,170,
            142,0,0.4));

        addCommands(new cmdVisionDriveDistance(7,192,
            50,0,0.10));

        addCommands(new cmdDelay(1));
        
        
        
        // drive ontop of the charge station
        addCommands(new cmdVisionDriveDistance(7,86,
            106,0,-0.4));


        //addCommands(new cmdGyroBalance);
        addCommands(new cmdActiveBalance());
    }


}
