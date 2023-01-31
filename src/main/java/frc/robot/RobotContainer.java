package frc.robot;

import frc.robot.commands.*;
import frc.robot.commands.AutonCommands.Auton1;
import frc.robot.commands.armCommands.cmdSetArmExtendPos;
import frc.robot.commands.armCommands.cmdSetArmRotPos;
import frc.robot.commands.armCommands.cmdSetGripperPos;
import frc.robot.commands.driveCommands.cmdActiveBrake;
import frc.robot.commands.driveCommands.cmdDriveStraight;
import frc.robot.commands.driveCommands.cmdStrafe;
import frc.robot.commands.driveCommands.cmdTurnByGyro;
import frc.robot.commands.lightingCommands.cmdUpdateBaseColor;
import frc.robot.subsystems.*;
import frc.robot.subsystems.Lighting.lightPattern;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command.InterruptionBehavior;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.subsystems.*;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot
 * (including subsystems, commands, and button mappings) should be declared
 * here.
 */
public class RobotContainer {

  private static RobotContainer m_robotContainer = new RobotContainer();

  // The robot's subsystems
  public final TemplateSubsystem m_templateSubsystem = new TemplateSubsystem();
  public final DriveTrain m_driveTrain = new DriveTrain();
  public final SubGyro m_subGyro = new SubGyro();
  public final Lighting m_Lighting = new Lighting();
  public final Arm m_arm = new Arm();
  public final Vision m_Vision = new Vision();
  public final Gripper m_Gripper = new Gripper();

  // Joysticks
  private final CommandXboxController driveController = new CommandXboxController(0);
  private final CommandXboxController articController = new CommandXboxController(1);

 private final double debounce = 0.5;

  // A chooser for autonomous commands
  SendableChooser<Command> m_chooser = new SendableChooser<>();

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  private RobotContainer() {

    // SmartDashboard Buttons
    // SmartDashboard.putData("cmdDriveStraight", new cmdTurnByGyro());

    SmartDashboard.putData("testdrive", new cmdDriveStraight(5, .25, 0.0));

    configureButtonBindings();

    // Test Drive code
    m_chooser.setDefaultOption("active break", new cmdActiveBrake());
    m_chooser.addOption("driveforward", new cmdDriveStraight(24, .25, 0.0));
    m_chooser.addOption("driveback", new cmdDriveStraight(24, -.25, 0.0));

    m_chooser.addOption("turn-90 (left)", new cmdTurnByGyro(-90, .2, true));
    m_chooser.addOption("turn 90 (right)", new cmdTurnByGyro(90, .2, false));

    m_chooser.addOption("turn-180 (left)", new cmdTurnByGyro(180, .2, true));
    m_chooser.addOption("turn 180 (right)", new cmdTurnByGyro(180, .2, false));

    m_chooser.addOption("strafe left", new cmdStrafe(12, .25, 0.0));
    m_chooser.addOption("strafe right", new cmdStrafe(12, -.25, 0.0));

    SmartDashboard.putData("turn-0", new cmdTurnByGyro(0, .2, true));
    SmartDashboard.putData("driveforward", new cmdDriveStraight(24, .25, m_subGyro.getNormaliziedNavxAngle()));
    SmartDashboard.putData("driveback", new cmdDriveStraight(24, -.25, m_subGyro.getNormaliziedNavxAngle()));
    SmartDashboard.putData("turn-90 (left)", new cmdTurnByGyro(-90, .2, true));
    SmartDashboard.putData("turn 90 (right)", new cmdTurnByGyro(90, .2, false));
    SmartDashboard.putData("strafe left", new cmdStrafe(12, .25, m_subGyro.getNormaliziedNavxAngle()));
    SmartDashboard.putData("strafe right", new cmdStrafe(12, -.25, m_subGyro.getNormaliziedNavxAngle()));
    SmartDashboard.putData("active break", new cmdActiveBrake());
    SmartDashboard.putNumber("Extend arm Pos", 0);
    SmartDashboard.putData("set color red", new cmdUpdateBaseColor(lightPattern.RED));
    SmartDashboard.putData("set color blue", new cmdUpdateBaseColor(lightPattern.BLUE));
    SmartDashboard.putData("Extend arm go to pos",
        new cmdSetArmExtendPos(SmartDashboard.getNumber("Extend arm Pos", 0)));

    SmartDashboard.putData("Rot in", new cmdSetArmRotPos(0, 30, .15, false));
    SmartDashboard.putData("Rot out", new cmdSetArmRotPos(61, 30, .15, false));
    SmartDashboard.putData("Extend Arm", new cmdSetArmExtendPos(195, 30, .15, false));
    SmartDashboard.putData("Retract Arm", new cmdSetArmExtendPos(1, 30, .15, false));
    SmartDashboard.putData("Grip Open", new cmdSetGripperPos(Gripper.openPos));
    SmartDashboard.putData("Grip Cone", new cmdSetGripperPos(Gripper.coneClosePos));
    SmartDashboard.putData("Grip cube", new cmdSetGripperPos(Gripper.cubeClosePos));
    SmartDashboard.putData("auton1test", new Auton1());

    SmartDashboard.putData("Auto Mode", m_chooser);
  }

  public static RobotContainer getInstance() {
    return m_robotContainer;
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing
   * it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {

    // A is gripper close cone
    Trigger A_ArticButton = articController.a();
    A_ArticButton.debounce(debounce)
      .onTrue(new cmdSetGripperPos(Gripper.coneClosePos));

    //X is gripper close cube
    Trigger X_ArticButton = articController.x();
    X_ArticButton.debounce(debounce)
      .onTrue(new cmdSetGripperPos(Gripper.cubeClosePos));

    //Y is gripper open 
    Trigger Y_ArticButton = articController.y();
    Y_ArticButton.debounce(debounce)
    .onTrue(new cmdSetGripperPos(Gripper.openPos));

    //DUp set extention far


    //DLeft set extention middle


    //DDown set extention pos low 

  
  }

  public CommandXboxController getDriveController() {
    return driveController;
  }

  public CommandXboxController getArticController() {
    return articController;
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // The selected command will be run in autonomous
    return m_chooser.getSelected();
  }

  public void updateSmartboard() {

    SmartDashboard.putNumber("Heading", m_subGyro.getNormaliziedNavxAngle());
    SmartDashboard.putNumber("Dist", m_driveTrain.getDistanceTraveledInches());

    SmartDashboard.putNumber("ArmExtendPos", m_arm.getArmExtendPos());
    SmartDashboard.putNumber("ArmRotPos", m_arm.getArmRotPos());
    SmartDashboard.putNumber("gripperpos", m_Gripper.getGripPos());
    // SmartDashboard.putNumber("LDM1POS", m_driveTrain.lDM1.getPositionABS());

    SmartDashboard.putNumber("AprilTag ID", m_Vision.getTagId());
    SmartDashboard.putNumber("Distance in Meters", m_Vision.getRangeFromTagMeters());
    SmartDashboard.putNumber("Target Yaw", m_Vision.getTagYawDegrees());
    SmartDashboard.putNumber("Target Pitch", m_Vision.getTagPitchDegrees());

  }

}
