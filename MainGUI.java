import javax.swing.*;
import java.awt.*;

public class MainGUI extends JFrame {
    private CentralHub hub;
    private JComboBox<String> deviceDropdown;
    private JTextArea outputArea;
    private JPanel controlPanel;

    public MainGUI() {
        hub = new CentralHub();

        setTitle("The Smart Home");
        setSize(650, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // TOP PANEL
        JPanel topPanel = new JPanel(new FlowLayout());

        deviceDropdown = new JComboBox<>();
        for (SmartDevice device : hub.getDevices()) {
            deviceDropdown.addItem(device.getName());
        }

        topPanel.add(new JLabel("Select a device:"));
        topPanel.add(deviceDropdown);

        add(topPanel, BorderLayout.NORTH);

        // CENTER
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setBorder(BorderFactory.createTitledBorder("Output"));
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        controlPanel = new JPanel();
        controlPanel.setBorder(BorderFactory.createTitledBorder("Controls"));
        controlPanel.setPreferredSize(new Dimension(220, 0));
        add(controlPanel, BorderLayout.EAST);

        // BOTTOM PANEL
        JPanel bottomPanel = new JPanel(new FlowLayout());

        JButton viewStateBtn = new JButton("View State");
        JButton executeBtn = new JButton("Execute");

        bottomPanel.add(viewStateBtn);
        bottomPanel.add(executeBtn);

        add(bottomPanel, BorderLayout.SOUTH);

        // VIEW STATE
        viewStateBtn.addActionListener(e -> {
            SmartDevice device = getSelectedDevice();
            outputArea.setText(device.viewState());
        });

        // MODIFY SETTINGS
        deviceDropdown.addActionListener(e -> {
            updateControlPanel();
            outputArea.setText(getSelectedDevice().viewState());
        });

        // EXECUTE
        executeBtn.addActionListener(e -> {
            SmartDevice device = getSelectedDevice();
            String result = device.execute();
            outputArea.setText(result + "\n\n" + device.viewState());
        });

        // SHOW DEFAULT
        outputArea.setText(getSelectedDevice().viewState());

        updateControlPanel();
    }

    private SmartDevice getSelectedDevice() {
        return hub.getDevices()[deviceDropdown.getSelectedIndex()];
    }

    private void updateControlPanel() {
        controlPanel.removeAll();
        SmartDevice device = getSelectedDevice();
        JPanel panel = device.getControlPanel(outputArea);

        controlPanel.setLayout(new BorderLayout());
        controlPanel.add(panel, BorderLayout.CENTER);

        controlPanel.revalidate();
        controlPanel.repaint();
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainGUI().setVisible(true);
        });
    }
}