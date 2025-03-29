package me.bubbles.bubblemod;

import me.bubbles.bubblemod.commands.CommandManager;
import me.bubbles.bubblemod.files.FileHandler;
import me.bubbles.bubblemod.files.FileManager;
import me.bubbles.bubblemod.module.Mod;
import me.bubbles.bubblemod.module.ModuleManager;
import me.bubbles.bubblemod.module.categories.chat.AntiSpam;
import me.bubbles.bubblemod.module.categories.chat.ReactionTimer;
import me.bubbles.bubblemod.module.categories.movement.Sprint;
import me.bubbles.bubblemod.module.categories.render.FullBright;
import me.bubbles.bubblemod.module.categories.render.ModList;
import me.bubbles.bubblemod.module.categories.render.Watermark;
import me.bubbles.bubblemod.mysql.MySQL;
import me.bubbles.bubblemod.ui.screens.clickgui.ClickGUI;
import net.fabricmc.api.ModInitializer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.MessageIndicator;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class Client implements ModInitializer {

    private static FileManager fileManager;
    private static FileHandler antiSpamList;

    public static final Client instance = new Client();
    public static MinecraftClient mc = MinecraftClient.getInstance();
    private static CommandManager commandManager = new CommandManager();
    private boolean isEnabled=false;
    public static boolean whitelisted=false;

    @Override
    public void onInitialize() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        this.isEnabled=true;
        initMods();

        // Anti Spam List

        fileManager=new FileManager();
        File file = new File(System.getProperty("java.io.tmpdir")+"\\BubbleMod_AntiSpam.txt");
        try {
            if(file.createNewFile()) {
                antiSpamList = new FileHandler(file);
                antiSpamList.write("sell");
                antiSpamList.write("buy");
                antiSpamList.write("offer");
                antiSpamList.write("staff applications");
                antiSpamList.write("not voted for us today");
                antiSpamList.write(" has won ");
                antiSpamList.write("VOTE >>");
                antiSpamList.write("/buy");
                antiSpamList.write("has just purchased the");
                antiSpamList.write("has taken advantage of");
                antiSpamList.write("has opened a");
                antiSpamList.write("[STORE]");
                antiSpamList.write("has redeemed a");
                antiSpamList.write("from a voucher");
                antiSpamList.write("[TIP]");
                antiSpamList.write("[INFO]");
                antiSpamList.write("from your lootcrate.");
                antiSpamList.write("You have redeemed a");
                antiSpamList.write("CB");
                antiSpamList.write("/ah");
                antiSpamList.write("/msg");
                antiSpamList.write("/vote");
                antiSpamList.write("twitch.tv");
                antiSpamList.write("youtube.com");
                antiSpamList.write("Check it out @");
                antiSpamList.write("» gg");
                antiSpamList.write("discord.gg/akumamc");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        antiSpamList=new FileHandler(file);

    }

    public void onKeyPress(int key, int action) {

        if(mc.player!=null) {

            if(action == GLFW.GLFW_PRESS) {

                for(Mod mod : ModuleManager.instance.getModules()) {

                    if(key==mod.getKey()) mod.toggle();
                }

                if(key == GLFW.GLFW_KEY_RIGHT_SHIFT) {

                    if(whitelisted) {
                        mc.openScreen(ClickGUI.instance);
                    }else{
                        try {
                            MySQL mySQL = new MySQL().connect();
                            if(mySQL.getData(mc.player.getUuidAsString()).isValid()) {
                                whitelisted=true;
                                mc.openScreen(ClickGUI.instance);
                                Client.mc.inGameHud.getChatHud().addMessage(Text.of("§8[§b§lBubbleMod§8] §fUUID confirmed on whitelist, welcome back."));
                            }else{
                                Client.mc.inGameHud.getChatHud().addMessage(Text.of("§8[§b§lBubbleMod§8] §fUUID not on whitelist, bad boy!"));
                            }
                            mySQL.closeConnection();
                        } catch (SQLException e) {
                            e.printStackTrace();
                            Client.mc.inGameHud.getChatHud().addMessage(Text.of("§8[§b§lBubbleMod§8] §cUnable to connect to MySQL."));
                        }
                    }

                }

            }

        }

    }

    public static FileManager getFileManager() {
        return fileManager;
    }

    public static FileHandler getAntiSpamList() {
        return antiSpamList;
    }

    public void onTick() {
        if (mc.player!=null) {
            for(Mod mod : ModuleManager.instance.getEnabledModules()) {
                mod.onTick();
            }
        }
    }

    public void onChat(Text message, MessageSignatureData signature, MessageIndicator indicator, CallbackInfo ci) {
        if (mc.player!=null) {
            for(Mod mod : ModuleManager.instance.getEnabledModules()) {
                mod.onChat(message, signature, indicator, ci);
            }
        }
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public static double roundCoordinate(double n) {
        n = Math.round(n * 100) / 100d;  // Round to 1/100th
        return Math.nextAfter(n, n + Math.signum(n));  // Fix floating point errors
    }

    private void initMods() {
        ModuleManager.instance.addModules(new Sprint(), new Watermark(), new ModList(), new FullBright(), new AntiSpam(), new ReactionTimer());
    }

}
