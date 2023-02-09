package frc.robot;

import frc.robot.commands.*;
import frc.robot.commands.AutonCommands.A_R_P1_V1;
import frc.robot.commands.armCommands.cmdSetArmMode;
import frc.robot.commands.armCommands.cmdSetGripperPos;
import frc.robot.commands.driveCommands.cmdActiveBrake;
import frc.robot.commands.driveCommands.cmdDriveStraight;
import frc.robot.commands.driveCommands.cmdStrafe;
import frc.robot.commands.driveCommands.cmdTurnByGyro;
import frc.robot.commands.driveCommands.cmdUpdateDriveSpeed;
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
  public final Intake m_Intake = new Intake();

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
    m_chooser.addOption("A_R_P1_V1", new A_R_P1_V1());
    /*
     * m_chooser.addOption("driveforward", new cmdDriveStraight(24, .25, 0.0));
     * m_chooser.addOption("driveback", new cmdDriveStraight(24, -.25, 0.0));
     * 
     * m_chooser.addOption("turn-90 (left)", new cmdTurnByGyro(-90, .2, true));
     * m_chooser.addOption("turn 90 (right)", new cmdTurnByGyro(90, .2, false));
     * 
     * m_chooser.addOption("turn-180 (left)", new cmdTurnByGyro(180, .2, true));
     * m_chooser.addOption("turn 180 (right)", new cmdTurnByGyro(180, .2, false));
     * 
     * m_chooser.addOption("strafe left", new cmdStrafe(12, .25, 0.0));
     * m_chooser.addOption("strafe right", new cmdStrafe(12, -.25, 0.0));
     */

    /*
     * SmartDashboard.putData("turn-0", new cmdTurnByGyro(0, .2, true));
     * SmartDashboard.putData("driveforward", new cmdDriveStraight(24, .25,
     * m_subGyro.getNormaliziedNavxAngle()));
     * SmartDashboard.putData("driveback", new cmdDriveStraight(24, -.25,
     * m_subGyro.getNormaliziedNavxAngle()));
     * SmartDashboard.putData("turn-90 (left)", new cmdTurnByGyro(-90, .2, true));
     * SmartDashboard.putData("turn 90 (right)", new cmdTurnByGyro(90, .2, false));
     * SmartDashboard.putData("strafe left", new cmdStrafe(12, .25,
     * m_subGyro.getNormaliziedNavxAngle()));
     * SmartDashboard.putData("strafe right", new cmdStrafe(12, -.25,
     * m_subGyro.getNormaliziedNavxAngle()));
     */
    SmartDashboard.putData("active break", new cmdActiveBrake());
    SmartDashboard.putData("Extend arm Pos", new cmdSetArmMode(Arm.Mode.DELIVERHIGH, false));
    SmartDashboard.putData("arm Pos Intake", new cmdSetArmMode(Arm.Mode.INTAKE, false));
    SmartDashboard.putData("set color red", new cmdUpdateBaseColor(lightPattern.RED));
    SmartDashboard.putData("set color blue", new cmdUpdateBaseColor(lightPattern.BLUE));

    SmartDashboard.putData("Grip Open", new cmdSetGripperPos(Gripper.openPos, false));
    SmartDashboard.putData("Grip Cone", new cmdSetGripperPos(Gripper.coneClosePos, false));
    SmartDashboard.putData("Grip cube", new cmdSetGripperPos(Gripper.cubeClosePos, false));
    SmartDashboard.putData("auton1test", new A_R_P1_V1());

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
    A_ArticButton.onTrue(new cmdSetGripperPos(Gripper.coneClosePos, false))
        .onTrue(new cmdUpdateBaseColor(Lighting.lightPattern.ORANGE));

    // X is gripper close cube
    Trigger X_ArticButton = articController.x();
    X_ArticButton.onTrue(new cmdSetGripperPos(Gripper.cubeClosePos, false))
        .onTrue(new cmdUpdateBaseColor(Lighting.lightPattern.ORANGE));

    // Y is gripper open
    Trigger Y_ArticButton = articController.y();
    Y_ArticButton.onTrue(new cmdSetGripperPos(Gripper.openPos, false))
        .onTrue(new cmdUpdateBaseColor(Lighting.lightPattern.GOLD));

    // DUp set extention high
    Trigger dUP_ArticButton = articController.povUp();
    dUP_ArticButton.onTrue(new cmdSetArmMode(Arm.Mode.DELIVERHIGH, false))
        .onTrue(new cmdUpdateBaseColor(Lighting.lightPattern.BLUEGREEN));

    // DLeft set extention middle
    Trigger dLeft_ArticButton = articController.povLeft();
    dLeft_ArticButton.onTrue(new cmdSetArmMode(Arm.Mode.DELIVERMED, false))
        .onTrue(new cmdUpdateBaseColor(Lighting.lightPattern.GREEN));

    // DRight is intake
    Trigger dRight_ArticButton = articController.povRight();
    dRight_ArticButton.onTrue(new cmdSetArmMode(Arm.Mode.INTAKE, false))
        .onTrue(new cmdUpdateBaseColor(Lighting.lightPattern.LAWNGREEN));

    // DDown set extention pos low
    Trigger dDown_ArticButton = articController.povDown();
    dDown_ArticButton.onTrue(new cmdSetArmMode(Arm.Mode.DELIVERLOW, false))
        .onTrue(new cmdUpdateBaseColor(Lighting.lightPattern.LIME));

    // select set extension pos carry
    Trigger back_ArticButton = articController.back();
    back_ArticButton.onTrue(new cmdSetArmMode(Arm.Mode.CARRY, false))
        .onTrue(new cmdUpdateBaseColor(Lighting.lightPattern.DARKGREEN));

    // left trigger on driver = boost
    Trigger lTrigger_driveController = driveController.leftTrigger();
    lTrigger_driveController.onTrue(new cmdUpdateDriveSpeed(DriveTrain.boostSpeed))
        .onTrue(new cmdUpdateBaseColor(Lighting.lightPattern.RAINBOWPARTY))
        .onFalse(new cmdUpdateDriveSpeed(DriveTrain.normalDriveSpeed));

    // right bumper on driver = active break
    Trigger rBumper_driveController = driveController.rightBumper();
    rBumper_driveController.whileTrue(new cmdActiveBrake())
        .onTrue(new cmdUpdateBaseColor(Lighting.lightPattern.RED));

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

    SmartDashboard.putNumber("cSensor", m_Gripper.getDistance());

  }

}
