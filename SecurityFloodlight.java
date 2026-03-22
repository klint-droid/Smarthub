import java.util.InputMismatchException;
import java.util.Scanner;

import javax.swing.JOptionPane;

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
        String info = "Motion Detection: " + (motionArmed ? "ON" : "OFF") + "\nSensitivity: " + motionSensitivityOptions[sensitivityIndex];
        JOptionPane.showMessageDialog(null, info);
    }

    public void modifySettings(Scanner scanner) {
        while(true){
            try{
                String input = JOptionPane.showInputDialog("Enter motion detection status (1 = ON, 0 = OFF): ");
                if(input == null) return;
                int motionStatusInput = Integer.parseInt(input);
                setMotionArmed(motionStatusInput == 1);
                break;
            } catch (InputMismatchException e){
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a number between 0 and 1 only. Please try again.");
            }
        }
        while(true){
            try{
                super.modifySettings(scanner);

                String message = "Select sensitivity:";
                for (int i = 0; i < motionSensitivityOptions.length; i++) {
                    message += (i + 1) + ". " + motionSensitivityOptions[i] + "\n";
                }
                String input = JOptionPane.showInputDialog(null, message);
                if(input == null) return;
                int choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= 3) {
                    String selected = motionSensitivityOptions[choice - 1];
                    JOptionPane.showMessageDialog(null, "You selected: " + selected);
                } else {
                    throw  new IllegalArgumentException("Invalid choice.");
                }
                break;
            } catch (InputMismatchException e){
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a number between 1 and 3 only. Please try again.");
            } catch (IllegalArgumentException e){
                JOptionPane.showMessageDialog(null, e.getMessage() + "\nPlease try again.");
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
