package me.bubbles.bubblemod.commands.AutoChat;

import me.bubbles.bubblemod.timers.Timer;
import net.minecraft.client.MinecraftClient;

public class ChatTimer extends Timer {

    private String message;

    public ChatTimer(String message) {
        this.message=message;
    }

    public void setMessage(String message) {
        this.message=message;
    }

    @Override
    public void timeOver() {
        MinecraftClient.getInstance().getNetworkHandler().sendChatMessage(message);
        reset();
    }

}