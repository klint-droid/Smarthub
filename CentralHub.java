public class CentralHub{
    private SmartDevice[] devices;

    public CentralHub() {
        devices = new SmartDevice[] {
            new SmartLight("Smart Light"),
            new Thermostat("Thermostat"),
            new SecurityCamera("Security Camera"),
            new SmartSpeaker("Smart Speaker"),
            new SmartLock("Smart Lock"),
            new SecurityFloodlight("Security Floodlight", 100, "White")
        };
    }

    public SmartDevice[] getDevices() {
        return devices;
    }
}