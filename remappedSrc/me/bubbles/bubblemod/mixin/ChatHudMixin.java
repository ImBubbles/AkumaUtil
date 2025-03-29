package me.bubbles.bubblemod.mixin;

import me.bubbles.bubblemod.Client;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.MessageIndicator;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatHud.class)
public class ChatHudMixin {

    @Inject(method = "addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;Lnet/minecraft/client/gui/hud/MessageIndicator;)V", at = @At("INVOKE"), cancellable = true)
    public void onAddMessage(Text message,
                             @Nullable MessageSignatureData signature,
                             @Nullable MessageIndicator indicator, CallbackInfo ci) {
        Client.instance.onChat(message, signature, indicator, ci);
    }

    @Shadow
    private void shadow$logChatMessage(Text message,
                                       @Nullable MessageIndicator indicator)
    {

    }

    @Shadow
    private void shadow$addMessage(Text message,
                                   @Nullable MessageSignatureData signature, int ticks,
                                   @Nullable MessageIndicator indicator, boolean refresh)
    {

    }

}
