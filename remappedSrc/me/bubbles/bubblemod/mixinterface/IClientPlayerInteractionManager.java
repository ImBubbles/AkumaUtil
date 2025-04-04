package me.bubbles.bubblemod.mixinterface;

import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public interface IClientPlayerInteractionManager
{
    public float getCurrentBreakingProgress();

    public void setBreakingBlock(boolean breakingBlock);

    public void windowClick_PICKUP(int slot);

    public void windowClick_QUICK_MOVE(int slot);

    public void windowClick_THROW(int slot);

    public void rightClickItem();

    public void rightClickBlock(BlockPos pos, Direction side, Vec3d hitVec);

    public void sendPlayerActionC2SPacket(PlayerActionC2SPacket.Action action,
                                          BlockPos blockPos, Direction direction);

    public void sendPlayerInteractBlockPacket(Hand hand,
                                              BlockHitResult blockHitResult);

    public void setBlockHitDelay(int delay);
}
