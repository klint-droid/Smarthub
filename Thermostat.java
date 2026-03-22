import java.util.Scanner;

import javax.swing.JOptionPane;

public class Thermostat extends BaseDevice implements SmartDevice {
    private double currentTemp;
    private double targetTemp = 0.0;

    public Thermostat(String name, double currentTemp){
        super(name);
        setCurrentTemp(currentTemp);
    }

    public double getCurrentTemp() {
        return currentTemp;
    }

    public void setCurrentTemp(double currentTemp) {
        this.currentTemp = currentTemp;
    }

    public double getTargetTemp() {
        return targetTemp;
    }

    public void setTargetTemp(double targetTemp) {
        if(targetTemp >= 16 && targetTemp <= 30){
            this.targetTemp = targetTemp;
        } else {
            throw new IllegalArgumentException("Target temperature is out of range");
        }
    }
    public void viewState() {
            JOptionPane.showMessageDialog(null,"Current temperature: " + currentTemp + "°C, Target temperature: " + targetTemp + "°C");
    }
    public void modifySettings(Scanner scanner) {
        while(true){
            try {
                String inputTemp = JOptionPane.showInputDialog("Enter target temperature(16-30):");
                
                if(inputTemp == null){
                    return;
                }
                double targetTempInput = Double.parseDouble(inputTemp);
                setTargetTemp(targetTempInput);
                break;

            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(null, e.getMessage() + "\nPlease try again.");
            }
        }
        JOptionPane.showMessageDialog(null, "Settings modified. Target temperature set to " + targetTemp + "°C.");
    }
    public void execute() {
        setCurrentTemp(getTargetTemp());
        String message = "Executing thermostat...Target temperature set to " + getCurrentTemp() + "°C.";
        JOptionPane.showMessageDialog(null, message);
    }
}
