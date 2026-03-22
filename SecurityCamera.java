import java.util.Scanner;
import java.util.InputMismatchException;
public class SecurityCamera extends BaseDevice implements SmartDevice{
    private boolean powerOn = false;
    private boolean recording = false;

    public SecurityCamera(String name){
        super(name);
    }

    public boolean getPowerOn(){
        return powerOn;
    }

    public boolean getRecording(){
        return recording;
    }

    public void setPowerOn(boolean powerOn){
        this.powerOn = powerOn;
    }

    public void setRecording(boolean recording){
        this.recording = recording;
    }
    public void viewState(){
        System.out.println("Power: " + (powerOn ? "ON" : "OFF"));
        System.out.println("Recording: " + (recording ? "YES" : "NO"));
    }
    public void modifySettings(Scanner scanner){


        while (true) {
            try{
                System.out.println("Enter power (1 = ON, 0 = OFF): ");
                int powerInput = scanner.nextInt();
                scanner.nextLine();

                if(powerInput == 1){
                    setPowerOn(true);
                } else if (powerInput == 0){
                    setPowerOn(false);
                    setRecording(false);
                    break;
                } else {
                    throw new IllegalArgumentException("Invalid input.");
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number between 0 and 1 only. Please try again.");
                scanner.nextLine();
            } 
            catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                System.out.println("Please try again.");
                scanner.nextLine();
            }
        }
    }

    public void startRecording(){
        if(!powerOn){
            System.out.println("Camera is off...Cannot start recording.");
            return;
        }

        if(!recording){
            setRecording(true);
            System.out.println("Recording started.");
        } else {
            System.out.println("Recording already started.");
        }
    }

    public void stopRecording(){
        if(!powerOn){
            System.out.println("Camera is off...");
            return;
        }

        if(recording){
            setRecording(false);
            System.out.println("Recording stopped.");
        } else {
            System.out.println("Already stopped.");
        }
    }
    public void execute(){
        if(powerOn && !recording){
            setRecording(true);
            System.out.println("Executing security camera...");
            System.out.println("Recording started.");
        } else {
            setRecording(false);
            System.out.println("Camera is off...");
        }
    }
}
