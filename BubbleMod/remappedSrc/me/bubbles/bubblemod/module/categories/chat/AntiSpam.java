package me.bubbles.bubblemod.module.categories.chat;

import me.bubbles.bubblemod.Client;
import me.bubbles.bubblemod.module.Mod;
import me.bubbles.bubblemod.module.settings.NumberSetting;
import net.minecraft.client.gui.hud.MessageIndicator;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class AntiSpam extends Mod {

    private NumberSetting tickUpdate = new NumberSetting("#",1200,72000,6000,200);
    private int ticks=0;
    private int blocked=0;

    public AntiSpam() {
        super("AntiSpam","Prevents chat spam", Category.CHAT);
        addSetting(tickUpdate);
    }

    @Override
    public void onChat(Text message, MessageSignatureData signature, MessageIndicator indicator, CallbackInfo ci) {

        String str = message.getString().toLowerCase();

        ArrayList<String> blacklist;

        try {
            blacklist = Client.getAntiSpamList().getStringData();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        if(blacklist!=null)
            for(int i=0; i<blacklist.size(); i++) {
                if(str.contains(blacklist.get(i).toLowerCase())) {
                    ci.cancel();
                    blocked++;
                }
            }

    }

    @Override
    public void onDisable() {
        ticks=0;
    }

    @Override
    public void onTick() {
        if(ticks<tickUpdate.getValueInt()) {
            ticks++;
        }else{
            Client.mc.inGameHud.getChatHud().addMessage(Text.of("§8[§b§lBubbleMod§8] §fBlocked messages: §c"+blocked));
            blocked=0;
            ticks=0;
        }
    }
}
