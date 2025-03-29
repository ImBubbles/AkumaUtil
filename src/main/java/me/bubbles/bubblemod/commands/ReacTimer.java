package me.bubbles.bubblemod.commands;

import me.bubbles.bubblemod.Client;
import me.bubbles.bubblemod.module.categories.chat.ReactionTimer;
import net.minecraft.text.Text;

public class ReacTimer extends Command {

    public ReacTimer() {
        super("reactimer","Displays the amount of time left of the reaction timer",true);
        addAliases("rt");
    }

    @Override
    public void run(String message) {
        client.getMinecraftClient().inGameHud.getChatHud().addMessage(Text.of("§8[§b§lBubbleMod§8] §fChat Reaction In Approx: §c"+ formatSeconds(ReactionTimer.timer/20)));
    }

    private String formatSeconds(int input) {
        int minutes = input/60;
        int seconds = input-minutes*60;

        return minutes+"m "+seconds+"s";
    }

}
