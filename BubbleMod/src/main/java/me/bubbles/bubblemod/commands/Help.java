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

        for(Command command : client.getCommandManager().getActiveCommands()) {
            boolean hasAlias = !command.getAliases().isEmpty();
            result+="§c."+command.getCommand();
            result+=" §7| §f"+command.getDesc()+"\n";
            if(hasAlias) {
                result+="§7Aliases: ";
                for(String str : command.getAliases()) {
                    if(!command.getAliases().get(command.getAliases().size()-1).equals(str)) {
                        result+="§c."+str+"§7, ";
                    }else{
                        result+="§c."+str;
                    }
                }
                result+="\n";
            }
        }

        client.getMinecraftClient().inGameHud.getChatHud().addMessage(Text.of("\n§8[§b§lBubbleMod§8]\n§fMenu Key: §7right_shift\n§fCommands:\n"+result));

    }

}
