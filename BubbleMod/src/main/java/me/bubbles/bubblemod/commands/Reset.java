package me.bubbles.bubblemod.commands;

import me.bubbles.bubblemod.Client;

public class Reset extends Command {
    public Reset() {
        super("reset","Reset mine",true);
        addAliases("r");
    }

    @Override
    public void run(String message) {
        client.getMinecraftClient().getNetworkHandler().sendChatCommand("mine reset");
    }

}
