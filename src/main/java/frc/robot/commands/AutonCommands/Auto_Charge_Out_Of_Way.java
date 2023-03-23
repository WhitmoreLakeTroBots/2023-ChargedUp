package frc.robot.commands.AutonCommands;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.cmdDelay;
import frc.robot.commands.armCommands.*;
import frc.robot.commands.driveCommands.*;
import frc.robot.commands.intakeCommands.cmdIntakePos;
import frc.robot.commands.intakeCommands.cmdStartIntake;
import frc.robot.commands.intakeCommands.cmdStopIntake;
import frc.robot.commands.lightingCommands.*;
import frc.robot.commands.visionCommands.*;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Gripper;
import frc.robot.subsystems.Lighting.lightPattern;

/**
 *
 */
public class Auto_Charge_Out_Of_Way extends SequentialCommandGroup {

    public Auto_Charge_Out_Of_Way(int aprilTag) {
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
        
        //addCommands(new cmdIntakePos(Intake.inPos, true));
        //addCommands(new cmdUpdateBaseColor(lightPattern.RAINBOW));

        
     
        // drive over the charge station to get outside of the community
        addCommands(new cmdSetBrakeMode(IdleMode.kCoast));
        
        addCommands(new cmdVisionDriveDistance(aprilTag,170,
            120,0,0.40));
        addCommands(new cmdIntakePos(Arm.Mode.DELIVERLOW, false));
        addCommands(new cmdVisionDriveDistance(aprilTag,192,
            52,0,0.40));
        addCommands(new cmdSetBrakeMode(IdleMode.kBrake));
        addCommands(new cmdStartIntake());
        addCommands(new cmdIntakePos(Arm.Mode.DELIVERLOW, true));
        //addCommands(new cmdDelay(1));
        //addCommands(new cmdStartIntake());
        addCommands(new cmdDriveStraight(30, 0.25));
        addCommands(new cmdStopIntake());
        addCommands(new cmdIntakePos(Arm.Mode.TRANSFERPOS, true));
        addCommands(new cmdDelay(0.5));
        addCommands(new cmdSetGripperPos(Gripper.cubeClosePos, false));
        /*
        addCommands(new cmdSetBrakeMode(IdleMode.kCoast));
        // drive ontop of the charge station
        addCommands(new cmdVisionDriveDistance(aprilTag,86,
            126,5,-0.55));
        addCommands(new cmdSetBrakeMode(IdleMode.kCoast));
        //addCommands(new cmdIntakePos(Arm.Mode.START, false));
        

        //addCommands(new cmdGyroBalance);
        addCommands(new cmdActiveBalance());
        */
        if(aprilTag == 2){
            addCommands(new cmdStrafe(20,-0.4,0));
        }
        else{
            addCommands(new cmdStrafe(20,0.4,0));
        }
    }


}
