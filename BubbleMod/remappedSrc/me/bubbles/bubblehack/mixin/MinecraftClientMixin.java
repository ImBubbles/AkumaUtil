package me.bubbles.bubblemod.mixin;

import java.io.File;
import java.util.UUID;

import me.bubbles.bubblemod.Client;
import me.bubbles.bubblemod.mixinterface.IClientPlayerEntity;
import me.bubbles.bubblemod.mixinterface.IClientPlayerInteractionManager;
import me.bubbles.bubblemod.mixinterface.IMinecraftClient;
import me.bubbles.bubblemod.mixinterface.IWorld;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.minecraft.UserApiService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.WindowEventHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.util.ProfileKeys;
import net.minecraft.client.util.Session;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.thread.ReentrantThreadExecutor;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin
        extends ReentrantThreadExecutor<Runnable>
        implements WindowEventHandler, IMinecraftClient
{
    @Shadow
    @Final
    public File runDirectory;
    @Shadow
    private int itemUseCooldown;
    @Shadow
    private ClientPlayerInteractionManager interactionManager;
    @Shadow
    private ClientPlayerEntity player;
    @Shadow
    public ClientWorld world;
    @Shadow
    @Final
    private Session session;
    @Shadow
    @Final
    private YggdrasilAuthenticationService authenticationService;

    private Session clientSession;
    private ProfileKeys clientProfileKeys;

    private MinecraftClientMixin(Client client, String string_1)
    {
        super(string_1);
    }

    /*@Inject(at = {@At(value = "FIELD",
            target = "Lnet/minecraft/client/MinecraftClient;crosshairTarget:Lnet/minecraft/util/hit/HitResult;",
            ordinal = 0)}, method = {"doAttack()Z"}, cancellable = true)
    private void onDoAttack(CallbackInfoReturnable<Boolean> cir)
    {

    }*/

    /*@Inject(at = {@At(value = "FIELD",
            target = "Lnet/minecraft/client/MinecraftClient;itemUseCooldown:I",
            ordinal = 0)}, method = {"doItemUse()V"}, cancellable = true)
    private void onDoItemUse(CallbackInfo ci)
    {

    }*/

    @Inject(at = @At("HEAD"),
            method = {"getSession()Lnet/minecraft/client/util/Session;"},
            cancellable = true)
    private void onGetSession(CallbackInfoReturnable<Session> cir)
    {
        if(clientSession == null)
            return;

        cir.setReturnValue(clientSession);
    }

    /*@Redirect(at = @At(value = "FIELD",
            target = "Lnet/minecraft/client/MinecraftClient;session:Lnet/minecraft/client/util/Session;",
            opcode = Opcodes.GETFIELD,
            ordinal = 0),
            method = {
                    "getSessionProperties()Lcom/mojang/authlib/properties/PropertyMap;"})
    private Session getSessionForSessionProperties(MinecraftClient mc)
    {
        if(clientSession != null)
            return clientSession;

        return session;
    }*/

    @Override
    public void rightClick()
    {
        doItemUse();
    }

    @Override
    public int getItemUseCooldown()
    {
        return itemUseCooldown;
    }

    @Override
    public void setItemUseCooldown(int itemUseCooldown)
    {
        this.itemUseCooldown = itemUseCooldown;
    }

    @Override
    public IClientPlayerEntity getPlayer()
    {
        return (IClientPlayerEntity)player;
    }

    @Override
    public IWorld getWorld()
    {
        return (IWorld)world;
    }

    @Override
    public IClientPlayerInteractionManager getInteractionManager()
    {
        return (IClientPlayerInteractionManager)interactionManager;
    }

    /*@Override
    public void setSession(Session session)
    {
        clientSession = session;

        UserApiService userApiService =
                client_createUserApiService(session.getAccessToken());
        UUID uuid = clientSession.getProfile().getId();
        clientProfileKeys =
                new ProfileKeys(userApiService, uuid, runDirectory.toPath());

    }*/

    private UserApiService client_createUserApiService(String accessToken)
    {
        try
        {
            return authenticationService.createUserApiService(accessToken);

        }catch(AuthenticationException e)
        {
            e.printStackTrace();
            return UserApiService.OFFLINE;
        }
    }

    @Shadow
    private void doItemUse()
    {

    }
}
