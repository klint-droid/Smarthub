import javax.swing.JOptionPane;
import java.util.Scanner;

public class SecurityFloodlight extends SmartLight {

    private String[] motionSensitivityOptions = {"LOW", "MEDIUM", "HIGH"};
    private int sensitivityIndex = 1;
    private boolean motionArmed = false;

    public SecurityFloodlight(String name, int brightness, String color) {
        super(name, brightness, color);
    }

    public boolean isMotionArmed() {
        return motionArmed;
    }

    public void setMotionArmed(boolean motionArmed) {
        this.motionArmed = motionArmed;
    }


    public void viewState() {
        super.viewState();

        String info = "Motion Detection: " + (motionArmed ? "ON" : "OFF") +
                      "\nSensitivity: " + motionSensitivityOptions[sensitivityIndex];

        JOptionPane.showMessageDialog(null, info);
    }

    public void modifySettings(Scanner scanner) {

        while (true) {
            try {
                String input = JOptionPane.showInputDialog(
                        null,
                        "Motion Detection:\n1 = ON\n0 = OFF"
                );

                if (input == null) return;

                int value = Integer.parseInt(input.trim());

                if (value != 0 && value != 1) {
                    throw new IllegalArgumentException("Only 0 or 1 allowed.");
                }

                motionArmed = (value == 1);
                break;

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid number input.");
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }

        super.modifySettings(scanner);

        while (true) {
            try {
                String message = "Select Sensitivity:\n";

                for (int i = 0; i < motionSensitivityOptions.length; i++) {
                    message += (i + 1) + ". " + motionSensitivityOptions[i] + "\n";
                }

                String input = JOptionPane.showInputDialog(null, message);

                if (input == null) return;

                int choice = Integer.parseInt(input.trim());

                if (choice < 1 || choice > 3) {
                    throw new IllegalArgumentException("Choose 1–3 only.");
                }

                sensitivityIndex = choice - 1;
                JOptionPane.showMessageDialog(null,
                        "Sensitivity set to " + motionSensitivityOptions[sensitivityIndex]);

                break;

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid number input.");
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
    }

    public void triggerMotionAlert() {

        if (!motionArmed) {
            JOptionPane.showMessageDialog(null, "Motion detection is OFF.");
            return;
        }

        setBrightness(100);

        String message = "Motion Detected!\n"
                + "Sensitivity: " + motionSensitivityOptions[sensitivityIndex]
                + "\nBrightness set to MAX.";

        JOptionPane.showMessageDialog(null, message);
    }

    public void execute() {
        super.execute();
        triggerMotionAlert();
    }
}