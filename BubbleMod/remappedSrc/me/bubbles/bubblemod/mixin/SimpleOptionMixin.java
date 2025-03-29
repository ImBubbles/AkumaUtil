package me.bubbles.bubblemod.mixin;

import java.util.Objects;
import java.util.function.Consumer;

import me.bubbles.bubblemod.mixinterface.ISimpleOption;

import net.minecraft.client.option.SimpleOption;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.client.MinecraftClient;

@Mixin(SimpleOption.class)
public class SimpleOptionMixin<T> implements ISimpleOption<T>
{
    @Shadow
    T value;

    @Shadow
    @Final
    private Consumer<T> changeCallback;

    @Override
    public void forceSetValue(T newValue)
    {
        if(!MinecraftClient.getInstance().isRunning())
        {
            value = newValue;
            return;
        }

        if(!Objects.equals(value, newValue))
        {
            value = newValue;
            changeCallback.accept(value);
        }
    }
}
