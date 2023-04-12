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


import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Gripper;
import frc.robot.subsystems.Lighting.lightPattern;

/**
 *
 */
public class AutonBarrier_V2 extends SequentialCommandGroup {


    public AutonBarrier_V2(boolean red) {
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
        addCommands(new cmdSetBrakeMode(IdleMode.kCoast));

        addCommands(new cmdIntakePos(Arm.Mode.DELIVERLOW, false));
        addCommands(new cmdDriveStraight(30, 0.4, 0)); //dist in inches
        //addCommands(new Auton1Parallel(Arm.Mode.INTAKE,10,0.3,true));
        addCommands(new cmdDriveStraight(20, 0.55, 0));
        addCommands(new cmdDriveStraight(40, 0.75, 0)); //dist in inches
        addCommands(new cmdDriveStraight(15, 0.55, 0)); //target distance was 20
        addCommands(new cmdIntakePos(Arm.Mode.DELIVERLOW, false));
        addCommands(new cmdDriveStraight(22, 0.25, 0)); //dist in inches, was 30 inches
        //lower intake 
        
        addCommands(new cmdSetBrakeMode(IdleMode.kBrake));
        
        //start intake
        addCommands(new cmdStartIntake());

        addCommands(new cmdIntakePos(Arm.Mode.DELIVERLOW, true));
        //strafe to infront of cube for red alliance left for blue alliance right 
        if(red){
            addCommands(new cmdStrafe(22, -0.50, 0)); //dist in inches
        } else{
            addCommands(new cmdStrafe(22, 0.50, 0)); //dist in inches 
        }
        
       
        //drive 20 inches
        addCommands(new cmdDriveStraight(32, 0.2, 0)); //dist in inches
        //transfer cube to gripper
        addCommands(new cmdStopIntake());
        //addCommands(new cmdIntakePos(Arm.Mode.INTAKE, true));
        addCommands(new cmdIntakePos(Arm.Mode.TRANSFERPOS, true));
        addCommands(new cmdDelay(0.5));
        //addCommands(new cmdStopIntake());
        //eject from intake to gripper
        //addCommands(new cmdIntakeEject());
        //close gripper on cube
        //addCommands(new cmdSetGripperPos(Gripper.cubeClosePos,false));
        //put intake away
        //addCommands(new cmdSetArmMode(Arm.Mode.INTAKE, false));
        //strafe back
        //addCommands(new cmdStrafe(20, -0.2, 0)); //dist in inches
        //addCommands(new cmdSetGripperPos(Gripper.cubeClosePos,false));
        //drive to commmunity
        addCommands(new cmdSetBrakeMode(IdleMode.kCoast));
        addCommands(new cmdDriveStraight(30, -0.45, 0)); //dist in inches
        addCommands(new cmdSetGripperPos(Gripper.cubeClosePos,false));
        addCommands(new cmdDriveStraight(30, -0.60, 0)); //dist in inches
        addCommands(new cmdDriveStraight(61, -0.80, 0)); //dist in inches
        addCommands(new cmdSetArmMode(Arm.Mode.DELIVERHIGH, false));
        addCommands(new cmdDriveStraight(24, -0.60, 0)); //dist in inches
        //extend intake 
        //addCommands(new cmdSetArmMode(Arm.Mode.DELIVERHIGH, false));
        //drive to delivery
        addCommands(new cmdDriveStraight(20, -0.2, 0)); //dist in inches
        addCommands(new cmdSetBrakeMode(IdleMode.kBrake));
        
        //strafe to position for red alliance left for blue alliance right
        if(red){
            addCommands(new cmdStrafe(8, -0.45, 0)); //dist in inches
        } else{
            addCommands(new cmdStrafe(8, 0.45, 0)); //dist in inches 
        }
        
        //addCommands(new cmdStopIntake());
        //extend arm
        addCommands(new cmdSetArmMode(Arm.Mode.DELIVERHIGH, true));
        //deliver
        addCommands(new cmdSetGripperPos(Gripper.openPos,true));
        // go to intake 
        addCommands(new cmdSetArmMode(Arm.Mode.INTAKE, true));

    }


}
