import javax.swing.JOptionPane;
import java.util.*;

public class SmartLock extends BaseDevice implements SmartDevice {

    private boolean locked = true;
    private String pin = "2026";
    private List<String> failedAttempts = new ArrayList<>();
    private int attemptCount = 0;

    public SmartLock(String name) {
        super(name);
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public int getAttemptCount() {
        return attemptCount;
    }

    @Override
    public void viewState() {
        String info = "Device: " + getName()
                + "\nStatus: " + (locked ? "LOCKED" : "UNLOCKED")
                + "\nFailed Attempts: " + attemptCount;

        JOptionPane.showMessageDialog(null, info);
    }

    @Override
    public void modifySettings(Scanner scanner) {
        attemptCount = 0;
        while (attemptCount < 3) {
            try {
                String input = JOptionPane.showInputDialog(
                        null,
                        "Enter 4-digit PIN:",
                        "Smart Lock",
                        JOptionPane.QUESTION_MESSAGE
                );

                if (input == null) return;

                input = input.trim();

                if (!input.matches("\\d{4}")) {
                    throw new IllegalArgumentException("PIN must be exactly 4 digits.");
                }

                if (input.equals(pin)) {
                    attemptCount = 0;
                    locked = !locked;

                    JOptionPane.showMessageDialog(
                            null,
                            "Access Granted \nDevice is now " + (locked ? "LOCKED " : "UNLOCKED ")
                    );
                    return;

                } else {
                    attemptCount++;
                    failedAttempts.add(input);

                    JOptionPane.showMessageDialog(
                            null,
                            "Incorrect PIN \nAttempt " + attemptCount + " of 3"
                    );
                }

            } catch (IllegalArgumentException e) {
                attemptCount++;

                JOptionPane.showMessageDialog(
                        null,
                        e.getMessage() + "\nAttempt " + attemptCount + " of 3"
                );
            }
        }
        locked = true;
        JOptionPane.showMessageDialog(null, "Too many failed attempts 🚫\nDevice locked.");
    }
    public void viewFailedAttempts() {

        if (failedAttempts.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No failed attempts.");
            return;
        }

        StringBuilder log = new StringBuilder("Failed Attempts:\n");

        for (String attempt : failedAttempts) {
            log.append("- ").append(attempt).append("\n");
        }

        JOptionPane.showMessageDialog(null, log.toString());
    }
    public void execute() {
        locked = true;

        JOptionPane.showMessageDialog(
                null,
                "Executing Smart Lock...\nDevice is now LOCKED 🔒"
        );
    }
}