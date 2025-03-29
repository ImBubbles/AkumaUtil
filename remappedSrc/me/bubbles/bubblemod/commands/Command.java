package me.bubbles.bubblemod.commands;

public class Command {
    private String name;
    private String command;
    private String desc;
    private boolean enabled;

    public Command(String name, String desc, boolean enabled) {
        this.name=name;
        this.command=name.toLowerCase();
        this.desc=desc;
        this.enabled=enabled;
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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void toggleEnabled() {
        this.enabled=!this.enabled;
    }

    public void run(String message) {

    }

}
