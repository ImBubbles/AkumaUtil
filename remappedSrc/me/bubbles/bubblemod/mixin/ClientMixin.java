package me.bubbles.bubblemod.mixin;

import me.bubbles.bubblemod.Client;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static me.bubbles.bubblemod.Client.mc;

@Mixin(MinecraftClient.class)
public class ClientMixin {

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    public void onTick(CallbackInfo ci) {
        if(mc.player!=null) {
            Client.instance.onTick();
        }
    }

}
