import java.util.*;
import javax.swing.*;
import java.awt.GridLayout;

public class SmartSpeaker extends BaseDevice implements SmartDevice {
    private int volume;
    private List<String> tracks = new ArrayList<>();
    private int currentIndex = 0;
    private boolean playing = false;

    private final String[] availableTracks = {
        "Blinding Lights", "Shape of You", "Levitating",
        "Peaches", "Stay", "Bad Habits",
        "Happier", "Senorita"
    };

    public SmartSpeaker(String name) {
        super(name);
        this.volume = 50;
    }

    public int getVolume() { return volume; }
    public boolean isPlaying() { return playing; }

    public void setVolume(int volume) {
        if (volume >= 0 && volume <= 100) {
            this.volume = volume;
        } else {
            throw new IllegalArgumentException("Volume must be 0–100");
        }
    }

    public String viewState() {
        String currentTrack = tracks.isEmpty() ? "No track" : tracks.get(currentIndex);

        return "Device: " + getName() +
               "\nVolume: " + volume +
               "\nCurrent Track: " + currentTrack +
               "\nPlaying: " + (playing ? "YES" : "NO");
    }

    public String modifySettings(String input) {
        try {
            int value = Integer.parseInt(input);
            setVolume(value);
            return "Volume updated to " + volume +
                   "\n(Type 'generate' or 'manual' next to set playlist)";
        } catch (NumberFormatException e) {
            return handleCommands(input);
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    private String handleCommands(String input) {
        input = input.toLowerCase();

        if (input.equals("generate")) {
            return generateRandomPlaylist(3); // default 3 tracks
        } 
        else if (input.equals("manual")) {
            tracks.clear();
            return "Manual mode: Use 'add:trackname' to add tracks";
        } 
        else if (input.startsWith("add:")) {
            String track = input.substring(4);
            tracks.add(track);
            return "Added track: " + track;
        } 
        else {
            return "Commands:\n- number (volume)\n- generate\n- manual\n- add:trackname";
        }
    }

    private String generateRandomPlaylist(int count) {
        Random rand = new Random();
        Set<String> uniqueTracks = new HashSet<>();

        while (uniqueTracks.size() < count) {
            uniqueTracks.add(availableTracks[rand.nextInt(availableTracks.length)]);
        }

        tracks.clear();
        tracks.addAll(uniqueTracks);
        currentIndex = 0;

        return "Playlist generated:\n" + String.join("\n", tracks);
    }

    public String play() {
        if (tracks.isEmpty()) return "No tracks available.";
        playing = true;
        return "Playing: " + tracks.get(currentIndex);
    }
    public String pause() {
        if (tracks.isEmpty()) return "No tracks available.";
        playing = false;
        return "Paused: " + tracks.get(currentIndex);
    }
    public String skip() {
        if (tracks.isEmpty()) return "No tracks available.";
        currentIndex = (currentIndex + 1) % tracks.size();
        return "Next track: " + tracks.get(currentIndex);
    }

    public String execute() {
        return play();
    }

    public JPanel getControlPanel(JTextArea outputArea){
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(10, 1, 5, 5));

        JLabel volumeLabel = new JLabel("Volume: " + volume);
        
        JSlider volumeSlider = new JSlider(0, 100, volume);

        volumeSlider.addChangeListener(e -> {
            setVolume(volumeSlider.getValue());
            volumeLabel.setText("Volume: " + volume);
        });
        JLabel trackLabel = new JLabel("Track: " + (tracks.isEmpty() ? "None" : tracks.get(currentIndex)));
        
        JButton playBtn = new JButton("Play");
        playBtn.addActionListener(e -> {
            String result = play();
            outputArea.setText(result + "\n\n" + viewState());
        });

        JButton pauseBtn = new JButton("Pause");
        pauseBtn.addActionListener(e -> {
            String result = pause();
            outputArea.setText(result + "\n\n" + viewState());
        });

        JButton skipBtn = new JButton("Skip");
        skipBtn.addActionListener(e -> {
            String result = skip();
            trackLabel.setText("Track: " + (tracks.isEmpty() ? "None" : tracks.get(currentIndex)));
            outputArea.setText(result + "\n\n" + viewState());
        });

        JButton generateBtn = new JButton("Generate Playlist");
        generateBtn.addActionListener(e -> {
            String result = generateRandomPlaylist(3); // default 3 tracks
            outputArea.setText(result + "\n\n" + viewState());
        });

        JTextField trackField = new JTextField();
        trackField.setBorder(BorderFactory.createTitledBorder("Add Track (add:trackname)"));

        JButton addTrackBtn = new JButton("Add Track");
        addTrackBtn.addActionListener(e -> {
            String track = trackField.getText();
            if(!track.isEmpty()){
                tracks.add(track);
                outputArea.setText("Added: " + track + "\n\n" + viewState());
                trackField.setText("");
            } else {
                outputArea.setText("Enter a track name.");
            }
        });

        JButton refresButton = new JButton("Refresh State");
        refresButton.addActionListener(e -> {
            trackLabel.setText("Track: " + (tracks.isEmpty() ? "None" : tracks.get(currentIndex)));
            outputArea.setText(viewState());
        });

        panel.add(volumeLabel);
        panel.add(volumeSlider);
        panel.add(playBtn);
        panel.add(pauseBtn);
        panel.add(skipBtn);
        panel.add(generateBtn);
        panel.add(trackField);
        panel.add(addTrackBtn);
        panel.add(trackLabel);
        panel.add(refresButton);

        return panel;
    }
}