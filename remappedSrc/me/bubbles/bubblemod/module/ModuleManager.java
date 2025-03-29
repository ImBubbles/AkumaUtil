package me.bubbles.bubblemod.module;

import net.minecraft.network.Packet;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {

    public static final ModuleManager instance = new ModuleManager();
    private List<Mod> modules = new ArrayList<>();

    public ModuleManager() {
        addModules();
    }

    public List<Mod> getModules() {
        return modules;
    }

    public List<Mod> getEnabledModules() {
        List<Mod> enabled = new ArrayList<>();
        for(Mod mod : modules) {
            if(mod.isEnabled()) enabled.add(mod);
        }
        return enabled;
    }

    public List<Mod> getModulesInCategory(Mod.Category category) {

        List<Mod> categoryModules = new ArrayList<>();

        for(Mod mod : modules) {
            if(mod.getCategory()==category) {
                categoryModules.add(mod);
            }
        }

        return categoryModules;

    }

    public void onPacket(Packet<?> packet) {
        for(Mod mod : getEnabledModules()) {
            mod.onPacket(packet);
        }
    }

    public void addModules(Mod... mods) {
        for(Mod mod : mods) {
            modules.add(mod);
        }
    }

}
