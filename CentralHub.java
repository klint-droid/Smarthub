import javax.swing.JOptionPane;

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

            while (true) {

                String[] actions = {
                    "View Status",
                    "Modify Settings",
                    "Execute",
                    "Back"
                };

                int action = JOptionPane.showOptionDialog(
                    null,
                    "Selected: " + device.getName(),
                    "Device Menu",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    actions,
                    actions[0]
                );

                if (action == 3 || action == -1) break;

                switch (action) {
                    case 0 -> {
                        device.viewState();
                        JOptionPane.showMessageDialog(null, "Viewed successfully.");
                    }

                    case 1 -> {
                        device.modifySettings(new java.util.Scanner(System.in));
                        JOptionPane.showMessageDialog(null, "Settings modified.");
                    }

                    case 2 -> {
                        device.execute();
                        JOptionPane.showMessageDialog(null, "Executed successfully.");
                    }
                }

                if (device instanceof SmartSpeaker speaker) {
                    String[] speakerActions = {"Play", "Pause", "Skip", "Back"};

                    int spAction = JOptionPane.showOptionDialog(
                        null,
                        "Speaker Controls",
                        "Smart Speaker",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        speakerActions,
                        speakerActions[0]
                    );

                    if (spAction == 0) speaker.play();
                    else if (spAction == 1) speaker.pause();
                    else if (spAction == 2) speaker.skip();
                }

                if (device instanceof SecurityCamera cam) {
                    String[] camActions = {"Start Recording", "Stop Recording", "Back"};

                    int camAction = JOptionPane.showOptionDialog(
                        null,
                        "Camera Controls",
                        "Security Camera",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        camActions,
                        camActions[0]
                    );

                    if (camAction == 0) cam.startRecording();
                    else if (camAction == 1) cam.stopRecording();
                }

                if (device instanceof SmartLock lock) {
                    if (action == 0) lock.viewFailedAttempts();
                }

                if (device instanceof SecurityFloodlight flood) {
                    String[] floodActions = {"Trigger Motion Alert", "Back"};

                    int fAction = JOptionPane.showOptionDialog(
                        null,
                        "Floodlight Controls",
                        "Security Floodlight",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        floodActions,
                        floodActions[0]
                    );

                    if (fAction == 0) flood.triggerMotionAlert();
                }
            }
        }

        JOptionPane.showMessageDialog(null, "Exiting system...");
    }
}