package me.bubbles.bubblemod.module.categories.render;

import me.bubbles.bubblemod.module.Mod;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

public class Watermark extends Mod {

    public Watermark() {
        super("Watermark","Displays the watermark",Category.RENDER);
    }

    @Override
    public void onRender(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        matrices.push();
        matrices.scale(2F,2F,2F);
        int sWidth=mc.getWindow().getScaledWidth();
        int sHeight=mc.getWindow().getScaledHeight();
        mc.textRenderer.drawWithShadow(matrices, Text.literal("BubbleMod"),2, 4, Color.CYAN.getRGB());
        matrices.pop();
    }

}