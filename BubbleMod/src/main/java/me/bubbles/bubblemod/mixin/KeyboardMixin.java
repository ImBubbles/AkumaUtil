package me.bubbles.bubblemod.mixin;

import me.bubbles.bubblemod.Client;
import net.minecraft.client.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public class KeyboardMixin {

    private final Client client = Client.getInstance();

    @Inject(method = "onKey", at = @At("HEAD"), cancellable=true)
    public void onKey(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci) {
        if(client!=null)
            client.onKeyPress(key, action);
    }

}