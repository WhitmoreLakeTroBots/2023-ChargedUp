package frc.robot.subsystems;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.CommonLogic;
import frc.robot.Constants;
// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import frc.robot.RobotMath;
import frc.robot.hardware.WL_Spark;

/**
 *
 */
public class DriveTrain extends SubsystemBase {

    private WL_Spark lDM1;
    private WL_Spark lDM2;
    private WL_Spark rDM1;
    private WL_Spark rDM2;

    private double wheelDiameter = 8; // in inches
    private double gearRatio = 10.71; // Motor to wheel ratio was 12.75
    private double teleopPower = .40;
    public static double normalDriveSpeed = 0.50;
    public static double boostSpeed = 1.0;

    private boolean bGoToPos = false;
    private double driveDist = 0.0;
    private double strafeDist = 0.0;
    private double heading = 0.0;
    private double travelDist = 0.0;
    private double drivePower = 0.0;
    private boolean bComplete = true;
    public double gyroHeading = 0.0; 

    private double lDM1Power;
    private double lDM2Power;
    private double rDM1Power;
    private double rDM2Power;

    //private final double maxrpm = 5676;

    public final double kp_driveStraightGyro = 0.006;

    private double joyDeadBand = 0.03;

    /**
    *
    */
    public DriveTrain() {

        lDM1 = new WL_Spark(Constants.CANID.lDM1, WL_Spark.MotorType.kBrushless);
        lDM1.restoreFactoryDefaults();
        lDM1.setInverted(false);
        setSparkParms(lDM1);

        lDM2 = new WL_Spark(Constants.CANID.lDM2, WL_Spark.MotorType.kBrushless);
        lDM2.restoreFactoryDefaults();
        lDM2.setInverted(false);
        setSparkParms(lDM2);

        rDM1 = new WL_Spark(Constants.CANID.rDM1, WL_Spark.MotorType.kBrushless);
        rDM1.restoreFactoryDefaults();
        rDM1.setInverted(true);
        setSparkParms(rDM1);

        rDM2 = new WL_Spark(Constants.CANID.rDM2, WL_Spark.MotorType.kBrushless);
        rDM2.restoreFactoryDefaults();
        rDM2.setInverted(true);
        setSparkParms(rDM2);

    }

    private void setSparkParms(WL_Spark wls) {
        // set Spark Max params for all 4 sparks to be the same and burn them in
        // This is good practice because if you brown out they can return to default
        // or if you replace one mid comp then you do not have to worry about forgetting
        // to
        // update them via the RevClient.

        wls.setSmartCurrentLimit(40);
        wls.setIdleMode(IdleMode.kBrake);
        wls.burnFlash();

    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run

        if(bGoToPos){
            driveGoToPos();

        }

    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run when in simulation

    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void CMDteleOp(CommandXboxController driveController) {
       if (bGoToPos == false){
        doDrive(-1 * CommonLogic.joyDeadBand(driveController.getLeftY(), joyDeadBand),
                CommonLogic.joyDeadBand(driveController.getLeftX(), joyDeadBand),
                CommonLogic.joyDeadBand(driveController.getRightX(), joyDeadBand), teleopPower);
       }
    }

    public void doDrive(double drive, double strafe, double turn, double speed) {
        double Drive = drive;
        double Strafe = strafe;
        double Turn = turn;

        lDM1Power = Drive + Strafe + Turn;
        rDM1Power = Drive - Strafe - Turn;
        lDM2Power = Drive - Strafe + Turn;
        rDM2Power = Drive + Strafe - Turn;
        scale();
        SetMotorPower(speed);
        System.err.print("LDMPower " + lDM1Power);
    }

    public double getMaxValue() {
        double Max = -1;
        if (Math.abs(lDM1Power) > Max) {
            Max = Math.abs(lDM1Power);
        }
        if (Math.abs(lDM2Power) > Max) {
            Max = Math.abs(lDM2Power);
        }
        if (Math.abs(rDM1Power) > Max) {
            Max = Math.abs(rDM1Power);
        }
        if (Math.abs(rDM2Power) > Max) {
            Max = Math.abs(rDM2Power);
        }
        return Max;
    }

    public void scale() {
        double Max = getMaxValue();
        if (Max > 1) {

            lDM1Power = lDM1Power / Max;
            lDM2Power = lDM2Power / Max;
            rDM1Power = rDM1Power / Max;
            rDM2Power = rDM2Power / Max;
        }
    }

    public void SetMotorPower(double speed) {
        lDM1.set(lDM1Power * speed);
        lDM2.set(lDM2Power * speed);
        rDM1.set(rDM1Power * speed);
        rDM2.set(rDM2Power * speed);

    }

    public void StopDrive() {
        lDM1.set(0);
        lDM2.set(0);
        rDM1.set(0);
        rDM2.set(0);
    }

    public void resetEncoders() {
        lDM1.resetEncoder();
        lDM2.resetEncoder();
        rDM1.resetEncoder();
        rDM2.resetEncoder();
    }

    public double getDistanceTraveledInches() {
        double motorAverageRotations = (lDM1.getPositionABS() + lDM2.getPositionABS() + rDM1.getPositionABS()
                + rDM2.getPositionABS()) / 4;
        return (motorAverageRotations * wheelDiameter * Math.PI) / gearRatio;
    }

    public void activeBrake(double power) {

        lDM1.set(RobotMath.goToPos(lDM1.getPosition(), 0, 0.1, power));
        lDM2.set(RobotMath.goToPos(lDM2.getPosition(), 0, 0.1, power));
        rDM1.set(RobotMath.goToPos(rDM1.getPosition(), 0, 0.1, power));
        rDM2.set(RobotMath.goToPos(rDM2.getPosition(), 0, 0.1, power));
    }
    public void setMaxSpeed(double newSpeed){
        teleopPower = RobotMath.safetyCap(newSpeed, 0.40, 1);
    }

    private void driveGoToPos(){

        double headingDelta = RobotMath.calcTurnRate(gyroHeading,
        this.heading, kp_driveStraightGyro);

        if (travelDist <= getDistanceTraveledInches()) {
            bComplete = true;
            // end(false);
            this.StopDrive();
        }
       else { doDrive(driveDist, strafeDist, headingDelta, drivePower);
    }
}

    public void cmdGoToPos(double drive, double strafe, double heading, double mPower){
        // calculate drive power and calculate strafe power
        bComplete = false;
        this.driveDist = drive;
        this.strafeDist = strafe;
        this.heading = heading;
        this.drivePower = mPower;
        // calculate drive hypotenuse and save 
        this.travelDist = Math.sqrt(Math.pow(driveDist, 2) + Math.pow(strafeDist, 2));
    }
    public void enableGoToPos() {
        bGoToPos = true;
    }
    public void disableGoToPos() {
        bGoToPos = false;
    }
    public boolean isComplete(){
        return bComplete;
    }
    public double getDriveDist(){return driveDist;}
    public double getStrafeDist(){return strafeDist;}
}
