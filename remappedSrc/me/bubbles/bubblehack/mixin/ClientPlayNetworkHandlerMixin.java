package me.bubbles.bubblemod.mixin;

import me.bubbles.bubblemod.Client;
import me.bubbles.bubblemod.module.Mod;
import me.bubbles.bubblemod.module.ModuleManager;
import me.bubbles.bubblemod.module.categories.liveoverflow.Demo;
import me.bubbles.bubblemod.module.categories.liveoverflow.ForceSurvival;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static me.bubbles.bubblemod.Client.roundCoordinate;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayNetworkHandlerMixin {

    @ModifyArg(method="onVehicleMove", at = @At(value="INVOKE",target="Lnet/minecraft/network/packet/c2s/play/VehicleMoveC2SPacket;<init>(Lnet/minecraft/entity/Entity;)V"), index=0)
    public Entity onVehicleMove(Entity entity) {
        entity.setPos(roundCoordinate(entity.getX()),entity.getY(),roundCoordinate(entity.getZ()));
        return entity;
    }

    @Inject(method="onGameStateChange", at = @At("HEAD"), cancellable = true)
    public void onGameStateChange(GameStateChangeS2CPacket packet, CallbackInfo ci) {
        for(Mod mod : ModuleManager.instance.getEnabledModules()) {
            if(mod instanceof Demo) {
                if(packet.getReason()==GameStateChangeS2CPacket.DEMO_MESSAGE_SHOWN) {
                    ci.cancel();
                }
            } else if (mod instanceof ForceSurvival) {
                if(packet.getReason()==GameStateChangeS2CPacket.GAME_MODE_CHANGED) {
                    Client.mc.interactionManager.setGameMode(GameMode.SURVIVAL);
                    ci.cancel();
                }
            }
        }
    }

}