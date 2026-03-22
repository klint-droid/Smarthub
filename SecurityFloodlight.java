import java.util.InputMismatchException;
import java.util.Scanner;

public class SecurityFloodlight extends SmartLight {
    
    private String[] motionSensitivityOptions = {"LOW", "MEDIUM", "HIGH"};
    private int sensitivityIndex = 1;
    private boolean motionArmed = false;

    public SecurityFloodlight(String name, int brightness, String color) {
        super(name, brightness, color);

    }

    public boolean getMotionArmed(){
        return motionArmed;
    }

    public void setMotionArmed(boolean motionArmed){
        this.motionArmed = motionArmed;
    }
    public void viewState() {
        super.viewState();
        System.out.println("Motion Sensitivity: " + motionSensitivityOptions[sensitivityIndex]);
        System.out.println("Motion Armed: " + (motionArmed ? "Yes" : "No"));
    }

    public void modifySettings(Scanner scanner) {
        while(true){
            try{
                System.out.println("Enter motion detection status (1 = ON, 0 = OFF): ");
                int motionStatusInput = scanner.nextInt();
                setMotionArmed(motionStatusInput == 1);
                break;
            } catch (InputMismatchException e){
                System.out.println("Invalid input. Please enter a number between 0 and 1 only. Please try again.");
                scanner.nextLine();
            }
        }
        scanner.nextLine();
        while(true){
            try{
                super.modifySettings(scanner);

                System.out.println("Select sensitivity:");
                for (int i = 0; i < motionSensitivityOptions.length; i++) {
                    System.out.println((i + 1) + ". " + motionSensitivityOptions[i]);
                }

                int choice = scanner.nextInt(); 
                if (choice >= 1 && choice <= 3) {
                    sensitivityIndex = choice - 1;
                } else {
                    throw  new IllegalArgumentException("Invalid choice.");
                }
                break;
            } catch (InputMismatchException e){
                System.out.println("Invalid input. Please enter a number between 1 and 3 only. Please try again.");
                scanner.nextLine();
            } catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
                System.out.println("Please try again.");
            }
        }

        
    }
    public void triggerMotionAlert() {
        if (getMotionArmed()) {
            setBrightness(100);
            System.out.println("Motion detected! (" 
                + motionSensitivityOptions[sensitivityIndex] + ")");
            System.out.println("Brightness set to full.");
        } else {
            System.out.println("Motion detection not armed.");
        }
    }

    public void execute() {
        super.execute();
        triggerMotionAlert();
    }
}
