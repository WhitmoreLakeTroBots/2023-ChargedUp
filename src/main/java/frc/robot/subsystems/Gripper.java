package frc.robot.subsystems;

import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMax.SoftLimitDirection;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotMath;
import frc.robot.hardware.WL_Spark;

/**
 *
 */
public class Gripper extends SubsystemBase {

    private WL_Spark gripperMoter;

    public static double coneClosePos = 0;
    public static double cubeClosePos = 13.75;
    private static double minPosGrip = 0;
    public static double maxPosGrip = 30;
    public static double openPos = maxPosGrip;
    private static double griptol = 1;
    private double targetGripPos = 0;
    private double stagPosGrip = 5;
    private double targetGripPower = .25;
    private double stagPowerGrip = .12;

    /**
    *
    */
    public Gripper() {

        gripperMoter = new WL_Spark(Constants.CANID.gripper, WL_Spark.MotorType.kBrushless);
        gripperMoter.setInverted(true);
        setSparkParms(gripperMoter);

    }

    private void setSparkParms(WL_Spark wls) {
        // set Spark Max params for all 4 sparks to be the same and burn them in
        // This is good practice because if you brown out they can return to default
        // or if you replace one mid comp then you do not have to worry about forgetting
        // to
        // update them via the RevClient.

        wls.setSmartCurrentLimit(10);
        wls.setIdleMode(IdleMode.kBrake);
        wls.setSoftLimit(SoftLimitDirection.kReverse, -1);
        wls.setSoftLimit(SoftLimitDirection.kForward, (float) maxPosGrip);
        wls.burnFlash();
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        gripperMoter.set(RobotMath.goToPosStag(getGripPos(), targetGripPos, griptol, targetGripPower, stagPosGrip,
                stagPowerGrip));

    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run when in simulation

    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public double getGripPos() {
        return gripperMoter.getPosition();
    }

    public void setGripPos(double target, double stagPos, double stagPower) {
        targetGripPos = RobotMath.safetyCap(target, minPosGrip, maxPosGrip);
        stagPosGrip = stagPos;
        stagPowerGrip = stagPower;
    }

    public boolean isCube() {
        return false;
    }

    public boolean isCone() {
        return false;
    }

    public boolean isEmpty() {
        return true;
    }

    public void stop() {
        stagPowerGrip = 0;
        targetGripPos = getGripPos();
    }
}