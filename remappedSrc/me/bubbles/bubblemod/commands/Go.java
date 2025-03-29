package me.bubbles.bubblemod.commands;

import me.bubbles.bubblemod.Client;

public class Go extends Command {

    public Go() {
        super("go", "Go to mine", true);
    }

    @Override
    public void run(String message) {
        Client.mc.getNetworkHandler().sendChatCommand("mine go");
    }

}
