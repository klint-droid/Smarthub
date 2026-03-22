import java.util.Scanner;
import java.util.InputMismatchException;
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
        System.out.println("Brightness: " + brightness);
        System.out.println("Color: " + color);
    }

    public void modifySettings(Scanner scanner) {
        int brightnessInput;
        while(true){
            try{
                System.out.println("Enter brightness (0-100): ");
                brightnessInput = scanner.nextInt();
                setBrightness(brightnessInput);
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number between 0 and 100 only. Please try again.");
                scanner.nextLine();
            }
            catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                System.out.println("Please try again.");
            }
        }
        System.out.println("Settings modified. Brightness set to " + brightness + ", Color set to " + color + ".");
    }
    public void execute(){
        setBrightness(100);
        System.out.println("Executing smart light...");
        System.out.println("Brightness set to full.");
        System.out.println("Color set to " + color + ".");
    }
}
