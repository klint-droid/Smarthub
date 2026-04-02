import java.awt.Dimension;
import java.util.InputMismatchException;
import javax.swing.*;
public class SmartLight extends BaseDevice implements SmartDevice{
    private int brightness;
    private String color;

    public SmartLight(String name) {
        super(name);
        setBrightness(100);
        setColor("White");
    }
    public int getBrightness() {
        return brightness;
    }
    public String getColor() {
        return color;
    }
    public void setBrightness(int brightness) {
        if(brightness >= 0 && brightness <= 100){
            this.brightness = brightness;
        }
        else{
            this.brightness = 100;
            throw new IllegalArgumentException("Brightness value is out of range");
        }
            
    }

    public void setColor(String color){
        if(color != null && !color.isEmpty() && !color.matches("\\d+")){
            this.color = color;
        }
        else{
            this.color = "White";
            throw new IllegalArgumentException("Color cannot be empty or number");
        }
    }

    public String viewState() {
         return "Device: " + getName() +
               "\nBrightness: " + brightness +
               "\nColor: " + color;
    }

    public String modifySettings(String input) {
        while(true){
            try{
                int value = Integer.parseInt(input);
                setBrightness(value);
                return "Brightness updated to " + brightness;
            } catch (InputMismatchException e) {
                return "Invalid input! Please enter a number.";
            }
            catch (IllegalArgumentException e) {
                return e.getMessage();
            }
        }
    }
    public String execute(){
        setBrightness(getBrightness());
        String message = "Executing smart light...\nBrightness set to " + getBrightness() + ", Color set to " + getColor() + ".";
        return message;
    }

    public JPanel getControlPanel(JTextArea outputArea){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        JLabel brightnessLabel = new JLabel("Brightness: " + getBrightness());
        brightnessLabel.setAlignmentX(JComponent.LEFT_ALIGNMENT);

        JSlider brightnesSlider = new JSlider(0, 100, getBrightness());
        brightnesSlider.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        brightnesSlider.setMaximumSize(new Dimension(180, 40));

        brightnesSlider.addChangeListener(e -> {
            brightnessLabel.setText("Brightness: " + brightnesSlider.getValue());
        });

        JLabel colorLabel = new JLabel("Color:");
        colorLabel.setAlignmentX(JComponent.LEFT_ALIGNMENT);

        JTextField colorField = new JTextField(getColor());
        colorField.setMaximumSize(new Dimension(180, 30));
        colorField.setAlignmentX(JComponent.LEFT_ALIGNMENT);

        JButton updateBtn = new JButton("Update Settings");
        updateBtn.setAlignmentX(JComponent.LEFT_ALIGNMENT);

        updateBtn.addActionListener(e -> {
            try{
                int value = brightnesSlider.getValue();
                setBrightness(value);
                setColor(colorField.getText());

                outputArea.setText("Settings updated!\n" + viewState());
            } catch (Exception ex){
                outputArea.setText("Error: " + ex.getMessage());
            }   
        });

        panel.add(brightnessLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(brightnesSlider);

        panel.add(Box.createVerticalStrut(15));
        panel.add(colorLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(colorField);

        panel.add(Box.createVerticalStrut(20));
        panel.add(updateBtn);

        return panel;
    }
}
