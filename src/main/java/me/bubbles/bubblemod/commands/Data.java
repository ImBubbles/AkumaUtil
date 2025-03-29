package me.bubbles.bubblemod.commands;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;

import java.util.HashMap;
import java.util.Set;

public class Data extends Command {

    private ItemStack itemStack;

    public Data() {
        super("data","Get the nbt data of current item in hand (useful for Bubbles)",true);
    }

    @Override
    public void run(String message) {
        MinecraftClient mc = client.getMinecraftClient();
        ClientPlayerEntity cpe = mc.player;
        assert cpe != null;
        if(!cpe.getMainHandStack().isEmpty()) {
            this.itemStack=cpe.getMainHandStack();
            String str = cpe.getMainHandStack().getNbt().asString();
            if(str!=null) {
                if(itemStack.getNbt().getKeys().contains("custom-item-data")) {
                    HashMap<String,String> set = getNBTData(itemStack.getNbt().getCompound("custom-item-data"));
                    StringBuilder stringBuilder = new StringBuilder();
                    for(String key : set.keySet()) {
                        stringBuilder.append(key).append(" : ").append(set.get(key)).append("\n");
                    }
                    mc.inGameHud.getChatHud().addMessage(Text.of(stringBuilder.toString()));
                } else {
                    mc.inGameHud.getChatHud().addMessage(Text.of(str));
                }
            }else{
                mc.inGameHud.getChatHud().addMessage(Text.of("§aNothing!"));
            }
        }else{
            mc.inGameHud.getChatHud().addMessage(Text.of("§aNothing!"));
        }
    }

    private HashMap<String,String> getNBTData(NbtCompound compound) {
        NbtCompound nbtCompound = compound;
        HashMap<String,String> result = new HashMap<>();
        for(String str : nbtCompound.getKeys()) {
            result.put(str,nbtCompound.get(str).asString());
        }
        return result;
    }

}
