import java.util.Scanner;

interface SmartDevice {
    void viewState();
    void modifySettings(Scanner scanner);
    void execute();
    String getName();
}
