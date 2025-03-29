package me.bubbles.bubblemod.mixin;

import me.bubbles.bubblemod.Client;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Inject(method = "render", at = @At("RETURN"), cancellable=true)
    public void renderHud(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        Client.getInstance().onRender(matrices, tickDelta, ci);
    }

}
