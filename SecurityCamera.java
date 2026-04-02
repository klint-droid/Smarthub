import javax.swing.*;
import java.awt.GridLayout;

public class SecurityCamera extends BaseDevice implements SmartDevice {
    private boolean powerOn = false;
    private boolean recording = false;

    public SecurityCamera(String name) {
        super(name);
    }

    public boolean isPowerOn() {
        return powerOn;
    }

    public boolean isRecording() {
        return recording;
    }

    public void setPowerOn(boolean powerOn) {
        this.powerOn = powerOn;

        if (!powerOn) {
            recording = false;
        }
    }

    public void setRecording(boolean recording) {
        this.recording = recording;
    }

    public String viewState() {
        return "Device: " + getName() +
               "\nPower: " + (powerOn ? "ON" : "OFF") +
               "\nRecording: " + (recording ? "YES" : "NO");
    }

    public String modifySettings(String input) {
        if (input.equals("1")) {
            setPowerOn(true);
            return "Camera turned ON";
        } 
        else if (input.equals("0")) {
            setPowerOn(false);
            return "Camera turned OFF (Recording stopped)";
        } 
        else {
            return "Invalid input! Use 1 (ON) or 0 (OFF)";
        }
    }

    public String startRecording() {
        if (!powerOn) {
            return "Camera is OFF. Cannot start recording.";
        }

        if (!recording) {
            recording = true;
            return "Recording started.";
        } else {
            return "Already recording.";
        }
    }

    public String stopRecording() {
        if (!powerOn) {
            return "Camera is OFF.";
        }

        if (recording) {
            recording = false;
            return "Recording stopped.";
        } else {
            return "Already stopped.";
        }
    }

    public String execute() {
        if (powerOn) {
            if (!recording) {
                recording = true;
                return "Camera ON → Recording started.";
            } else {
                return "Camera already recording.";
            }
        } else {
            return "Camera is OFF.";
        }
    }

    public JPanel getControlPanel(JTextArea outputArea){
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 5, 5));

        JLabel powerLabel = new JLabel("Power:");
        JButton togglePowerBtn = new JButton(powerOn ? "Turn OFF" : "Turn ON");

        togglePowerBtn.addActionListener(e -> {
            setPowerOn(!powerOn);
            
            powerLabel.setText("Power: " + (powerOn ? "ON" : "OFF"));
            togglePowerBtn.setText(powerOn ? "TURN OFF" : "TURN ON");

            outputArea.setText(viewState());
        });

        JButton startRecordingBtn = new JButton("Start Recording");

        startRecordingBtn.addActionListener(e -> {
            String result = startRecording();
            outputArea.setText(result + "\n\n" + viewState());
        });

        JButton stopRecordingBtn = new JButton("Stop Recording");

        stopRecordingBtn.addActionListener(e -> {
            String result = stopRecording();
            outputArea.setText(result + "\n\n" + viewState());
        });

        JLabel statusLabel = new JLabel("Recording: " + (recording ? "YES" : "NO"));

        JButton refreshBtn = new JButton("Refresh Status");

        refreshBtn.addActionListener(e -> {
            statusLabel.setText("Recording: " + (recording ? "YES" : "NO"));
            outputArea.setText(viewState());
        });

        panel.add(powerLabel);
        panel.add(togglePowerBtn);
        panel.add(startRecordingBtn);
        panel.add(stopRecordingBtn);
        panel.add(statusLabel);
        panel.add(refreshBtn);

        return panel;
    }
}