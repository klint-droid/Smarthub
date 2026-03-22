import java.util.*;

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
        System.out.println("Volume: " + volume);
        if(tracks.isEmpty()){
            System.out.println("No tracks available.");
        } else {
            System.out.println("Current track: " + tracks.get(currentIndex));
        }
        System.out.println("Playing: " + playing);
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
                System.out.println("Enter volume (0-100): ");
                int volumeInput = scanner.nextInt();
                setVolume(volumeInput);
                break;
            } catch(InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number between 0 and 100 only. Please try again.");
                scanner.nextLine();
            }
            catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                System.out.println("Please try again.");
            }
        }
        
        scanner.nextLine();
        int choice;
        while(true){
            try {
                System.out.println("\n1. Generate Random Playlist");
                System.out.println("2. Enter Tracks Manually");
                System.out.println("Option: ");
                choice = scanner.nextInt();
                if(choice == 1 || choice == 2) break;
                else System.out.println("Invalid choice.");
                
            } catch (InputMismatchException e) {
                System.out.println("Please try again. Enter a number between 1 and 2 only.");
                scanner.nextLine();
            }
        }
        scanner.nextLine();

        int count;

        while(true){
            try{
                System.out.println("How many tracks do you want to add?: ");
                count = scanner.nextInt();

                if(count > 0 && count <= availableTracks.length) break;
                else System.out.println("Invalid number of tracks. Please try again.");
            } catch(InputMismatchException e){
                System.out.println("Please try again. Enter a number between 1 and " + availableTracks.length + " only.");
                scanner.nextLine();
            }
        }
        scanner.nextLine();
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
                System.out.println("Enter track " + (i + 1) + ": ");
                String track = scanner.nextLine();
                tracks.add(track);
            }
        }
        currentIndex = 0;
        System.out.println("\nPlaylist generated.");
        for(int i = 0; i < tracks.size(); i++){
            System.out.println((i + 1) + ". " + tracks.get(i));
        }
    }
    public void play(){
        if(tracks.isEmpty()){
            System.out.println("No tracks available.");
            return;
        }
        setPlaying(true);
        System.out.println("Playing: " + tracks.get(currentIndex));
    }

    public void pause(){
        playing = false;
        System.out.println("Paused");
    }

    public void skip(){
        if(tracks.isEmpty()){
            System.out.println("No tracks to skip.");
            return;
        } 
        currentIndex = (currentIndex + 1) % tracks.size();
        System.out.println("Skipping to next track: " + tracks.get(currentIndex));
    }
    public void execute(){
        play();
        System.out.println("Resuming playback: " + tracks.get(currentIndex));
    }
}
