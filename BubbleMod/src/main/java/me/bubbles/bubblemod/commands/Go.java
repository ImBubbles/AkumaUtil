package me.bubbles.bubblemod.commands;

public class Go extends Command {

    public Go() {
        super("go", "Go to mine", true);
    }

    @Override
    public void run(String message) {
        client.getMinecraftClient().getNetworkHandler().sendChatCommand("mine go");
    }

}
