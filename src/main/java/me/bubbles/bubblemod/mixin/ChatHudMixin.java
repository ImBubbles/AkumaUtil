package me.bubbles.bubblemod.mixin;

import me.bubbles.bubblemod.Client;
import me.bubbles.bubblemod.module.Mod;
import me.bubbles.bubblemod.module.categories.chat.InfiChat;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.MessageIndicator;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatHud.class)
public class ChatHudMixin {
    private Client client = Client.getInstance();

    @ModifyConstant(
            method = {"addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;ILnet/minecraft/client/gui/hud/MessageIndicator;Z)V"},
            constant = {@Constant(
                    intValue = 100
            )},
            expect = 2
    )
    public int changeMaxHistory(int original) {
        for(Mod mod : client.getModuleManager().getEnabledModules()) {
            if(mod instanceof InfiChat) {
                return 16384;
            }
        }
        return original;
    }

    @Inject(method = "addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;Lnet/minecraft/client/gui/hud/MessageIndicator;)V", at = @At("INVOKE"), cancellable = true)
    public void onAddMessage(Text message,
                             @Nullable MessageSignatureData signature,
                             @Nullable MessageIndicator indicator, CallbackInfo ci) {
        client.onChat(message, signature, indicator, ci);
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
