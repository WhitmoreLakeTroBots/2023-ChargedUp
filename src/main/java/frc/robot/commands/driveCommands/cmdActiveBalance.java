package frc.robot.commands.driveCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.RobotMath;
import frc.robot.hardware.WL_Spark;
import frc.robot.PID;
import frc.robot.Robot;
/**
 *
 */
public class cmdActiveBalance extends CommandBase {
    private double targetPosition = 0; // inches
    private double brakePower = 0.08; // active Break Power
    private double currPower = 0.0;
    private double targetHeading = 0;
    private boolean bDone = false;
    private boolean encoders_need_reset = false;

    private final double pitchDegTol = 5;

    private final double kp_balance = 0.00006;
    private final double ki_balance = 0.0000025;
    private final double kd_balance = 0.000000025;

    private PID pid_Balance = new PID(kp_balance, ki_balance, kd_balance);

    public cmdActiveBalance() {


    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        bDone = false;
        RobotContainer.getInstance().m_driveTrain.resetEncoders();
        targetHeading = RobotContainer.getInstance().m_subGyro.getNormaliziedNavxAngle();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {

        double currHeading = RobotContainer.getInstance().m_subGyro.getNormaliziedNavxAngle();
        double currPitch = RobotContainer.getInstance().m_subGyro.getroll(); //navx rotated 90 degrees on robot
        double turnRate = RobotMath.calcTurnRate(currHeading, targetHeading,
                RobotContainer.getInstance().m_driveTrain.kp_driveStraightGyro * 2 );


        double correctivePitchPower = this.brakePower + Math.abs (pid_Balance.calcPID (0.0 ,currPitch));
        if (currPitch > pitchDegTol) {
            encoders_need_reset = true;
            RobotContainer.getInstance().m_driveTrain.doDrive(correctivePitchPower, 0, turnRate, 1);
        } else if (currPitch < -pitchDegTol) {
            encoders_need_reset = true;
            RobotContainer.getInstance().m_driveTrain.doDrive(-correctivePitchPower, 0, turnRate, 1);
        } else {
            if (encoders_need_reset == true){
                RobotContainer.getInstance().m_driveTrain.resetEncoders();
                encoders_need_reset = false;
            }
            pid_Balance.resetErrors();
            RobotContainer.getInstance().m_driveTrain.activeBrake(this.brakePower);
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        RobotContainer.getInstance().m_driveTrain.StopDrive();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return bDone;
    }

    @Override
    public boolean runsWhenDisabled() {

        return false;

    }
}
