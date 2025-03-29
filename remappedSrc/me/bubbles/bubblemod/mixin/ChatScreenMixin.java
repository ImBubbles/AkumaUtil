package me.bubbles.bubblemod.mixin;

import me.bubbles.bubblemod.Client;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChatScreen.class)
public class ChatScreenMixin extends Screen {
    @Shadow
    protected TextFieldWidget chatField;

    protected ChatScreenMixin(Text title) {
        super(title);
    }

    @Inject(at = @At("HEAD"),
            method = "sendMessage(Ljava/lang/String;Z)Z",
            cancellable = true)
    public void onSendMessage(String message, boolean addToHistory,
                              CallbackInfoReturnable<Boolean> cir)
    {
        if((message = normalize(message)).isEmpty())
            return;

        String newMessage = message;
        if(addToHistory)
            client.inGameHud.getChatHud().addToMessageHistory(newMessage);

        if(newMessage.startsWith("/"))
            client.player.networkHandler
                    .sendChatCommand(newMessage.substring(1));
        else if(newMessage.startsWith(".")) {
            Client.instance.getCommandManager().onCommand(message);
            cir.setReturnValue(false);
        }
        else client.player.networkHandler.sendChatMessage(newMessage);

        cir.setReturnValue(true);
    }

    @Shadow
    public String normalize(String chatText)
    {
        return null;
    }
}
