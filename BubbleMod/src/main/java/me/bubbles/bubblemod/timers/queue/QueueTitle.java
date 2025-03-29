package me.bubbles.bubblemod.timers.queue;
import net.minecraft.text.Text;

public class QueueTitle extends Queue {

    private String message;

    public QueueTitle(String message) {
        this.message=message;
    }

    @Override
    public void run() {
        clientInstance.getMinecraftClient().inGameHud.setTitle(Text.of(message));
    }

}
