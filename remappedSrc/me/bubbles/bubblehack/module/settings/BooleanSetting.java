package me.bubbles.bubblemod.module.settings;

public class BooleanSetting extends Setting {

    private boolean enabled;

    public BooleanSetting(String name, boolean defaultValue) {
        super(name);
        this.enabled=defaultValue;

    }

    public void toggle() {
        this.enabled=!this.enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}
