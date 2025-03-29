package me.bubbles.bubblemod;

import me.bubbles.bubblemod.commands.CommandManager;
import me.bubbles.bubblemod.files.FileHandler;
import me.bubbles.bubblemod.files.FileManager;
import me.bubbles.bubblemod.json.jsonHook;
import me.bubbles.bubblemod.json.jsonValues;
import me.bubbles.bubblemod.module.Mod;
import me.bubbles.bubblemod.module.ModuleManager;
import me.bubbles.bubblemod.module.categories.chat.AntiSpam;
import me.bubbles.bubblemod.module.categories.chat.InfiChat;
import me.bubbles.bubblemod.module.categories.chat.ReactionTimer;
import me.bubbles.bubblemod.module.categories.movement.Sprint;
import me.bubbles.bubblemod.module.categories.other.ImprovedAlerts;
import me.bubbles.bubblemod.module.categories.other.PetOverlay;
import me.bubbles.bubblemod.module.categories.render.FullBright;
import me.bubbles.bubblemod.module.categories.render.ModList;
import me.bubbles.bubblemod.module.categories.render.Watermark;
import me.bubbles.bubblemod.mysql.MySQL;
import me.bubbles.bubblemod.timers.TimerManager;
import me.bubbles.bubblemod.ui.screens.clickgui.ClickGUI;
import net.fabricmc.api.ModInitializer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.MessageIndicator;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public class Client implements ModInitializer {

    private static Client clientInstance;
    private MinecraftClient mc;
    private FileManager fileManager;
    private FileHandler antiSpamList;
    private FileHandler settings;
    private CommandManager commandManager;
    private ModuleManager moduleManager;
    private TimerManager timerManager;
    private boolean isEnabled=false;
    private boolean whitelisted=false;

    @Override
    public void onInitialize() {

        // No it's not a rat, stop looking!

        /*try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/

        this.isEnabled=true;

        // SET INSTANCES PRIMARY

        clientInstance=this;
        mc=MinecraftClient.getInstance();

        moduleManager = new ModuleManager();
        fileManager=new FileManager();
        timerManager=new TimerManager();

        // Anti Spam List

        File antiSpam = new File(System.getProperty("java.io.tmpdir")+"\\BubbleMod_AntiSpam.txt");
        try {
            if(antiSpam.createNewFile()) {
                antiSpamList = new FileHandler(antiSpam);
                antiSpamList.write("sell");
                antiSpamList.write("buy");
                antiSpamList.write("offer");
                //antiSpamList.write("/trade");
                antiSpamList.write("staff applications");
                antiSpamList.write("not voted for us today");
                antiSpamList.write("has won");
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

        // Settings

        File settingsFile = new File(System.getProperty("java.io.tmpdir")+"\\BubbleMod_Settings.txt");
        settings = new FileHandler(settingsFile);
        try {
            if(settingsFile.createNewFile()) {
                settings.write("firstTime:true");
                settings.write("logUUID:false");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // SET INSTANCES SECONDARY
        commandManager = new CommandManager();

        // INIT MODS
        initMods();

    }

    public void onKeyPress(int key, int action) {

        if(mc.player!=null) {

            if(action == GLFW.GLFW_PRESS) {

                for(Mod mod : moduleManager.getModules()) {

                    if(key==mod.getKey()) mod.toggle();

                }

                if(key == GLFW.GLFW_KEY_RIGHT_SHIFT) {

                    if(whitelisted) {
                        mc.setScreen(ClickGUI.instance);
                    }else{
                        try {
                            if(settings.getStringStringData(":").get("firstTime").equals("false")) {
                                if(confirmWhitelist(true)) {
                                    logUUID();
                                    mc.setScreen(ClickGUI.instance);
                                    mc.inGameHud.getChatHud().addMessage(Text.of("§8[§b§lBubbleMod§8] §fUUID confirmed on whitelist, welcome back."));
                                }else{
                                    mc.inGameHud.getChatHud().addMessage(Text.of("§8[§b§lBubbleMod§8] §fUUID not on whitelist, bad boy!"));
                                }
                            }else{
                                if(confirmWhitelist(false)) {
                                    mc.inGameHud.getChatHud().addMessage(Text.of("§8[§b§lBubbleMod§8] §fFirst time users must allow or deny (optional) to having their UUID logged when using BubbleMod. Type §c.yes §for §c.no §fin chat to allow or deny this."));
                                }else{
                                    mc.inGameHud.getChatHud().addMessage(Text.of("§8[§b§lBubbleMod§8] §fUUID not on whitelist, bad boy!"));
                                }
                            }
                        } catch (FileNotFoundException e) {
                            mc.inGameHud.getChatHud().addMessage(Text.of("§8[§b§lBubbleMod§8] §cCorrupted settings file."));
                        }
                    }

                }

            }

        }

    }

    private boolean confirmWhitelist(boolean set) {
        /*MySQL mySQL=null;
        try {
            mySQL = new MySQL().connect();
            if(mySQL.getData(mc.player.getUuidAsString()).isValid()) {
                if(set) {
                    whitelisted=true;
                }
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            mc.inGameHud.getChatHud().addMessage(Text.of("§8[§b§lBubbleMod§8] §cUnable to connect to MySQL."));
        }
        try {
            mySQL.closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;*/
        mc.inGameHud.getChatHud().addMessage(Text.of("§8[§b§lBubbleMod§8] §cYou are using a version of BubbleMod with no whitelist."));
        whitelisted=true;
        return true;
    }

    private void logUUID() throws FileNotFoundException {
        /*jsonValues jValues = new jsonValues(mc);
        jsonHook jHook = new jsonHook(jValues.getJsonAddress());
        jHook.addEmbed(jValues.getEmbed());
        jHook.execute();*/
    }

    public void onTick() {
        if (mc.player!=null) {
            for(Mod mod : moduleManager.getEnabledModules()) {
                mod.onTick();
            }
            timerManager.onTick();
        }
    }

    public void onRender(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        if (mc.player!=null) {
            for(Mod mod : moduleManager.getEnabledModules()) {
                mod.onRender(matrices, tickDelta, ci);
            }
        }
    }

    public void onChat(Text message, MessageSignatureData signature, MessageIndicator indicator, CallbackInfo ci) {
        if (mc.player!=null) {
            for(Mod mod : moduleManager.getEnabledModules()) {
                mod.onChat(message, signature, indicator, ci);
            }
        }
    }

    public boolean isEnabled() {
        return isEnabled;
    }
    private void initMods() {
        moduleManager.addModules(
                new Sprint(),
                new Watermark(),
                new ModList(),
                new FullBright(),
                new AntiSpam(),
                new ReactionTimer(),
                new InfiChat(),
                //new Pings(),
                //new RichPresence(),
                new ImprovedAlerts(),
                new PetOverlay()
        );
    }

    // GETTERS

    public static Client getInstance() {
        return clientInstance;
    }

    public MinecraftClient getMinecraftClient() {
        return mc;
    }
    public boolean isWhitelisted() {
        return this.whitelisted;
    }
    public CommandManager getCommandManager() {
        return this.commandManager;
    }
    public ModuleManager getModuleManager() {
        return this.moduleManager;
    }
    public FileManager getFileManager() {
        return fileManager;
    }

    public FileHandler getAntiSpamList() {
        return new FileHandler(new File(System.getProperty("java.io.tmpdir")+"\\BubbleMod_AntiSpam.txt"));
    }

    public FileHandler getSettings() {
        return settings;
    }

    public TimerManager getTimerManager() {
        return timerManager;
    }

}
