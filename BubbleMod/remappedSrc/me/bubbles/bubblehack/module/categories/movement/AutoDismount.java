package me.bubbles.bubblemod.module.categories.movement;

import me.bubbles.bubblemod.module.Mod;

public class AutoDismount extends Mod {

    public AutoDismount() {
        super("AutoDismount","Auto dismounts",Category.MOVEMENT);
    }

    @Override
    public void onTick() {
        mc.player.dismountVehicle();
        super.onTick();
    }
}
