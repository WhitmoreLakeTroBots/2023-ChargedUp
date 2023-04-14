package frc.robot.commands.visionCommands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.RobotMath;
import frc.robot.subsystems.Lighting;
import frc.robot.Constants.GearTrain;



// This command accespts a tag ID, and Vision Distance to drive until you are that distance from the tag.
// encoderMaxDist_inches is a saftey to stop driving should we loose sight of the tag or maybe never see it.

public class cmdVisionDriveDistance extends CommandBase {
    int tagNum = 0;
    boolean bDone = false;

    private double targetVisionInches = 0.0;
    private double encoderMaxDistInches = 0.0;
    private double headingDeg = 0.0;
    private final double tolInches = 6.0;
    private double power = .8;
    

    public cmdVisionDriveDistance( int tagId, double targetVisionDist_inches, 
        double encoderMaxDist_inches,double gyroHeadingDeg) {

        this.tagNum = tagId;
        this.targetVisionInches = targetVisionDist_inches;
        this.encoderMaxDistInches = encoderMaxDist_inches;
        this.headingDeg = gyroHeadingDeg;
        this.power = 0.8;


    }
    public cmdVisionDriveDistance( int tagId, double targetVisionDist_inches, 
        double encoderMaxDist_inches,double gyroHeadingDeg,double drivePower) {
        this.tagNum = tagId;
        this.targetVisionInches = targetVisionDist_inches;
        this.encoderMaxDistInches = encoderMaxDist_inches;
        this.headingDeg = gyroHeadingDeg;
        this.power = drivePower;

    }


     //if fixedDist = false => stagPosition is suposed to recieve the percantage to be traversed in stag, in 0.xx format
     

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        bDone = false;
        RobotContainer.getInstance().m_driveTrain.disableGoToPos();
        RobotContainer.getInstance().m_driveTrain.resetEncoders();
        this.power = this.power*GearTrain.gearFactor; //after we changed gear ratios to keep autons same
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {

        
        double totalWheelDist = RobotContainer.getInstance().m_driveTrain.getDistanceTraveledInches();
        double totalTagDist = RobotContainer.getInstance().m_Estimator.getDistanceFromTagInInches(this.tagNum);
        System.err.println("vision distance: "+totalTagDist  + "    Wheeldist: " + totalWheelDist);
 
        
        if (RobotMath.isInRange(totalTagDist, this.targetVisionInches, this.tolInches)){ 
            System.err.println("stopped with vision");
            RobotContainer.getInstance().m_driveTrain.StopDrive();
            RobotContainer.getInstance().m_Lighting.setNewBaseColor(Lighting.lightPattern.WHITE.getValue());
            end (false);
        }
        else if (RobotMath.isInRange(totalWheelDist, this.encoderMaxDistInches, this.tolInches)){
            RobotContainer.getInstance().m_driveTrain.StopDrive();
            RobotContainer.getInstance().m_Lighting.setNewBaseColor(Lighting.lightPattern.BLACK.getValue());
            end(false);
        // Stop because we are the correct vision distance from the tag... AKA on top of the charge station    
        }
        else {
            // Keep Driving as straight as possible by gyro.
            double turnRate = RobotMath.calcTurnRate(RobotContainer.getInstance().m_subGyro.getNormaliziedNavxAngle(),
            this.headingDeg, RobotContainer.getInstance().m_driveTrain.kp_driveStraightGyro);
            RobotContainer.getInstance().m_driveTrain.doDrive(power, 0, turnRate, 1);
            RobotContainer.getInstance().m_Lighting.setNewBaseColor(Lighting.lightPattern.GREEN.getValue());
        }


    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        
        this.bDone = true;
        RobotContainer.getInstance().m_driveTrain.StopDrive();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return this.bDone;
    }

    @Override
    public boolean runsWhenDisabled() {
        return false;

    }
}
