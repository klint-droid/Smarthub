import java.util.*;

import javax.swing.*;
import java.awt.GridLayout;

public class SmartLock extends BaseDevice implements SmartDevice {

    private boolean locked = true;
    private String pin = "2026";
    private List<String> failedAttempts = new ArrayList<>();
    private int attemptCount = 0;

    public SmartLock(String name) {
        super(name);
    }

    public boolean isLocked() {
        return locked;
    }

    public int getAttemptCount() {
        return attemptCount;
    }

    @Override
    public String viewState() {
        return "Device: " + getName() +
               "\nStatus: " + (locked ? "LOCKED 🔒" : "UNLOCKED 🔓") +
               "\nFailed Attempts: " + attemptCount;
    }

    public String modifySettings(String input) {

        if (input == null || input.trim().isEmpty()) {
            return "Operation cancelled.";
        }

        input = input.trim();

        // Validate PIN format
        if (!input.matches("\\d{4}")) {
            attemptCount++;
            failedAttempts.add(input);
            return "PIN must be exactly 4 digits.\nAttempt " + attemptCount + " of 3";
        }

        // Correct PIN
        if (input.equals(pin)) {
            attemptCount = 0;
            locked = !locked;
            return "Access Granted ✔\nDevice is now " + (locked ? "LOCKED " : "UNLOCKED ");
        }

        attemptCount++;
        failedAttempts.add(input);

        if (attemptCount >= 3) {
            locked = true;
            return "Too many failed attempts \nDevice locked.";
        }

        return "Incorrect PIN \nAttempt " + attemptCount + " of 3";
    }

    public String viewFailedAttempts() {
        if (failedAttempts.isEmpty()) {
            return "No failed attempts.";
        }

        StringBuilder log = new StringBuilder("Failed Attempts:\n");
        for (String attempt : failedAttempts) {
            log.append("- ").append(attempt).append("\n");
        }

        return log.toString();
    }

    public String execute() {
        locked = true;
        return "Smart Lock executed.\nDevice is now LOCKED 🔒";
    }

    public JPanel getControlPanel(JTextArea outputArea){
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 5, 5));

        JLabel statusLabel = new JLabel("Status: " + (locked ? "LOCKED" : "UNLOCKED"));

        JTextField pinField = new JTextField();
        pinField.setBorder(BorderFactory.createTitledBorder("Enter PIN"));

        JButton submitBtn = new JButton("Submit");

        submitBtn.addActionListener(e -> {
            String input = pinField.getText();

            String result = modifySettings(input);

            statusLabel.setText("Status: " + (locked ? "LOCKED" : "UNLOCKED"));
            outputArea.setText(result + "\n\n" + viewState());

            pinField.setText("");
        });

        JButton lockBtn = new JButton("Lock");
        lockBtn.addActionListener(e -> {
            locked = true;
            outputArea.setText("Device locked manually. \n\n" + viewState());
            statusLabel.setText("Status: " + (locked ? "Locked" : "Unlocked"));
        });

        if(attemptCount >= 3) {
            pinField.setEnabled(false);
            submitBtn.setEnabled(false);
        }
        JButton logBtn = new JButton("View Failed Attempts");
        logBtn.addActionListener(e -> {
            outputArea.setText(viewFailedAttempts());
        });

        panel.add(statusLabel);
        panel.add(pinField);
        panel.add(submitBtn);
        panel.add(lockBtn);
        panel.add(logBtn);

        return panel;
    }
}