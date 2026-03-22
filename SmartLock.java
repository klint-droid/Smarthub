import java.util.Scanner;

import javax.swing.JOptionPane;

import java.util.*;
public class SmartLock extends BaseDevice implements SmartDevice{
    private boolean locked = true;
    private String pin = "2026";
    private List<String> failedAttempts = new ArrayList<>();
    private int attemptCount = 0;
    
    public SmartLock(String name) {
        super(name);
    }

    public boolean getLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public int attemptCount() {
        return attemptCount;
    }

    public void setAttemptCount(int attemptCount) {
        this.attemptCount = attemptCount;
    }
    public void viewState() {
        String info = "Device Name: " + getName() + "\nLocked: " + (getLocked() ? "YES" : "NO");
        JOptionPane.showMessageDialog(null, info);
    }

    public void modifySettings(Scanner scanner) {
        scanner.nextLine();
        while(true){
            try {
                String input = JOptionPane.showInputDialog("Enter PIN: ");
                if(input == null) return;
                input = input.trim();

                if(!input.matches("\\d{4}")){
                    throw new IllegalArgumentException("Invalid input.");
                }
                if (input.equals(pin)) {
                    attemptCount = 0;
                    setLocked(true);

                    JOptionPane.showMessageDialog(null, "PIN entered successfully. Device " + (getLocked() ? "Locked." : "Unlocked."));
                    
                    break;
                } else {
                    attemptCount++;
                    failedAttempts.add(input);
                    JOptionPane.showMessageDialog(null, "Attempt " + attemptCount + " of 3");

                    if(attemptCount >= 3){
                        setLocked(true);
                        JOptionPane.showMessageDialog(null, "Too many failed attempts. Device Locked");
                        break;
                    }
                    throw new IllegalArgumentException("WRONG PIN");
                }
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(null, e.getMessage() + "\nPlease try again.");
            }
        }
    }
    public void viewFailedAttempts(){
        JOptionPane.showMessageDialog(null, "Failed attempts: " + attemptCount + "\n Failed Attempt Logs: ");
        for(String attempt: failedAttempts){
            JOptionPane.showMessageDialog(null, "Attempt: " + attempt + "\n");
        }
    }
    public void execute(){
        locked = true;
        String message = "Executing smart lock...\nDevice Locked...";
        JOptionPane.showMessageDialog(null, message);
    }

}
