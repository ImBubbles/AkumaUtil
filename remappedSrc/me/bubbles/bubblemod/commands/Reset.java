package me.bubbles.bubblemod.commands;

import me.bubbles.bubblemod.Client;

public class Reset extends Command {
    public Reset() {
        super("reset","Reset mine",true);
    }

    @Override
    public void run(String message) {
        Client.mc.getNetworkHandler().sendChatCommand("mine reset");
    }

}
