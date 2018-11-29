package com.example.roomi.roomi;

// The data structure for for working with personal
public class PersonnelDatastructure {
    private String name;
    private String avatarColour;
    private int accessLevel;

    public PersonnelDatastructure() {

    }

    public PersonnelDatastructure(String name, String avatarColour, int accessLevel) {
        this.name = name;
        this.avatarColour = avatarColour;
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

    public String getAvatarColour() {
        return avatarColour;
    }

    public void setAvatarColour(String avatarColour) {
        this.avatarColour = avatarColour;
    }

    public int getaccessLevel() {
        return accessLevel;
    }

    public void setaccessLevel(int accessLevel) {
        this.accessLevel = accessLevel;
    }
}

