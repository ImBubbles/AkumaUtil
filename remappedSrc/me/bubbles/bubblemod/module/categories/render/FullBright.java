package me.bubbles.bubblemod.module.categories.render;

import me.bubbles.bubblemod.mixinterface.ISimpleOption;
import me.bubbles.bubblemod.module.Mod;
import me.bubbles.bubblemod.module.settings.ModeSetting;
import me.bubbles.bubblemod.module.settings.NumberSetting;
import net.minecraft.client.option.SimpleOption;

public class FullBright extends Mod {

    private final ModeSetting mode = new ModeSetting(
            "Mode", "Gamma", "Gamma","Night-vision"
    );

    private final NumberSetting defaultGamma = new NumberSetting(
            "Default",
            0, 1, 0.5, 0.01
    );

    private boolean wasGammaChanged;
    private float nightVisionStrength;

    public FullBright() {
        super("FullBright","100% Gamma",Category.RENDER);
        addSettings(mode,defaultGamma);
    }

    @Override
    public void onEnable() {
        double gamma = mc.options.getGamma().getValue();

        if(gamma > 1)
            wasGammaChanged = true;
        else
            defaultGamma.setValue(gamma);
    }

    @Override
    public void onDisable() {
        resetGamma(defaultGamma.getValue());
    }

    @Override
    public void onTick() {

        boolean shouldChangeGamma =
                this.isEnabled() && mode.isMode("Gamma");

        if(shouldChangeGamma)
        {
            setGamma(16);
            return;
        }

        if(wasGammaChanged)
            resetGamma(defaultGamma.getValue());

    }

    private void setGamma(double target) {
        wasGammaChanged = true;

        SimpleOption<Double> gammaOption = mc.options.getGamma();
        @SuppressWarnings("unchecked")
        ISimpleOption<Double> gammaOption2 =
                (ISimpleOption<Double>)(Object)gammaOption;
        double oldGammaValue = gammaOption.getValue();

        if(Math.abs(oldGammaValue - target) <= 0.5)
        {
            gammaOption2.forceSetValue(target);
            return;
        }

        if(oldGammaValue < target)
            gammaOption2.forceSetValue(oldGammaValue + 0.5);
        else
            gammaOption2.forceSetValue(oldGammaValue - 0.5);
    }

    private void resetGamma(double target) {
        SimpleOption<Double> gammaOption = mc.options.getGamma();
        @SuppressWarnings("unchecked")
        ISimpleOption<Double> gammaOption2 =
                (ISimpleOption<Double>)(Object)gammaOption;
        double oldGammaValue = gammaOption.getValue();

        if(oldGammaValue < target)
            gammaOption2.forceSetValue(oldGammaValue + 0.5);
        else
            gammaOption2.forceSetValue(oldGammaValue - 0.5);
    }

    private void updateNightVision() {

        boolean shouldGiveNightVision =
                isEnabled() && mode.isMode("Night-vision");

        if(shouldGiveNightVision)
            nightVisionStrength = 1;
        else
            nightVisionStrength = 0;

    }

    public boolean isNightVisionActive() {
        return nightVisionStrength > 0;
    }

    public float getNightVisionStrength() {
        return nightVisionStrength;
    }

}
