package frc.robot.subsystems;

import frc.robot.commands.*;
import frc.robot.hardware.WL_Spark;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotMath;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.Spark;

/**
 *
 */
public class Arm extends SubsystemBase {

    private WL_Spark armExtend;
    private WL_Spark armRot;
    private double targetArmExtendPos = 0;
    private double targetArmRotPos = 0;
    private double targetPowerExtend = 0.80;
    private double targetPowerRot = 0.55;

    private static double maxPosExtend = 224;
    private static double minPosExtend = -1;
    private static double maxPosRot = 92;
    private static double minPosRot = 0;
    private static double tolExtend = 4.0;
    private static double tolRot = 2;
    private double stagPosExtend = 40.0;
    private double stagPosRot = 30.0;
    private double stagPowerExtend = 0.2;
    private double stagPowerRot = 0.1;

    public static double carryRotPos = 30;
    public static double carryExtendPos = 0;
    public static double deliveryHighRotPos = 80;
    public static double deliveryHighExtendPos = 220;
    // fix med and low delivery positions to where they should be
    public static double deliveryMedRotPos = 80;
    public static double deliveryMedExtendPos = 0;
    public static double deliveryLowRotPos = 60;
    public static double deliveryLowExtendPos = 0;
    public static double intakeRotPos = 0;
    public static double intakeExtPos = 0;

    private static double safetyArmRotPos = 20;
    private static double safetyArmExtPos = 7;
    private static double pauseRotPos = 0;
    private static double pauseExtPos = 0;

    private boolean bDoneRot = true;
    private boolean bDoneExt = true;

    private Mode CurrentMode = Mode.START;

    public Arm() {

        armExtend = new WL_Spark(Constants.CANID.armExtend, WL_Spark.MotorType.kBrushless);
        armExtend.setInverted(false);
        setSparkParms(armExtend);

        armRot = new WL_Spark(Constants.CANID.armRot, WL_Spark.MotorType.kBrushless);
        armRot.setInverted(true);
        setSparkParms(armRot);

    }

