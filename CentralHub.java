import javax.swing.*;

public class CentralHub {

    public static void main(String[] args) {

        SmartDevice[] devices = {
            new SmartLight("Smart Light", 50, "White"),
            new Thermostat("Thermostat", 20),
            new SecurityCamera("Security Camera"),
            new SmartSpeaker("Smart Speaker"),
            new SmartLock("Smart Lock"),
            new SecurityFloodlight("Security Floodlight", 50, "White")
        };

        while (true) {

            // Device selection
            String[] deviceOptions = new String[devices.length + 1];
            for (int i = 0; i < devices.length; i++) {
                deviceOptions[i] = devices[i].getName();
            }
            deviceOptions[devices.length] = "Exit";

            int choice = JOptionPane.showOptionDialog(
                null,
                "Select a device:",
                "SMART HOME HUB",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                deviceOptions,
                deviceOptions[0]
            );

            if (choice == devices.length || choice == -1) break;

            SmartDevice device = devices[choice];

            // Device menu loop
            while (true) {

                String[] actions = getActions(device);

                int action = JOptionPane.showOptionDialog(
                    null,
                    "Device: " + device.getName(),
                    "Device Menu",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    actions,
                    actions[0]
                );

                if (action == actions.length - 1 || action == -1) break;

                handleAction(device, action);
            }
        }

        JOptionPane.showMessageDialog(null, "Exiting system...");
    }

    // 🔹 Dynamic menu based on device
    private static String[] getActions(SmartDevice device) {

        if (device instanceof SmartSpeaker) {
            return new String[]{
                "View Status", "Modify Settings", "Execute",
                "Play", "Pause", "Skip",
                "Back"
            };
        }

        if (device instanceof SecurityCamera) {
            return new String[]{
                "View Status", "Modify Settings", "Execute",
                "Start Recording", "Stop Recording",
                "Back"
            };
        }

        if (device instanceof SecurityFloodlight) {
            return new String[]{
                "View Status", "Modify Settings", "Execute",
                "Trigger Motion Alert",
                "Back"
            };
        }

        if (device instanceof SmartLock) {
            return new String[]{
                "View Status", "Modify Settings", "Execute",
                "View Failed Attempts",
                "Back"
            };
        }

        return new String[]{
            "View Status", "Modify Settings", "Execute", "Back"
        };
    }

    private static void handleAction(SmartDevice device, int action) {

        switch (action) {
            case 0 -> device.viewState();

            case 1 -> device.modifySettings(null);

            case 2 -> device.execute();

            default -> {

                if (device instanceof SmartSpeaker speaker) {
                    if (action == 3) speaker.play();
                    else if (action == 4) speaker.pause();
                    else if (action == 5) speaker.skip();
                }

                if (device instanceof SecurityCamera cam) {
                    if (action == 3) cam.startRecording();
                    else if (action == 4) cam.stopRecording();
                }

                if (device instanceof SecurityFloodlight flood) {
                    if (action == 3) flood.triggerMotionAlert();
                }

                if (device instanceof SmartLock lock) {
                    if (action == 3) lock.viewFailedAttempts();
                }
            }
        }
    }
}