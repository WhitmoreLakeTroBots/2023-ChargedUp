package frc.robot.subsystems;

import com.revrobotics.ColorSensorV3;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMax.SoftLimitDirection;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotMath;
import frc.robot.hardware.WL_Spark;
import frc.robot.subsystems.Arm.Mode;
import frc.robot.RobotContainer;

/**
 *
 */
public class Intake extends SubsystemBase {

    private WL_Spark intakeMotor;
    private WL_Spark rotMotor;

    public static double inPos = 0;
    public static double outPos = 13.75;
    public static double transferPos = 3.0;
    private static double outOfTheWayPos = 20.0;
    private static double minPos = 0;
    public static double maxPos = 35;
    public static double safetyPos = 15;

    private static double intakePow = 0.5;

    private static double rotTol = 1.5;
    private static double stagPower = .25;
    private static double stagRotPos = 10;

    private boolean isRunning = false;
    private boolean cubeFound = false;
    private double intakeCurrCutOff = 8;
    private intakeState currentState = intakeState.STOP;

    private double targetRotPos = 0;

    public Intake() {

        intakeMotor = new WL_Spark(Constants.CANID.intake, WL_Spark.MotorType.kBrushless);
        intakeMotor.setInverted(false);
        setSparkParms(intakeMotor);

        rotMotor = new WL_Spark(Constants.CANID.intakeRot, WL_Spark.MotorType.kBrushless);
        rotMotor.setInverted(true);
        setSparkParms(rotMotor);
    }

    private void setSparkParms(WL_Spark wls) {
        // set Spark Max params for all 4 sparks to be the same and burn them in
        // This is good practice because if you brown out they can return to default
        // or if you replace one mid comp then you do not have to worry about forgetting
        // to
        // update them via the RevClient.

        wls.setSmartCurrentLimit(15);
        wls.setIdleMode(IdleMode.kBrake);
        wls.setSoftLimit(SoftLimitDirection.kReverse, -1);
        wls.setSoftLimit(SoftLimitDirection.kForward, (float) maxPos);
        wls.burnFlash();
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        rotMotor.set(RobotMath.goToPosStag(getIntakeRotPos(), targetRotPos, rotTol, intakePow, stagRotPos,
                stagPower));


        switch (currentState) {

            case STOP: 

            break;
            case STARTING:
            if ((getIntakeCur() < intakeCurrCutOff )&&(getIntakeCur()>1)) {
                currentState = intakeState.RUNNING;
            }
            break;

            case RUNNING: 
                detectCube();
            break;
            case DETECTED:
                
            break;

            case TRANSFER:

            break;

            case STORAGE:

            break;

            case SAFETY:

            break;



            default:
        }
     }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run when in simulation

    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public double getIntakeRotPos() {
        return rotMotor.getPosition();
    }
    public double getIntakeVelocity() {
        return intakeMotor.getVelocity();
    }
    public double getIntakeCur() {
        return intakeMotor.getOutputCurrent();
    }
    public void setIntakeRotPos(double target) {
        if((RobotContainer.getInstance().m_arm.getCurrentMode() == Mode.SAFETYMODE) && 
        (RobotContainer.getInstance().m_arm.isInPos())){
            targetRotPos = RobotMath.safetyCap(target, minPos, maxPos);
        }
    }

    private void detectCube() {
        if (getIntakeCur() > intakeCurrCutOff) {
            stopIntake();
            currentState = intakeState.DETECTED;
        }

    }

    public void startIntake() {
        intakeMotor.set(intakePow);
        currentState = intakeState.STARTING;
    }

    public void stopIntake() {
        intakeMotor.set(0);
    }

    public void stopIntakeRot() {
        targetRotPos = getIntakeRotPos();
    }

    public void changePos(){
        if(RobotMath.isInRange(targetRotPos, outPos, rotTol)){
            setIntakeRotPos(transferPos);
        }
        else if(RobotMath.isInRange(targetRotPos, transferPos, rotTol)){
            setIntakeRotPos(outPos);
        }
        else{
            setIntakeRotPos(outPos);
        }
    }

    public void getOutOfWay(){
        setIntakeRotPos(outOfTheWayPos);
    }

    public void goToIn(){
        setIntakeRotPos(inPos);
    }

    public void runInReverse(){
        intakeMotor.set(-intakePow);
    }
    

    public enum intakeState {
        STOP,
        STARTING,
        RUNNING,
        DETECTED,
        TRANSFER,
        STORAGE,
        SAFETY;
    }
}
