package com.example.roomi.roomi;

public class RoomDatastructure {
    private String name;
    private int temperature;
    private int brightness;

    public RoomDatastructure() {

    }

    public RoomDatastructure(String name, int temperature, int brightness) {
        this.name = name;
        this.temperature = temperature;
        this.brightness = brightness;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.substring(0, 1).toUpperCase() + name.substring(1);
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
}
