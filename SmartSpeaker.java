import java.util.*;

import javax.swing.JOptionPane;

public class SmartSpeaker extends BaseDevice implements SmartDevice{
    private int volume;
    private List<String> tracks = new ArrayList<>();
    private int currentIndex = 0;
    private boolean playing = false;
    private final String[] availableTracks = {
        "Blinding Lights",
        "Shape of You",
        "Levitating",
        "Peaches",
        "Stay",
        "Bad Habits",
        "Happier",
        "Senorita"
    }; 

    public SmartSpeaker(String name){
        super(name);
    }

    public void viewState(){
        JOptionPane.showMessageDialog(null, "Volume: " + volume);
        if(tracks.isEmpty()){
            JOptionPane.showMessageDialog(null, "No tracks available.");
        } else {
            JOptionPane.showMessageDialog(null, "Current track: " + tracks.get(currentIndex));
        }
        JOptionPane.showMessageDialog(null, "Playing: " + playing);
    }

    public int getVolume(){
        return volume;
    }

    public boolean getPlaying(){
        return playing;
    }

    public void setVolume(int volume){
        if(volume >= 0 && volume <= 100){
            this.volume = volume;
        }
        else{
            this.volume = 100;
            throw new IllegalArgumentException("Volume value is out of range");
        }
    }

    public void setPlaying(boolean playing){
        this.playing = playing;
    }

    public void modifySettings(Scanner scanner){
        while(true){
            try {
                String input = JOptionPane.showInputDialog("Enter volume (0-100): ");
                if(input == null) return;
                int volumeInput = Integer.parseInt(input);
                setVolume(volumeInput);
                break;
            } catch(InputMismatchException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a number between 0 and 100 only. Please try again.");
            }
            catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(null, e.getMessage() + "\nPlease try again.");
            }
        }
        
        int choice;
        while(true){
            try {
                String[] actions = {"1. Generate random playlist", "2. Add tracks manually", "3. Back"};
                
                int action = JOptionPane.showOptionDialog(
                    null,
                    "Select an action:",
                    "SMART SPEAKER",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    actions,
                    actions[0]
                );

                choice = action + 1;

                if(choice == 1 || choice == 2) break;
                else JOptionPane.showMessageDialog(null,    "Invalid choice.");
                
            } catch (InputMismatchException e) {
                JOptionPane.showMessageDialog(null, "Please try again. Enter a number between 1 and 2 only.");
            }
        }

        int count;

        while(true){
            try{
                String input = JOptionPane.showInputDialog("How many tracks do you want to add?: ");
                if(input == null) return;
                count = Integer.parseInt(input);

                if(count > 0 && count <= availableTracks.length) break;
                else JOptionPane.showMessageDialog(null, "Invalid number of tracks. Please try again.");
            } catch(InputMismatchException e){
                JOptionPane.showMessageDialog(null, "Please try again. Enter a number between 1 and " + availableTracks.length + " only.");
            }
        }
        tracks.clear();

        if(choice == 1){
            Random rand = new Random();

            Set<String> uniqueTracks = new HashSet<>();

            while(uniqueTracks.size() < count){
                String randomTrack = availableTracks[rand.nextInt(availableTracks.length)];
                uniqueTracks.add(randomTrack);
            }
            tracks.addAll(uniqueTracks);
        } else {    
            for(int i = 0; i < count; i++){
                String input = JOptionPane.showInputDialog("Enter track " + (i + 1) + ": ");
                if(input == null) return;
                tracks.add(input);
            }
        }
        currentIndex = 0;
        JOptionPane.showMessageDialog(null, "\nPlaylist generated.");
        for(int i = 0; i < tracks.size(); i++){
            JOptionPane.showMessageDialog(null, (i + 1) + ". " + tracks.get(i));
        }
    }
    public void play(){
        if(tracks.isEmpty()){
            JOptionPane.showMessageDialog(null, "No tracks available.");
            return;
        }
        setPlaying(true);
        JOptionPane.showMessageDialog(null, "Playing: " + tracks.get(currentIndex));
    }

    public void pause(){
        playing = false;
        JOptionPane.showMessageDialog(null, "Paused" + tracks.get(currentIndex));
    }

    public void skip(){
        if(tracks.isEmpty()){
            JOptionPane.showMessageDialog(null, "No tracks to skip.");
            return;
        } 
        currentIndex = (currentIndex + 1) % tracks.size();
        JOptionPane.showMessageDialog(null, "Skipping to next track: " + tracks.get(currentIndex));
    }
    public void execute(){
        play();
        JOptionPane.showMessageDialog(null, "Resuming playback: " + tracks.get(currentIndex));
    }
}
