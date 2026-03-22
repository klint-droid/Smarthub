import java.util.Scanner;

import javax.swing.JOptionPane;

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
        String info = "Device Name: " + getName() + "\nPower: " + (powerOn ? "ON" : "OFF") + "\nRecording: " + (recording ? "YES" : "NO");
        JOptionPane.showMessageDialog(null, info);
    }
    public void modifySettings(Scanner scanner){


        while (true) {
            try{
                String input = JOptionPane.showInputDialog("Enter power (1 = ON, 0 = OFF): ");
                if(input == null) return;
                int powerInput = Integer.parseInt(input);
                
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
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(null, e.getMessage() + "\nPlease try again.");
            } 
            catch (InputMismatchException e) {
                JOptionPane.showMessageDialog(null, "Please enter a number between 0 and 1 only. Please try again.");
            }
        }
    }

    public void startRecording(){
        if(!powerOn){
            JOptionPane.showMessageDialog(null, "Camera is off...Cannot start recording.");
            return;
        }

        if(!recording){
            setRecording(true);
            JOptionPane.showMessageDialog(null, "Recording started.");
        } else {
            JOptionPane.showMessageDialog(null, "Recording already started.");
        }
    }

    public void stopRecording(){
        if(!powerOn){
            JOptionPane.showMessageDialog(null, "Camera is off...");
            return;
        }

        if(recording){
            setRecording(false);
            JOptionPane.showMessageDialog(null, "Recording stopped.");
        } else {
            JOptionPane.showMessageDialog(null, "Already stopped.");
        }
    }
    public void execute(){
        if(powerOn && !recording){
            setRecording(true);
            String message = "Executing security camera...\nRecording started...";
            JOptionPane.showMessageDialog(null, message);
        } else {
            setRecording(false);
            JOptionPane.showMessageDialog(null, "Camera is off...");
        }
    }
}
