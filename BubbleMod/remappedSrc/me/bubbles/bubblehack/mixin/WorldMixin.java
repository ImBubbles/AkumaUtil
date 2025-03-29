package me.bubbles.bubblemod.mixin;

import me.bubbles.bubblemod.mixinterface.IWorld;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;


@Mixin(World.class)
public abstract class WorldMixin implements WorldAccess, AutoCloseable, IWorld {
    @Inject(at = {@At("HEAD")},
            method = {"getRainGradient(F)F"},
            cancellable = true)
    private void onGetRainGradient(float f, CallbackInfoReturnable<Float> cir)
    {

    }

    @Override
    public float getSkyAngle(float tickDelta)
    {

        long timeOfDay = getLevelProperties().getTimeOfDay();

        return getDimension().getSkyAngle(timeOfDay);
    }

    @Override
    public int getMoonPhase()
    {
        return getDimension().getMoonPhase(getLunarTime());
    }

    @Override
    public Stream<VoxelShape> getBlockCollisionsStream(@Nullable Entity entity,
                                                       Box box)
    {
        return StreamSupport
                .stream(getBlockCollisions(entity, box).spliterator(), false);
    }
}
