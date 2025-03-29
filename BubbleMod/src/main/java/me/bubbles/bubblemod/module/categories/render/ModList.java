package me.bubbles.bubblemod.module.categories.render;

import me.bubbles.bubblemod.module.Mod;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Comparator;
import java.util.List;

public class ModList extends Mod {
    public ModList() {
        super("Mod List","Displays the mods in top right", Category.RENDER);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onRender(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        int index=0;

        int sWidth=mc.getWindow().getScaledWidth();
        int sHeight=mc.getWindow().getScaledHeight();

        List<Mod> enabled = clientInstance.getModuleManager().getEnabledModules();
        enabled.sort(Comparator.comparingInt(m -> (int)mc.textRenderer.getWidth(((Mod)m).getDisplayName())).reversed());

        for(Mod mod : enabled) {
            if(!(mod instanceof ModList)) {
                mc.textRenderer.drawWithShadow(matrices,mod.getDisplayName(), (sWidth-4)-mc.textRenderer.getWidth(mod.getDisplayName()), 10+(index*mc.textRenderer.fontHeight), -1);
                index++;
            }
        }
    }

}
