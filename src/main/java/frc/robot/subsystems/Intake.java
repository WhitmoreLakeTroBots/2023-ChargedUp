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

    public static double inPos = 2;
    public static double outPos = 84.0;
    public static double transferPos = 25.0;
    public static double outOfTheWayPos = 63.0;
    public static double minPos = 0;
    public static double maxPos = 87.0;
    //safety pos must be less than out of the way pos - 1 - tol
    public static double safetyPos = 58.0;

    private static double intakePow = 0.70;
    private static double rotPow = 0.80;

    private static double rotTol = 3;
    private static double stagPower = 0.05;
    private static double stagRotPos = 30;

    private boolean isRunning = false;
    private boolean cubeFound = false;
    private double intakeCurrCutOff = 8;
    private intakeState currentState = intakeState.STOP;
    private boolean isInPos = false;

    private double curTime;
    private double endTime;
    private double delayTime = 0.5;

    private double targetRotPos = 0;

    private double holdPow = -0.04;
    private double holdPos = 34;

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

        wls.setSmartCurrentLimit(30);
        wls.setIdleMode(IdleMode.kBrake);
        wls.setSoftLimit(SoftLimitDirection.kReverse, -1);
        wls.setSoftLimit(SoftLimitDirection.kForward, (float) maxPos);
        wls.burnFlash();
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run

        
        switch (currentState) {

            case STOP: 

            break;
            case STARTING:
            if (RobotMath.getTime() > endTime) {
              //  currentState = intakeState.RUNNING;
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

        /*rotMotor.set(RobotMath.goToPosStagHold(getIntakeRotPos(), targetRotPos, rotTol, rotPow, stagRotPos,
                stagPower,holdPow,holdPos));
        */
        rotMotor.set(RobotMath.goToPosStag(getIntakeRotPos(), targetRotPos, rotTol, rotPow, stagRotPos,
            stagPower));


        if(RobotMath.isInRange(getIntakeRotPos(), targetRotPos, rotTol)){
            isInPos = true;
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
        if((RobotContainer.getInstance().m_arm.getArmRotPos() < (Arm.intakeRotPos + Arm.tolRot))&& 
            (!RobotContainer.getInstance().m_arm.ismoving())){
            targetRotPos = RobotMath.safetyCap(target, minPos, maxPos);
            isInPos = false;
        }
        else{
          //  RobotContainer.getInstance().m_arm.setCurrentMode(Mode.SAFETYMODE);
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
       // currentState = intakeState.STARTING;
        //curTime = RobotMath.getTime();
        //endTime = curTime + delayTime;
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
    public boolean getIsInPos(){return isInPos;}
    

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