    private void setSparkParms(WL_Spark wls) {
        // set Spark Max params for all 4 sparks to be the same and burn them in
        // This is good practice because if you brown out they can return to default
        // or if you replace one mid comp then you do not have to worry about forgetting
        // to
        // update them via the RevClient.

        wls.setSmartCurrentLimit(25);
        wls.setIdleMode(IdleMode.kBrake);
        wls.burnFlash();
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        armExtend.set(RobotMath.goToPosStag(getArmExtendPos(),
                targetArmExtendPos, tolExtend, targetPowerExtend, stagPosExtend, stagPowerExtend));

        armRot.set(RobotMath.goToPosStag(getArmRotPos(), targetArmRotPos, tolRot, targetPowerRot, stagPosRot,
                stagPowerRot));

        if(armExtend.get() == 0){
            bDoneExt = true;
        }
        else {
            bDoneExt = false;
        }

        if(armRot.get() == 0){
            bDoneRot = true;
        }
        else{
            bDoneRot = false;
        }

        switch (CurrentMode) {

            case START:
                goToStart();
                break;

            case CARRY:
                goToCarry();
                break;

            case DELIVERLOW:
                goToDeliverLow();
                break;

            case DELIVERMED:
                goToDeliverMed();
                break;

            case DELIVERHIGH:
                goToDeliverHigh();
                break;

            case INTAKE:
                goToIntake();
                break;

            default:
                goToStart();

        }

    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run when in simulation

    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public double getArmExtendPos() {
        return armExtend.getPosition();
    }

    public double getArmRotPos() {
        return armRot.getPosition();
    }

    private void setArmExtendTargPos(double target, double stagPos, double stagPower) {
        targetArmExtendPos = RobotMath.safetyCap(target, minPosExtend, maxPosExtend);
        stagPosExtend = stagPos;
        stagPowerExtend = stagPower;
    }

    private void setArmRotTargPos(double target, double stagPos, double stagPower) {
        targetArmRotPos = RobotMath.safetyCap(target, minPosRot, maxPosRot);
        stagPosRot = stagPos;
        stagPowerRot = stagPower;
    }

    private void pauseExt() {
        pauseExtPos = targetArmExtendPos;
        targetArmExtendPos = armExtend.getPosition();
    }

    private void pauseRot() {
        pauseRotPos = targetArmRotPos;
        targetArmRotPos = armRot.getPosition();
    }

    private void unpauseExt() {
        targetArmExtendPos = pauseExtPos;
    }

    private void unpauseRot() {
        targetArmRotPos = pauseRotPos;
    }

    private void goToStart() {
        // move extention to start pos
        setArmExtendTargPos(0, stagPosExtend, stagPowerExtend);
        // move rotation to start pos
        setArmRotTargPos(0, stagPosRot, stagPosRot);
        // beware of rot safety pos
        pauseRotPos = 0;
        if ((armRot.getPosition() <= safetyArmRotPos) && (armExtend.getPosition() > safetyArmExtPos)) {
            pauseRot();
        } else {
            unpauseRot();
        }

    }

    private void goToCarry() {
        // move ext to carry pos
        setArmExtendTargPos(carryExtendPos, stagPosExtend, stagPosExtend);
        // move rot to carry pos
        setArmRotTargPos(carryRotPos, stagPosRot, stagPosRot);
        // beware of rot safety pos
        pauseRotPos = carryRotPos;
        if ((armRot.getPosition() <= safetyArmRotPos) && (armExtend.getPosition() > safetyArmExtPos)) {
            pauseRot();
        } else {
            unpauseRot();
        }

    }

    private void goToDeliverLow() {
        // move ext to deliver low pos
        setArmExtendTargPos(deliveryLowExtendPos, stagPosExtend, stagPowerExtend);
        // move rot to deliver low pos
        setArmRotTargPos(deliveryLowRotPos, stagPosRot, stagPowerRot);
        // beware of rot safety pos
        pauseExtPos = deliveryLowExtendPos;

        if (armRot.getPosition() < safetyArmRotPos) {
            pauseExt();
        } else {
            unpauseExt();
        }

    }

    private void goToDeliverMed() {
        // move ext to deliver med pos
        setArmExtendTargPos(deliveryMedExtendPos, stagPosExtend, stagPowerExtend);
        // move rot to deliver med pos
        setArmRotTargPos(deliveryMedRotPos, stagPosRot, stagPowerRot);
        // beware of rot safety pos
        pauseExtPos = deliveryMedExtendPos;

        if (armRot.getPosition() < safetyArmRotPos) {
            pauseExt();
        } else {
            unpauseExt();
        }
    }

    private void goToDeliverHigh() {
        // move ext to deliver high pos
        setArmExtendTargPos(deliveryHighExtendPos, stagPosExtend, stagPowerExtend);
        // move rot to deliver high pos
        setArmRotTargPos(deliveryHighRotPos, stagPosRot, stagPowerRot);
        // beware of rot safety pos
        pauseExtPos = deliveryHighExtendPos;

        if (armRot.getPosition() < safetyArmRotPos) {
            pauseExt();
        } else {
            unpauseExt();
        }
    }

    private void goToIntake() {
        setArmExtendTargPos(intakeExtPos, stagPosExtend, stagPowerExtend);
        setArmRotTargPos(intakeRotPos, stagPosRot, stagPowerRot);
        pauseRotPos = intakeRotPos;
        if ((armRot.getPosition() <= safetyArmRotPos) && (armExtend.getPosition() > safetyArmExtPos)) {
            pauseRot();
        } else {
            unpauseRot();
        }
    }

    public void setCurrentMode(Mode newMode) {
        CurrentMode = newMode;
    }

    public boolean isInPos(){
        if((bDoneRot) && (bDoneExt)){
            return true;
        }
        else{
            return false;
        }
    }

    public enum Mode {
        START,
        CARRY,
        DELIVERLOW,
        DELIVERMED,
        DELIVERHIGH,
        INTAKE;
    }

}
