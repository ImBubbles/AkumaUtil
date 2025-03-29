package me.bubbles.bubblemod.commands;

import me.bubbles.bubblemod.Client;

import java.util.ArrayList;
import java.util.Arrays;

public class Command {
    private String name;
    private String command;
    private String desc;
    private boolean enabled;
    private ArrayList<String> aliases;
    public Client client = Client.getInstance();

    public Command(String name, String desc, boolean enabled) {
        this.name=name;
        this.command=name.toLowerCase();
        this.desc=desc;
        this.enabled=enabled;
        this.aliases=new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getCommand() {
        return command;
    }

    public String getDesc() {
        return desc;
    }
    public ArrayList<String> getAliases() {
        return aliases;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void toggleEnabled() {
        this.enabled=!this.enabled;
    }
    public void addAliases(String... alias) {
        aliases.addAll(Arrays.asList(alias));
    }

    public void run(String message) {

    }

}
