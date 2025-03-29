package me.bubbles.bubblemod.module.categories.chat;

import me.bubbles.bubblemod.Client;
import me.bubbles.bubblemod.module.Mod;
import net.minecraft.client.gui.hud.MessageIndicator;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class ReactionTimer extends Mod {

    private final int seconds = 20;
    private final int time = 200*seconds;
    public static int timer=0;

    public ReactionTimer() {
        super("ReacTimer","Reaction Timer", Category.CHAT);
    }

    @Override
    public void onEnable() {
        timer=time;
    }

    @Override
    public void onDisable() {
        timer=time;
    }

    @Override
    public void onTick() {

        timer=clamp(timer-1,0,time);

        int[] list = {60*seconds,30*seconds,15*seconds,10*seconds,5*seconds,4*seconds,3*seconds,2*seconds,1*seconds};

        for(int i : list) {
            if(timer==i) {
                Client.mc.inGameHud.getChatHud().addMessage(Text.of("§8[§b§lBubbleMod§8] §fChat Reaction In Approx: §c"+i/seconds+"s"));
            }
        }

    }

    @Override
    public void onChat(Text message, MessageSignatureData signature, MessageIndicator indicator, CallbackInfo ci) {
        if(message.getString().contains("REACTION » Type the following word")||message.getString().contains("REACTION » Unscramble")||message.getString().contains("REACTION » Solve the following equation")) {
            timer=clamp(time,0,time);;
            Client.mc.inGameHud.getChatHud().addMessage(Text.of("§8[§b§lBubbleMod§8] §fChat Reaction Timer Reset"));
        }
    }

    public int clamp(int now, int min, int max) {
        if(now<min) {
            return min;
        }else if(now>max) {
            return max;
        }else{
            return now;
        }
    }

}
