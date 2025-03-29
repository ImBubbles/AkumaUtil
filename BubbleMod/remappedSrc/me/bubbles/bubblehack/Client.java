package me.bubbles.bubblemod;

import me.bubbles.bubblemod.mixinterface.IMinecraftClient;
import me.bubbles.bubblemod.module.Mod;
import me.bubbles.bubblemod.module.ModuleManager;
import me.bubbles.bubblemod.ui.screens.clickgui.ClickGUI;
import net.fabricmc.api.ModInitializer;

import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;

public class Client implements ModInitializer {

    public static final Client instance = new Client();
    public static MinecraftClient mc = MinecraftClient.getInstance();
    public static IMinecraftClient imc = (IMinecraftClient)mc;
    private boolean isEnabled=false;

    @Override
    public void onInitialize() {
        this.isEnabled=true;
    }

    public void onKeyPress(int key, int action) {
        if(action == GLFW.GLFW_PRESS) {
            for(Mod mod : ModuleManager.instance.getModules()) {
                if(key==mod.getKey()) mod.toggle();
            }

            if(key == GLFW.GLFW_KEY_RIGHT_SHIFT) {
                mc.setScreen(ClickGUI.instance);
            }

        }
    }

    public void onTick() {
        if (mc.player!=null) {
            for(Mod mod : ModuleManager.instance.getEnabledModules()) {
                mod.onTick();
            }
        }
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public static double roundCoordinate(double n) {
        n = Math.round(n * 100) / 100d;  // Round to 1/100th
        return Math.nextAfter(n, n + Math.signum(n));  // Fix floating point errors
    }

}
