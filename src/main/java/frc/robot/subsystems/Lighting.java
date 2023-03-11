package frc.robot.subsystems;


import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.motorcontrol.Spark;

/**
 *
 */
public class Lighting extends SubsystemBase {
    private double curBaseColor = -0.45;
    private double newBaseColor = -0.45;
    private double tempColor = 0;
    private double duration = 2000; // in ms
    private Spark ledDriver;

    public Lighting() {
        ledDriver = new Spark(Constants.PWM.ledDriver);

    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run

    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run when in simulation

    }

    public void setNewBaseColor(double newCol) {
        newBaseColor = newCol;
        ledDriver.set(newBaseColor);
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public enum lightPattern {
        RAINBOWOCEAN("rainbow ocean", -0.95),
        RAINBOWLAVA("rainbow lava", -0.93),
        RAINBOWPARTY("rainbow party", -0.97),
        RAINBOW("rainbow rainbow", -0.99),
        CONFETTI("confetti", -0.87),
        RAINWAVES("rainbow waves", -0.45),
        HOTPINK("hot pink", 0.57),
        DARKRED("dark red", 0.59),
        RED("red", 0.61),
        REDORANGE("red orange", 0.63),
        ORANGE("orange", 0.65),
        GOLD("gold", 0.67),
        YELLOW("yellow", 0.69),
        LAWNGREEN("lawn green", 0.71),
        LIME("lime", 0.73),
        DARKGREEN("dark green", 0.75),
        GREEN("green", 0.77),
        BLUEGREEN("blue green", 0.79),
        AQUA("aqua", 0.81),
        SKYBLUE("sky blue", 0.83),
        DARKBLUE("dark blue", 0.85),
        BLUE("blue", 0.87),
        BLUEVIOLET("blue violet", 0.89),
        VIOLET("violet", 0.91),
        WHITE("white", 0.93),
        GRAY("gray", 0.95),
        DARKGRAY("dark gray", 0.97),
        BLACK("black", 0.99);

        private final String name;
        private final double value;

        public double getValue() {
            return value;
        }

        public String getName() {
            return name;
        }

        lightPattern(String name, double value) {
            this.name = name;
            this.value = value;
        }

    }

}
