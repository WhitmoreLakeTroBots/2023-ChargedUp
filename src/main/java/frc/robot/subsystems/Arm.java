// ROBOTBUILDER TYPE: Subsystem.

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
    private double targetPower = 0.15;
    private static double maxPos = 10;
    private static double minPos = -1;
    private static double tol = 0.5;



    
    
    public Arm() {

       armExtend = new WL_Spark(Constants.CANID.armExtend,WL_Spark.MotorType.kBrushless);
    armExtend.setInverted(false);
    setSparkParms(armExtend);

    armRot = new WL_Spark(Constants.CANID.armRot,WL_Spark.MotorType.kBrushless);
    armRot.setInverted(false);
    setSparkParms(armRot);




    }

    private void setSparkParms(WL_Spark wls) {
        // set Spark Max params for all 4 sparks to be the same and burn them in
        // This is good practice because if you brown out they can return to default
        // or if you replace one mid comp then you do not have to worry about forgetting to 
        // update them via the RevClient.

        wls.setSmartCurrentLimit(25);
        wls.setIdleMode(IdleMode.kBrake);
        wls.burnFlash();
    }


    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        armExtend.set( RobotMath.goToPos(getArmExtendPos(), targetArmExtendPos, tol, targetPower));

        armRot.set(RobotMath.goToPos(getArmRotPos(), targetArmRotPos, tol, targetPower));

    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run when in simulation

    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.


    public double getArmExtendPos(){
        return armExtend.getPosition();
    }

    public double getArmRotPos(){
        return armRot.getPosition();
    }

    public void setArmExtendTargPos(double target){
        targetArmExtendPos = RobotMath.safetyCap(target, minPos, maxPos);
    }

    public void setArmRotTargPos(double target){
        targetArmRotPos = RobotMath.safetyCap(target, minPos, maxPos);
    }

}

