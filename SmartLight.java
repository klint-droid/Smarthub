import java.util.Scanner;
import java.util.InputMismatchException;
import javax.swing.JOptionPane;

public class SmartLight extends BaseDevice implements SmartDevice{
    private int brightness;
    private String color;

    public SmartLight(String name, int brightness, String color) {
        super(name);
        setBrightness(brightness);
        setColor(color);
    }
    public int getBrightness() {
        return brightness;
    }
    public String getColor() {
        return color;
    }
    public void setBrightness(int brightness) {
        if(brightness >= 0 && brightness <= 100){
            this.brightness = brightness;
        }
        else{
            this.brightness = 100;
            throw new IllegalArgumentException("Brightness value is out of range");
        }
            
    }

    public void setColor(String color){
        if(color != null && !color.isEmpty() && !color.matches("\\d+")){
            this.color = color;
        }
        else{
            this.color = "White";
            throw new IllegalArgumentException("Color cannot be empty or number");
        }
    }

    public void viewState() {
        String info = "Device Name: " + getName() + "\nBrightness: " + getBrightness() + "\nColor: " + getColor();
        JOptionPane.showMessageDialog(null, info);
    }

    public void modifySettings(Scanner scanner) {
        int brightnessInput;
        while(true){
            try{
                String input = JOptionPane.showInputDialog("Enter brightness (0-100):");

                if(input == null){
                    return;
                }
                brightnessInput = Integer.parseInt(input);
                setBrightness(brightnessInput);
                break;
            } catch (InputMismatchException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a number between 0 and 100.");
            }
            catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(null, e.getMessage() + "\nPlease try again.");
            }
        }
        JOptionPane.showMessageDialog(null, "Settings modified. Brightness set to " + brightness + ", Color set to " + color + ".");
    }
    public void execute(){
        setBrightness(100);
        String message = "Executing smart light...\nBrightness set to " + getBrightness() + ", Color set to " + getColor() + ".";
        JOptionPane.showMessageDialog(null, message);
    }
}
