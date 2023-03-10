package frc.robot.subsystems;



import frc.robot.RobotMath;
//import frc.robot.commands.*;
//import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


/**
 *
 */
public class WL_SubVibration extends SubsystemBase {
    // Add Constants here.....

    // we need to think of some patterns for vibration execution.
    // I'm thinking we setup low / medium / high strenght
    // Also thinking durations short / Med / Long
    // and Side for patterns. Billcontroller.hand.left or .right

    private static double lowIntensity = 0.3;
    private static double MedIntensity = 0.5;
    private static double HighIntensity = 0.8;

    private static double ShortDuration = 0.5; // sec
    private static double MediumDuration = 1.0;
    private static double LongDuration = 1.5;

    //private double StartTime ;

    private double lEndTime;
    private double rEndTime;
    
    private double lIntensity;
    private double rIntensity;

    private VibType vType;

    public WL_SubVibration() {
        // Initialize constants here

       // StartTime = RobotMath.getTime();

    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run

        //RunVibration();

        ExecVibLeft();
        ExecVibRight();

    }

    public void start() {
        // Start hardware here
    }

    public void stop() {
        // stop hardware here
    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run when in simulation

    }

    private void ExecVibLeft(){
        if (RobotMath.getTime() <= lEndTime) {

       //     RobotContainer.getInstance().Xbox.setRumble(HAND.LEFT, lIntensity);
        }
        else {
       //     RobotContainer.getInstance().Xbox.setRumble(HAND.LEFT, 0.0);
        }
    }

    private void ExecVibRight(){
        if (RobotMath.getTime() <= rEndTime) {

       //     RobotContainer.getInstance().Xbox.setRumble(HAND.RIGHT, rIntensity);
        }
        else{
        //    RobotContainer.getInstance().Xbox.setRumble(HAND.RIGHT, 0.0);
        }

    }

    
    public double CalcEndtime(double ActDuration) {
        return RobotMath.getTime() + ActDuration;
    }

    public void SetVib(VibType VMode) {
        vType = VMode;
       // StartTime = RobotMath.getTime();
        RunVibration();
    }

    private void RunVibration() {

        switch (vType) {

            case STOP:
                // StopVib
                //ExecVib(HAND.LEFT, 0.0, CalcEndtime(0));
                lIntensity = 0.0;
                lEndTime = CalcEndtime(0.0);
                //ExecVib(HAND.RIGHT, 0.0, CalcEndtime(0));
                rIntensity = 0.0;
                rEndTime = CalcEndtime(0.0);
                break;

            case TargetSeen:
                // vib target sean
                // lowIntensity, LongDuration LeftSide;
                //ExecVib(HAND.LEFT, lowIntensity, CalcEndtime(LongDuration));
                lIntensity = lowIntensity;
                lEndTime = CalcEndtime(LongDuration);
                vType = VibType.STOP;
                break;

            case TargetLock:
                // vib target lock
                // medIntensity, MedDuration RightSide
                //ExecVib(HAND.RIGHT, MedIntensity, CalcEndtime(MediumDuration));
                lIntensity = HighIntensity;
                lEndTime = CalcEndtime(MediumDuration);
                vType = VibType.STOP;
                break;

            case LauncherAutoSpeed:
                // vib speed audo
                // HighIntensity, ShortDuration, LeftSide
                //ExecVib(HAND.LEFT, HighIntensity, CalcEndtime(ShortDuration));
                lIntensity = HighIntensity;
                lEndTime = CalcEndtime(ShortDuration);
                vType = VibType.STOP;
                break;

            case LauncherFixedSpeed:
                // vib speed fixed
                // MedIntensity, MedDuration, LeftSide
                //ExecVib(HAND.LEFT, MedIntensity, CalcEndtime(MediumDuration));
                lIntensity = MedIntensity;
                lEndTime = CalcEndtime(MediumDuration);
                vType = VibType.STOP;
                break;

            case LauncherSpeedSet:

                // vib speed set
                // MedIntensity, MedDuration, RightSide
                //ExecVib(HAND.RIGHT, MedIntensity, CalcEndtime(MediumDuration));
                rIntensity = lowIntensity;
                rEndTime =CalcEndtime(ShortDuration);
                vType = VibType.STOP;
                break;

                case BallCaptured:
                rIntensity = HighIntensity;
                rEndTime =CalcEndtime(ShortDuration);
                vType = VibType.STOP;
                break;
        }
    }

    public enum VibType {
        STOP,
        TargetSeen,
        TargetLock,
        LauncherFixedSpeed,
        LauncherAutoSpeed,
        LauncherSpeedSet,
        BallCaptured;
    }

}
