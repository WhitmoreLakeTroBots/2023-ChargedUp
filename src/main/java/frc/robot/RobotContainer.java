package frc.robot;

import frc.robot.commands.AutonCommands.A_B_P1_V2;
import frc.robot.commands.AutonCommands.A_B_P3_V2;
import frc.robot.commands.AutonCommands.Auto_Charge2_V1;
import frc.robot.commands.AutonCommands.Auto_Charge7_V1;
import frc.robot.commands.AutonCommands.Auto_Charge7_V2;
import frc.robot.commands.AutonCommands.Auto_V1;
import frc.robot.commands.armCommands.cmdSetArmMode;
import frc.robot.commands.armCommands.cmdSetGripperPos;
import frc.robot.commands.driveCommands.cmdActiveBrake;
import frc.robot.commands.driveCommands.cmdDriveStraight;
import frc.robot.commands.driveCommands.cmdResetGyro;
import frc.robot.commands.driveCommands.cmdUpdateDriveSpeed;
import frc.robot.commands.intakeCommands.cmdReverseIntake;
import frc.robot.commands.intakeCommands.cmdStartIntake;
import frc.robot.commands.intakeCommands.cmdStopIntake;
import frc.robot.commands.intakeCommands.cmdStopIntakeRot;
import frc.robot.commands.lightingCommands.cmdUpdateBaseColor;
import frc.robot.commands.visionCommands.cmdDisableAutoDrive;
import frc.robot.commands.visionCommands.cmdDriveToTarget;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj2.command.Command;

//import org.apache.commons.io.filefilter.TrueFileFilter;

