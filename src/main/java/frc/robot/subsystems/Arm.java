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
    private double targetPowerExtend = 0.50;
    private double targetPowerRot = 0.40;
    private static double maxPosExtend = 224;
    private static double minPosExtend = -1;
    private static double maxPosRot = 39;
    private static double minPosRot = 0;
    private static double tolExtend = 5.0;
    private static double tolRot = 0.35;
    private double stagPosExtend = 1.0;
    private double stagPosRot = 2.0;
    private double stagPowerExtend = 1.0;
    private double stagPowerRot = 0.2;



    
    
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
        armExtend.set( RobotMath.goToPosStag(getArmExtendPos(), targetArmExtendPos, tolExtend, targetPowerExtend,stagPosExtend,stagPowerExtend));

        armRot.set(RobotMath.goToPosStag(getArmRotPos(), targetArmRotPos, tolRot, targetPowerRot,stagPosRot,stagPowerRot));

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

    public void setArmExtendTargPos(double target,double stagPos, double stagPower){
        targetArmExtendPos = RobotMath.safetyCap(target, minPosExtend, maxPosExtend);
        stagPosExtend = stagPos;
        stagPowerExtend = stagPower;
    }

    public void setArmRotTargPos(double target, double stagPos, double stagPower){
        targetArmRotPos = RobotMath.safetyCap(target, minPosRot, maxPosRot);
        stagPosRot = stagPos;
        stagPowerRot = stagPower;
    }

}

