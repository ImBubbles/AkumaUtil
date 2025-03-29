package me.bubbles.bubblemod.commands;

import me.bubbles.bubblemod.Client;
import me.bubbles.bubblemod.commands.AutoChat.AutoChat;
import me.bubbles.bubblemod.files.FileHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class CommandManager {

    private Client client=Client.getInstance();
    private MinecraftClient mc=MinecraftClient.getInstance();
    private FileHandler settings;
    private ArrayList<Command> commandList = new ArrayList<>();
    private ArrayList<Command> activeCommands = new ArrayList<>();

    public CommandManager() {
        registerCommands();
        settings=client.getSettings();
    }

    public ArrayList<Command> getCommandList() {
        return commandList;
    }

    public ArrayList<Command> getActiveCommands() {
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
        addCommand(new ReacTimer(),new Help(),new Math(),new Reset(),new Go(),new Data(),new AutoChat());
    }

    public void onCommand(String message) throws FileNotFoundException {

        boolean valid=false;

        if(message.equals(".yes")) {
            settings.replace("firstTime","true","false",":");
            settings.replace("logUUID","false","true",":");
            client.getMinecraftClient().inGameHud.getChatHud().addMessage(Text.of("§8[§b§lBubbleMod§8] §fYour UUID will be logged when using this mod. Type §c.no §fat any time to stop this."));
            return;
        } else if (message.equals(".no")) {
            settings.replace("firstTime","true","false",":");
            settings.replace("logUUID","true","false",":");
            client.getMinecraftClient().inGameHud.getChatHud().addMessage(Text.of("§8[§b§lBubbleMod§8] §fYour UUID will not be logged when using this mod. Type §c.yes §fat any time to start this."));
            return;
        }

        if(client.isWhitelisted()) {

            boolean isArgs=false;
            String str;

            if(message.toLowerCase().contains(" ")) {
                isArgs=true;
                str=message.toLowerCase().split(" ")[0].replace(".","");
            }else
                str=message.toLowerCase().replace(".","");

            for(Command command : activeCommands) {

                if(str.equals(command.getCommand().toLowerCase())) {
                    command.run(message);
                    valid=true;
                }

                for(String alias : command.getAliases()) {
                    if(alias.equals(str)) {
                        command.run(message);
                        valid=true;
                    }
                }

            }

        } else {
            client.getMinecraftClient().inGameHud.getChatHud().addMessage(Text.of("§8[§b§lBubbleMod§8] §cOpen the gui first to confirm whitelist!"));
            return;
        }

        if(!valid)
            client.getMinecraftClient().inGameHud.getChatHud().addMessage(Text.of("§8[§b§lBubbleMod§8] §cInvalid command, use .help for all commands."));

    }

}
