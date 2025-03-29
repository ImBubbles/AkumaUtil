package me.bubbles.bubblemod.module.categories.movement;

import me.bubbles.bubblemod.module.Mod;

public class Sprint extends Mod {

    public Sprint() {
        super("Sprint", "Autosprint", Category.MOVEMENT);
    }

    @Override
    public void onTick() {
        mc.player.setSprinting(true);
        super.onTick();
    }

    @Override
    public void onDisable() {
        super.onTick();
    }

}
