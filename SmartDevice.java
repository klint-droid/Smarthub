import javax.swing.JPanel;
import javax.swing.JTextArea;

interface SmartDevice {
    String viewState();
    String modifySettings(String input);
    String execute();
    String getName();

    JPanel getControlPanel(JTextArea outputArea);
}
