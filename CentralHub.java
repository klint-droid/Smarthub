import java.util.Scanner;
import java.util.InputMismatchException;

public class CentralHub {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        SmartDevice[] devices = {
            new SmartLight("Smart Light", 50, "White"),
            new Thermostat("Thermostat", 20),
            new SecurityCamera("Security Camera"),
            new SmartSpeaker("Smart Speaker"),
            new SmartLock("Smart Lock"),
            new SecurityFloodlight("Security Floodlight", 50, "White")
        };

        while (true) {
            System.out.println("\n=== SMART HOME HUB ===");

            for (int i = 0; i < devices.length; i++) {
                System.out.println((i + 1) + ". " + devices[i].getName());
            }
            System.out.println("0. Exit");

            int choice;

            while (true) {
                try {
                    System.out.print("Select device: ");
                    choice = scanner.nextInt();

                    if (choice < 0 || choice > devices.length) {
                        System.out.println("Invalid choice. Try again.");
                        continue;
                    }

                    break;

                } catch (InputMismatchException e) {
                    System.out.println("Invalid input! Please enter numbers only.");
                    scanner.nextLine(); 
                }
            }

            if (choice == 0) break;

            SmartDevice device = devices[choice - 1];

            while (true) {
                int action;

                try {
                    System.out.println("\nSelected: " + device.getName());
                    System.out.println("1. View Status");
                    System.out.println("2. Modify Settings");
                    System.out.println("3. Execute");

                    if (device instanceof SmartSpeaker) {
                        System.out.println("4. Play");
                        System.out.println("5. Pause");
                        System.out.println("6. Skip");
                    }

                    if (device instanceof SecurityCamera) {
                        System.out.println("4. Start Recording");
                        System.out.println("5. Stop Recording");
                    }
                    if(device instanceof SmartLock){
                        System.out.println("4. View Failed Attempts");
                    }

                    System.out.println("0. Back");

                    System.out.print("Select action: ");
                    action = scanner.nextInt();

                    if (action == 0) break;

                } catch (InputMismatchException e) { 
                    System.out.println("Invalid input! Please enter numbers only.");
                    scanner.nextLine(); 
                    continue;
                }

                switch (action) {
                    case 1 -> device.viewState();
                    case 2 -> device.modifySettings(scanner);
                    case 3 -> device.execute();

                    default -> {
                        if (device instanceof SmartSpeaker speaker) {
                            if (action == 4) speaker.play();
                            else if (action == 5) speaker.pause();
                            else if (action == 6) speaker.skip();
                        }

                        if (device instanceof SecurityCamera cam) {
                            if (action == 4) cam.startRecording();
                            else if (action == 5) cam.stopRecording();
                        }
                        if(device instanceof SmartLock lock){
                            if(action == 4) lock.viewFailedAttempts();
                        }
                        if (device instanceof SecurityFloodlight flood) {
                            if (action == 4) flood.triggerMotionAlert();
                        }
                    }
                }
            }
        }

        scanner.close();
        System.out.println("Exiting system...");
    }
}