import edu.wpi.first.wpilibj.XboxController;

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
  public final SubGyro m_subGyro = new SubGyro();
  public final DriveTrain m_driveTrain = new DriveTrain();
  public final Lighting m_Lighting = new Lighting();
  public final Arm m_arm = new Arm();
  public final SubPoseEstimator m_Estimator = new SubPoseEstimator();
  public final Gripper m_Gripper = new Gripper();
  public final Intake m_Intake = new Intake();

  // Joysticks
  private final CommandXboxController driveController = new CommandXboxController(0);
  private final CommandXboxController articController = new CommandXboxController(1);

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
    m_chooser.setDefaultOption("Auto V1", new Auto_V1());
    m_chooser.addOption("Auto V1", new Auto_V1());
    m_chooser.addOption("Auto Blue P1", new A_B_P1_V2());
    m_chooser.addOption("Auto Blue P3", new A_B_P3_V2());
    //m_chooser.addOption("active break", new cmdActiveBrake());
    //m_chooser.addOption("drive straight", new cmdDriveStraight(120, 0.6));
   // m_chooser.addOption("bluecube1", new cmdDriveToTarget(SubPoseEstimator.targetPoses.BLUE_GAME_PIECE_1, 0.40));
    m_chooser.addOption("Auto charge 2 red", new Auto_Charge2_V1());
    m_chooser.addOption("Auto charge 7 blue", new Auto_Charge7_V1());
    
    // m_chooser.addOption("Auto charge 7 blue 2", new Auto_Charge7_V2());

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
    // SmartDashboard.putData("active break", new cmdActiveBrake());
    // SmartDashboard.putData("Extend arm Pos", new
    // cmdSetArmMode(Arm.Mode.DELIVERHIGH, false));
    // SmartDashboard.putData("arm Pos Intake", new cmdSetArmMode(Arm.Mode.INTAKE,
    // false));
    // SmartDashboard.putData("set color red", new
    // cmdUpdateBaseColor(lightPattern.RED));
    // SmartDashboard.putData("set color blue", new
    // cmdUpdateBaseColor(lightPattern.BLUE));

    // SmartDashboard.putData("Grip Open", new cmdSetGripperPos(Gripper.openPos,
    // false));
    // SmartDashboard.putData("Grip Cone", new
    // cmdSetGripperPos(Gripper.coneClosePos, false));
    // SmartDashboard.putData("Grip cube", new
    // cmdSetGripperPos(Gripper.cubeClosePos, false));
    // SmartDashboard.putData("auton1test", new A_R_P1_V1());

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
        .onTrue(new cmdUpdateBaseColor(Lighting.lightPattern.YELLOW));

    // X is gripper close cube
    Trigger X_ArticButton = articController.x();
    X_ArticButton.onTrue(new cmdSetGripperPos(Gripper.cubeClosePos, false))
        .onTrue(new cmdUpdateBaseColor(Lighting.lightPattern.VIOLET));

    // Y is gripper open
    Trigger Y_ArticButton = articController.y();
    Y_ArticButton.onTrue(new cmdSetGripperPos(Gripper.openPos, false));

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

    // Start intake artic
    Trigger rTrigger_ArticButton = articController.rightTrigger();
    rTrigger_ArticButton.whileTrue(new cmdReverseIntake())
        .onFalse(new cmdStopIntake())
        .onTrue(new cmdUpdateBaseColor(Lighting.lightPattern.LAWNGREEN));

    // Stop intake artic
    Trigger rBumper_ArticButton = articController.rightBumper();
    rBumper_ArticButton.onTrue(new cmdStopIntake())
        .onTrue(new cmdUpdateBaseColor(Lighting.lightPattern.SKYBLUE));
    
   // Trigger lBumper_ArticButton = articController.leftBumper();
    //lBumper_ArticButton.onTrue(new cmdIntakePos(Intake.outPos, false));

    Trigger lTrigger_ArticButton = articController.leftTrigger();
    lTrigger_ArticButton.whileTrue(new cmdStartIntake())
    .onFalse(new cmdStopIntake());

    //start button E stop
    Trigger start_ArticButton = articController.start();
    start_ArticButton.onTrue(new cmdStopIntake())
    .onTrue(new cmdSetArmMode(Arm.Mode.STOP, false))
    .onTrue(new cmdStopIntakeRot());


    // left trigger on driver = boost
    Trigger lTrigger_driveController = driveController.leftTrigger();
    lTrigger_driveController.onTrue(new cmdUpdateDriveSpeed(DriveTrain.boostSpeed))
        .onFalse(new cmdUpdateDriveSpeed(DriveTrain.normalDriveSpeed));

    // right trigger on driver is AUTO DRIVE
    Trigger rTrigger_driveController = driveController.rightTrigger();
   rTrigger_driveController
   // .whileTrue(new cmdDriveStraight(72, 0.4))
        .whileTrue(new cmdDriveToTarget(SubPoseEstimator.targetPoses.RED_GAME_PIECE_1, 0.4))
        .onTrue(new cmdUpdateBaseColor(Lighting.lightPattern.RAINBOW))
        .onFalse(new cmdDisableAutoDrive());

    // right bumper on driver = active break
    // Trigger rBumper_driveController = driveController.rightBumper();
    // rBumper_driveController.whileTrue(new cmdActiveBrake())
    // .onTrue(new cmdUpdateBaseColor(Lighting.lightPattern.RED));

    // dDown on driver is active break
    Trigger dDown_driveController = driveController.povDown();
    dDown_driveController.whileTrue(new cmdActiveBrake())
        .onTrue(new cmdUpdateBaseColor(Lighting.lightPattern.RED));

    // Back button on driver, reset navx
    Trigger rBack_driveController = driveController.back();
    rBack_driveController.onTrue(new cmdResetGyro())
        .onTrue(new cmdUpdateBaseColor(Lighting.lightPattern.WHITE));

    Trigger a_driveController = driveController.a();
    a_driveController.whileTrue(new cmdActiveBrake())
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
    SmartDashboard.putNumber("roll", m_subGyro.getroll());
    SmartDashboard.putNumber("pitch", m_subGyro.getpitch());
    SmartDashboard.putNumber("yaw", m_subGyro.getyaw());

    SmartDashboard.putNumber("ArmExtendPos", m_arm.getArmExtendPos());
    SmartDashboard.putNumber("ArmRotPos", m_arm.getArmRotPos());
    SmartDashboard.putNumber("gripperpos", m_Gripper.getGripPos());
    SmartDashboard.putNumber("Intake Velocity", m_Intake.getIntakeVelocity());
    SmartDashboard.putNumber("Intake Current", m_Intake.getIntakeCur());
    SmartDashboard.putNumber("Intake Rotation",m_Intake.getIntakeRotPos());
    // SmartDashboard.putNumber("LDM1POS", m_driveTrain.lDM1.getPositionABS());

    SmartDashboard.putNumber("cSensor", m_Gripper.getDistance());

    //SmartDashboard.putNumber("Field_x", m_Estimator.getFieldX());
    //SmartDashboard.putNumber("Field_y", m_Estimator.getFieldY());
    //SmartDashboard.putNumber("Field_z", m_Estimator.getFieldZ());
    //SmartDashboard.putNumber("dist to x", m_Estimator.diffX);
    //SmartDashboard.putNumber("dist to y", m_Estimator.diffY);
    //SmartDashboard.putNumber("calc diff x", m_Estimator.calcDiffX);
    //SmartDashboard.putNumber("calc diff y", m_Estimator.calcDiffY);
    //SmartDashboard.putNumber("Field_yaw", Math.toDegrees(m_Estimator.getFieldYawRad()));
    //SmartDashboard.putNumber("Field_pitch", Math.toDegrees(m_Estimator.getFieldPitchRad()));
    //SmartDashboard.putNumber("Field_roll", Math.toDegrees(m_Estimator.getFieldRollRad()));

    SmartDashboard.putNumber("cam11_x", m_Estimator.getCameraX());
    SmartDashboard.putNumber("cam11_y", m_Estimator.getCameraY());
    SmartDashboard.putNumber("cam11_z", m_Estimator.getCameraZ());
    SmartDashboard.putNumber("TagID", m_Estimator.getFiducialId());

    SmartDashboard.putNumber("Drive distance", m_driveTrain.getDriveDist());
    SmartDashboard.putNumber("Strafe distance", m_driveTrain.getStrafeDist());
  }

}
