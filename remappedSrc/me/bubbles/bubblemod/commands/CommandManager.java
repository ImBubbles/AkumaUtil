package me.bubbles.bubblemod.commands;

import me.bubbles.bubblemod.Client;
import me.bubbles.bubblemod.mysql.MySQL;
import me.bubbles.bubblemod.mysql.User;
import me.bubbles.bubblemod.ui.screens.clickgui.ClickGUI;
import net.minecraft.text.Text;

import java.util.ArrayList;

public class CommandManager {

    private static ArrayList<Command> commandList = new ArrayList<>();
    private static ArrayList<Command> activeCommands = new ArrayList<>();

    public CommandManager() {
        registerCommands();
    }

    public static ArrayList<Command> getCommandList() {
        return commandList;
    }

    public static ArrayList<Command> getActiveCommands() {
        return activeCommands;
    }

    public void addCommand(Command... commands) {
        for(Command command : commands) {
            commandList.add(command);
            if(command.isEnabled()) {
                activeCommands.add(command);
            }
        }
    }

    public void registerCommands() {
        addCommand(new ReacTimer(),new Help(),new Math(),new Reset(),new Go());
    }

    public void onCommand(String message) {

        boolean valid=false;

        if(Client.whitelisted) {
            for(Command command : activeCommands) {
                if(message.toLowerCase().startsWith("."+command.getCommand().toLowerCase())) {
                    command.run(message);
                    valid=true;
                }
            }
        } else {
            Client.mc.inGameHud.getChatHud().addMessage(Text.of("§8[§b§lBubbleMod§8] §cOpen the gui first to confirm whitelist!"));
            return;
        }

        if(!valid)
            Client.mc.inGameHud.getChatHud().addMessage(Text.of("§8[§b§lBubbleMod§8] §cInvalid command, use .help for all commands."));

    }

}
