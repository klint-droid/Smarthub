import javax.swing.*;
import java.awt.GridLayout;

public class SecurityFloodlight extends SmartLight {

    private String[] motionSensitivityOptions = {"LOW", "MEDIUM", "HIGH"};
    private int sensitivityIndex = 1;
    private boolean motionArmed = false;

    public SecurityFloodlight(String name, int brightness, String color) {
        super(name);
        setBrightness(brightness);
        setColor(color);
    }

    public boolean isMotionArmed() {
        return motionArmed;
    }

    public void setMotionArmed(boolean motionArmed) {
        this.motionArmed = motionArmed;
    }

    public String viewState() {
        return super.viewState() +
               "\nMotion Detection: " + (motionArmed ? "ON" : "OFF") +
               "\nSensitivity: " + motionSensitivityOptions[sensitivityIndex];
    }

    public String modifySettings(String input) {

        if (input == null) return "Operation cancelled.";

        input = input.toLowerCase().trim();

        if (input.equals("on")) {
            motionArmed = true;
            return "Motion detection ON";
        } 
        else if (input.equals("off")) {
            motionArmed = false;
            return "Motion detection OFF";
        } 
        else if (input.startsWith("sensitivity:")) {
            try {
                int value = Integer.parseInt(input.split(":")[1]);
                if (value < 1 || value > 3) {
                    return "Sensitivity must be 1–3";
                }
                sensitivityIndex = value - 1;
                return "Sensitivity set to " + motionSensitivityOptions[sensitivityIndex];
            } catch (Exception e) {
                return "Invalid sensitivity format. Use: sensitivity:1-3";
            }
        } 
        else {
            return super.modifySettings(input);
        }
    }

    public String triggerMotionAlert() {
        if (!motionArmed) {
            return "Motion detection is OFF.";
        }

        setBrightness(100);

        return "Motion Detected!\n" +
               "Sensitivity: " + motionSensitivityOptions[sensitivityIndex] +
               "\nBrightness set to MAX.";
    }

    public String execute() {
        setBrightness(100);
        motionArmed = true;

        return "Floodlight activated.\n" +
               triggerMotionAlert();
    }

    public JPanel getControlPanel(JTextArea outputArea){
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(10, 1, 5, 5));

        JLabel brightnessLabel = new JLabel("Brightness: " + getBrightness());
        JSlider brightnessSlider = new JSlider(0, 100, getBrightness());

        brightnessSlider.addChangeListener(e -> {
            setBrightness(brightnessSlider.getValue());
            brightnessLabel.setText("Brightness: " + getBrightness());
        });

        JTextField colorField = new JTextField(getColor());
        colorField.setBorder(BorderFactory.createTitledBorder("Color"));

        JButton applyColorBtn = new JButton("Apply Color");
        applyColorBtn.addActionListener(e -> {
            try {
                setColor(colorField.getText());
                outputArea.setText("Color updated!\n" + viewState());
            } catch (Exception ex) {
                outputArea.setText("Error: " + ex.getMessage());
            }
        });

        JLabel motionLabel = new JLabel("Motion Detection: " + (motionArmed ? "ON" : "OFF"));
        JButton motionToggleBtn = new JButton("Toggle Motion Detection");
        JButton triggerAlertBtn = new JButton("Trigger Motion Alert");

        motionToggleBtn.addActionListener(e -> {
            motionArmed = !motionArmed;

            motionLabel.setText("Motion: " + (motionArmed ? "ON" : "OFF"));
            motionToggleBtn.setText(motionArmed ? "Turn Off" : "Turn On");
            
            triggerAlertBtn.setEnabled(motionArmed);

            outputArea.setText("Motion detection " + (motionArmed ? "enabled" : "disabled") + ".\n" + viewState());
        });

        String[] options = {"LOW", "MEDIUM", "HIGH"};
        JComboBox<String> sensitivityDropdown = new JComboBox<>(options);

        sensitivityDropdown.setSelectedIndex(sensitivityIndex);

        sensitivityDropdown.addActionListener(e -> {
            sensitivityIndex = sensitivityDropdown.getSelectedIndex();
            outputArea.setText("Sensitivity set to: " + options[sensitivityIndex] + "\n\n" + viewState());
        });

        triggerAlertBtn.addActionListener(e -> {
            String result = triggerMotionAlert();
            brightnessLabel.setText("Brightness: " + getBrightness());
            outputArea.setText(result + "\n\n" + viewState());
        });

        panel.add(brightnessLabel);
        panel.add(brightnessSlider);
        panel.add(colorField);
        panel.add(applyColorBtn);
        panel.add(motionLabel);
        panel.add(motionToggleBtn);
        panel.add(new JLabel("Sensitivity:"));
        panel.add(sensitivityDropdown);
        panel.add(triggerAlertBtn);

        return panel;
    }
}