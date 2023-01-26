package frc.robot.subsystems;


import frc.robot.commands.*;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.Spark;



/**
 *
 */
public class TemplateSubsystem extends SubsystemBase {

private Spark templateMotor;


    
    /**
    *
    */
    public TemplateSubsystem() {

templateMotor = new Spark(4);
 addChild("TemplateMotor",templateMotor);
 templateMotor.setInverted(false);


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

