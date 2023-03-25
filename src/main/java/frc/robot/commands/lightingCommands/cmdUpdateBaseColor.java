package frc.robot.commands.lightingCommands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Lighting;
import frc.robot.subsystems.Lighting.lightPattern;


public class cmdUpdateBaseColor extends CommandBase {

    private String newColor;
    private double newVal;

    public cmdUpdateBaseColor(String newCol) {
        newColor = newCol;
        newVal = Lighting.lightPattern.valueOf(newColor).getValue();

    }

    public cmdUpdateBaseColor(lightPattern newBaseColor){
        newVal = newBaseColor.getValue();
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        
        RobotContainer.getInstance().m_Lighting.setNewBaseColor(newVal);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public boolean runsWhenDisabled() {

        return false;

    }
}
