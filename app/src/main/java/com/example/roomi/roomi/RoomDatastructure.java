package com.example.roomi.roomi;

public class RoomDatastructure {
    private String name;
    private int temperature;
    private int brightness;
    private boolean securitySetup;
    private boolean homeSetup;
    private int accessLevel;

    public RoomDatastructure() {

    }

    public RoomDatastructure(String name, int temperature, int brightness, boolean securitySetup, boolean homeSetup, int accessLevel) {
        this.name = name;
        this.temperature = temperature;
        this.brightness = brightness;
        this.securitySetup = securitySetup;
        this.homeSetup = homeSetup;
        this.accessLevel = accessLevel;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {

        String[] arr = name.split(" ");
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < arr.length; i++) {
            sb.append(Character.toUpperCase(arr[i].charAt(0)))
                    .append(arr[i].substring(1)).append(" ");
        }
        this.name = sb.toString().trim();
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    public boolean isSecuritySetup() {
        return securitySetup;
    }

    public void setSecuritySetup(boolean securitySetup) {
        this.securitySetup = securitySetup;
    }

    public boolean isHomeSetup() {
        return homeSetup;
    }

    public void setHomeSetup(boolean homeSetup) {
        this.homeSetup = homeSetup;
    }

    public int getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(int accessLevel) {
        this.accessLevel = accessLevel;
    }
}