package me.bubbles.bubblemod.mysql;

public class User {
    private String uuid;
    private String discord;
    private int power;

    public User(String uuid, String discord, int power) {
        this.power=power;
        this.discord=discord;
        this.uuid=uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public String getDiscord() {
        return discord;
    }

    public int getPower() {
        return power;
    }

    public boolean isValid() {
        return power!=-1;
    }

}
