package me.bubbles.bubblemod.module.categories.chat;

import me.bubbles.bubblemod.Client;
import me.bubbles.bubblemod.module.Mod;
import net.minecraft.client.gui.hud.MessageIndicator;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class Pings extends Mod {

    // NOT WORKING

    private String ign;
    private int timeOut=0;

    public Pings() {
        super("Pings","Makes a ding whenever someone says your IGN",Category.CHAT);
    }

    @Override
    public void onChat(Text message, MessageSignatureData signature, MessageIndicator indicator, CallbackInfo ci) {

        if(timeOut!=3) {
            timeOut++;
            return;
        }

        String ign = Client.getInstance().getMinecraftClient().player.getName().getString();

        String msg = message.getString();

        if(msg.contains(ign)&&(!msg.contains("] "+ign))) {
            Client.getInstance().getMinecraftClient().player.getWorld().playSound(
                    null,
                    Client.getInstance().getMinecraftClient().player.getBlockPos(),
                    SoundEvents.BLOCK_ANVIL_FALL,
                    SoundCategory.MASTER,
                    1f,
                    1f);
            System.out.println("bro");
        }

        timeOut=0;

    }

}