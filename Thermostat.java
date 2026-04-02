import javax.swing.*;
import java.awt.Dimension;

public class Thermostat extends BaseDevice implements SmartDevice {
    private double currentTemp;
    private double targetTemp;

    public Thermostat(String name) {
        super(name);
        this.currentTemp = 20.0;
        this.targetTemp = 20.0;
    }

    public double getCurrentTemp() {
        return currentTemp;
    }

    public double getTargetTemp() {
        return targetTemp;
    }

    public void setTargetTemp(double targetTemp) {
        if (targetTemp >= 16 && targetTemp <= 30) {
            this.targetTemp = targetTemp;
        } else {
            throw new IllegalArgumentException("Temperature must be between 16–30°C");
        }
    }

    public String viewState() {
        return "Device: " + getName() +
               "\nCurrent Temp: " + currentTemp + "°C" +
               "\nTarget Temp: " + targetTemp + "°C";
    }

    public String modifySettings(String input) {
        try {
            double temp = Double.parseDouble(input);
            setTargetTemp(temp);
            return "Target temperature updated to " + targetTemp + "°C";
        } catch (NumberFormatException e) {
            return "Invalid input! Please enter a number.";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    public String execute() {
        currentTemp = targetTemp;
        return "Thermostat executed.\nCurrent temperature now " + currentTemp + "°C";
    }

    public JPanel getControlPanel(JTextArea outputArea){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel currentTempLabel = new JLabel("Current Temperature: " + currentTemp + "°C");
        currentTempLabel.setAlignmentX(JComponent.LEFT_ALIGNMENT);

        JLabel targetTempLabel = new JLabel("Target Temperature: " + targetTemp + "°C");
        targetTempLabel.setAlignmentX(JComponent.LEFT_ALIGNMENT);

        JTextField targetTempField = new JTextField(String.valueOf(getCurrentTemp()));
        targetTempField.setMaximumSize(new Dimension(180, 30));
        targetTempField.setAlignmentX(JComponent.LEFT_ALIGNMENT);

        JButton setTempBtn = new JButton("Set Target Temperature");
        setTempBtn.setAlignmentX(JComponent.LEFT_ALIGNMENT);

        setTempBtn.addActionListener(e -> {
            try {
                int value = Integer.parseInt(targetTempField.getText());
                setTargetTemp(value);
                outputArea.setText("Settings updated!\n" + viewState());
            } catch (Exception ex) {
                outputArea.setText("Error: " + ex.getMessage());
            }
        });
        panel.add(currentTempLabel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(targetTempLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(targetTempField);
        panel.add(Box.createVerticalStrut(20));
        panel.add(setTempBtn);

        return panel;
    }
}