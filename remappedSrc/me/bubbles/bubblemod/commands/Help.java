package me.bubbles.bubblemod.commands;

import me.bubbles.bubblemod.Client;
import net.minecraft.text.Text;

public class Help extends Command {

    public Help() {
        super("help", "Displays all commands", true);
    }

    @Override
    public void run(String message) {
        String result="";

        for(Command command : CommandManager.getActiveCommands()) {
            result+="§c."+command.getCommand()+" §7| §f"+command.getDesc()+"\n";
        }

        Client.mc.inGameHud.getChatHud().addMessage(Text.of("\n§8[§b§lBubbleMod§8]\n§fMenu Key: §7right_shift\n§fCommands:\n"+result));

    }

}
