package me.bubbles.bubblemod.module;

import me.bubbles.bubblemod.Client;
import me.bubbles.bubblemod.module.settings.Setting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.MessageIndicator;
import net.minecraft.network.Packet;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

public class Mod {

    private Category category;

    private String name;
    private String displayName;
    private String description;
    private int key;
    private boolean enabled;

    private List<Setting> settings = new ArrayList<>();

    protected static final MinecraftClient mc = Client.mc;

    public Mod(String name, String description, Category category) {
        this.name=name;
        this.displayName=name;
        this.description=description;
        this.category=category;
    }

    public List<Setting> getSettings() {
        return settings;
    }

    public void addSetting(Setting setting) {
        this.settings.add(setting);
    }

    public void addSettings(Setting... settings) {
        for(Setting setting : settings) {
            addSetting(setting);
        }
    }

    public void toggle() {
        this.enabled = !this.enabled;
        if(enabled) onEnable();
        else onDisable();
    }

    public void onEnable() {

    }

    public void onDisable() {

    }

    public void onTick() {

    }

    public void onPacket(Packet<?> packet) {

    }

    public void onChat(Text message, MessageSignatureData signature, MessageIndicator indicator, CallbackInfo ci) {

    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;

        if (enabled) onEnable();
        else onDisable();
    }

    public enum Category {
        RENDER("Render"),
        MOVEMENT("Movement"),
        CHAT("Chat");

        public String name;

        private Category(String name) {
            this.name=name;
        }

    }

}
