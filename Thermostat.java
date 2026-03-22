import java.util.Scanner;

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
        System.out.println("Current temperature: " + currentTemp + "°C, Target temperature: " + targetTemp + "°C");
    }
    public void modifySettings(Scanner scanner) {
        while(true){
            try {
                System.out.println("Enter target temperature(16-30): ");
                int temp = scanner.nextInt();
                setTargetTemp(temp);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                System.out.println("Please try again.");
            }
        }
        System.out.println("Settings modified. Target temperature set to " + targetTemp + "°C.");
    }
    public void execute() {
        System.out.println("Executing thermostat...");
        setCurrentTemp(getTargetTemp());
        System.out.println("Target temperature set to " + getCurrentTemp()+ "°C.");
    }
}
