package me.bubbles.bubblemod.module.categories.movement;
import me.bubbles.bubblemod.module.Mod;
import me.bubbles.bubblemod.module.settings.NumberSetting;

import java.util.stream.Stream;

import net.minecraft.util.math.Box;
import net.minecraft.util.shape.VoxelShape;

public class Parkour extends Mod {

    private NumberSetting minDepth = new NumberSetting("Min Depth", 0.05, 0.5, 0.05, 0.05);
    private NumberSetting edgeDistance = new NumberSetting("Edge Dis", 0.001, 0.25, 0.001, 0.001);

    public Parkour() {
        super("Parkour","Auto jumps at edge",Category.MOVEMENT);
        addSettings(minDepth, edgeDistance);
    }

    @Override
    public void onTick() {

        if(!mc.player.isOnGround() || mc.options.jumpKey.isPressed())
            return;

        if(mc.player.isSneaking() || mc.options.sneakKey.isPressed())
            return;

        Box box = mc.player.getBoundingBox();
        Box adjustedBox = box.stretch(0, -minDepth.getValue(), 0)
                .expand(-edgeDistance.getValue(), 0, -edgeDistance.getValue());

        Stream<VoxelShape> blockCollisions = imc.getWorld().getBlockCollisionsStream(mc.player, adjustedBox);

        if(blockCollisions.findAny().isPresent())
            return;

        mc.player.jump();

        super.onTick();
    }
}
