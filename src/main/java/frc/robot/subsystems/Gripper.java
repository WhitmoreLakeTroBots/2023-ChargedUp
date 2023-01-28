package frc.robot.subsystems;

import frc.robot.commands.*;
import frc.robot.Constants;
import com.revrobotics.CANSparkMax.IdleMode;
import frc.robot.hardware.WL_Spark;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.Spark;

/**
 *
 */
public class Gripper extends SubsystemBase {

    private WL_Spark gripperMoter;

    /**
    *
    */
    public Gripper() {

        gripperMoter = new WL_Spark(Constants.CANID.gripper, WL_Spark.MotorType.kBrushless);
        gripperMoter.setInverted(false);
        setSparkParms(gripperMoter);

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

    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run when in simulation

    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

}
