package me.bubbles.bubblemod.mixin;

import me.bubbles.bubblemod.module.Mod;
import me.bubbles.bubblemod.module.ModuleManager;
import me.bubbles.bubblemod.module.categories.liveoverflow.BotMovement;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import static me.bubbles.bubblemod.Client.roundCoordinate;

@Mixin(PlayerMoveC2SPacket.PositionAndOnGround.class)
public class PlayerPositionPacketMixin {
    @ModifyArgs(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/packet/c2s/play/PlayerMoveC2SPacket;<init>(DDDFFZZZ)V"))
    private static void init(Args args) {
        for(Mod mod : ModuleManager.instance.getEnabledModules()) {
            if(mod instanceof BotMovement) {
                args.set(0, roundCoordinate(args.get(0)));  // Round x
                args.set(2, roundCoordinate(args.get(2)));  // Round z
            }
        }
    }
}
