import java.util.Scanner;
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
        if (getLocked()) {
            System.out.println("Locked");
        } else {
            System.out.println("Unlocked");
        }
    }

    public void modifySettings(Scanner scanner) {
        scanner.nextLine();
        while(true){
            try {
                System.out.println("Enter PIN: ");
                String input = scanner.nextLine();

                if(!input.matches("\\d{4}")){
                    throw new IllegalArgumentException("Invalid input.");
                }
                if (input.equals(pin)) {
                    attemptCount = 0;
                    setLocked(true);

                    System.out.println(getLocked() ? "Locked." : "Unlocked.");
                    
                    break;
                } else {
                    attemptCount++;
                    failedAttempts.add(input);
                    System.out.println("Attempt " + attemptCount + " of 3");

                    if(attemptCount >= 3){
                        setLocked(true);
                        System.out.println("Too many failed attempts. Device Locked");
                        break;
                    }
                    throw new IllegalArgumentException("WRONG PIN");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                System.out.println("Please try again.");
            }
        }
    }
    public void viewFailedAttempts(){
        System.out.println("Failed attempts: " + failedAttempts);
        System.out.println("Failed Attempt Logs: ");
        for(String attempt: failedAttempts){
            System.out.println(attempt);
        }
    }
    public void execute(){
        locked = true;
        System.out.println("Executing smart lock...");
        System.out.println("Locked.");
    }

}
