package me.bubbles.bubblemod.module.categories.other;

import me.bubbles.bubblemod.module.Mod;
import me.bubbles.bubblemod.module.settings.NumberSetting;
import me.bubbles.bubblemod.timers.Timer;
import me.bubbles.bubblemod.timers.queue.QueueHandler;
import me.bubbles.bubblemod.timers.queue.QueueTitle;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.inventory.Inventory;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;
import java.util.Comparator;
import java.util.List;

public class PetOverlay extends Mod {

    private float petPercent=0;
    private float chargedTime=-1;
    private String percentStr="Loading...";
    private Color color=Color.white;
    private NumberSetting update = new NumberSetting("Update",0.5,10,2,0.5);
    private Timer updateTimer=new Timer().setTime(0);
    private QueueHandler queueHandler=new QueueHandler(20);

    public PetOverlay() {
        super("Pet Info","Displays an overlay of pet info",Category.OTHER);
        addSettings(update);
    }

    @Override
    public void onEnable() {
        updatePetPercent();
        percentStr="Pet Percent: "+(int) petPercent+"%";
        clientInstance.getTimerManager().addTimer(updateTimer);
        clientInstance.getTimerManager().addTimer(queueHandler);
    }

    @Override
    public void onDisable() {
        clientInstance.getTimerManager().removeTimer(updateTimer);
        clientInstance.getTimerManager().removeTimer(queueHandler);
    }

    @Override
    public void onTick() {
        updateTimer.setStartTime(update.getValueInt()*20);
        if(updateTimer.isTime(0)) {
            updatePetPercent();
            updateTimer.reset();
            if(chargedTime==-1) {
                if(petPercent>=15) {
                    color=Color.green;
                }else{
                    color=Color.red;
                }
                percentStr="Pet Percent: "+(int) petPercent+"%";
            }else{
                color=Color.yellow;
                percentStr="Pet Recharging";
            }
        }
    }


    @Override
    public void onRender(MatrixStack matrices, float tickDelta, CallbackInfo ci) {

        int sWidth=mc.getWindow().getScaledWidth();
        int sHeight=mc.getWindow().getScaledHeight();

        List<Mod> enabled = clientInstance.getModuleManager().getEnabledModules();
        enabled.sort(Comparator.comparingInt(m -> (int)mc.textRenderer.getWidth(((Mod)m).getDisplayName())).reversed());

        mc.textRenderer.drawWithShadow(matrices,percentStr, (sWidth/2)-(mc.textRenderer.getWidth(percentStr)/2), (sHeight-65)+(mc.textRenderer.fontHeight), color.getRGB());
    }

    private void updatePetPercent() {
        Inventory inv = MinecraftClient.getInstance().player.getInventory();
        for(int i = 0; i<inv.size(); i++) {
           if(!inv.getStack(i).isEmpty()) {
               if(inv.getStack(i).hasNbt()) {
                   if(!inv.getStack(i).getNbt().isEmpty()) {
                       if(inv.getStack(i).getNbt().getKeys().contains("custom-item-data")) {
                           if(inv.getStack(i).getNbt().getCompound("custom-item-data").getKeys().contains("pet-data-category")) {
                               if(!inv.getStack(i).getNbt().getCompound("custom-item-data").getString("pet-data-category").equals("HERO")) {
                                   try {
                                       float oldPerc=petPercent;
                                       float oldChargeTime=chargedTime;
                                       this.petPercent=
                                               getPetPercent(inv.getStack(i).getNbt().getCompound("custom-item-data"));
                                       this.chargedTime=
                                               (float) inv.getStack(i).getNbt().getCompound("custom-item-data").getLong("pet-battery-chargedtime");
                                       if((int)oldPerc!=(int)petPercent) {
                                           if(petPercent==0) {
                                               queueHandler.addQueue(new QueueTitle("§cPet Battery Died"));
                                           } else if(petPercent==15) {
                                               queueHandler.addQueue(new QueueTitle("§cLow Pet Battery"));
                                           }
                                           if(oldChargeTime!=chargedTime) {
                                               queueHandler.addQueue(new QueueTitle("§aPet Recharged"));
                                           }
                                       }
                                   }
                                   catch(NullPointerException e) {
                                       this.petPercent=-1;
                                       this.chargedTime=-1;
                                   }
                               }
                           }
                       }
                   }
               }
           }
        }
    }

    private float getPetPercent(NbtCompound customItemData) {
        long chargeLeft=customItemData.getLong("pet-battery-usedcount");
        long petBatteryDurability=customItemData.getLong("pet-battery-durability");
        return (100-((float)chargeLeft/(float)petBatteryDurability*100)+0.5F);
    }

}
