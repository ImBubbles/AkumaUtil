package me.bubbles.bubblemod.module.categories.other;

import me.bubbles.bubblemod.module.Mod;
import me.bubbles.bubblemod.timers.queue.QueueHandler;
import me.bubbles.bubblemod.timers.queue.QueueTitle;
import net.minecraft.client.gui.hud.MessageIndicator;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class ImprovedAlerts extends Mod {

    private QueueHandler queueHandler;
    private int timeOut=0;

    public ImprovedAlerts() {
        super("BetterAlerts (WIP)","Displays title alerts for special messages",Category.OTHER);
    }

    @Override
    public void onEnable() {
        this.queueHandler=new QueueHandler(30);
        clientInstance.getTimerManager().addTimer(queueHandler);
    }

    @Override
    public void onDisable() {
        clientInstance.getTimerManager().removeTimer(queueHandler);
    }

    @Override
    public void onChat(Text message, MessageSignatureData signature, MessageIndicator indicator, CallbackInfo ci) {
        String msg = message.getString();

        if(!sendMessage()) {
            return;
        }

        if(msg.contains(mc.player.getName().getString())) { // Crate Rewards
            if(msg.contains("has opened a")&&msg.contains("and received [Akuma Rank] as a jackpot")||msg.contains("has won Akuma Rank")) {
                queueHandler.addQueue(new QueueTitle("§aWon Akuma"));
            } else if(msg.contains("has opened a")&&msg.contains("and received [Akuma+ Rank] as a jackpot")||msg.contains("has won Akuma+ Rank")) {
                queueHandler.addQueue(new QueueTitle("§aWon Akuma+"));
            } else if(msg.contains("has opened a")&&msg.contains("and received [Infinity Rank] as a jackpot")||msg.contains("has won Infinity Rank")) {
                queueHandler.addQueue(new QueueTitle("§aWon Infinity"));
            } else if(msg.contains("$20 STORE COUPON")||msg.contains("$20 COUPON")) {
                queueHandler.addQueue(new QueueTitle("§aWon $20 Coupon!"));
            } else if(msg.contains("$10 STORE COUPON")||msg.contains("$10 COUPON")) {
                queueHandler.addQueue(new QueueTitle("§aWon $10 Coupon!"));
            } else if(msg.contains("has voted on all links")) {
                queueHandler.addQueue(new QueueTitle("§aVoted on all links!"));
            }else if(msg.contains("vKit Shard")) { // vKits
                String vKit = "vKit";
                if(msg.contains("Christmas")) {
                    vKit="Christmas";
                } else if(msg.contains("Companion")) {
                    vKit="Companion";
                } else if(msg.contains("Halloween")) {
                    vKit="Halloween";
                } else if(msg.contains("Scavenger")) {
                    vKit="Scavenger";
                } else if(msg.contains("Valentine")) {
                    vKit="Valentine";
                } else if(msg.contains("Vote")) {
                    vKit="Vote";
                }
                queueHandler.addQueue(new QueueTitle("§aWon §f"+vKit+"§a vKit"));
            }
        }

        if (msg.contains("VOUCHERS")) { // VOUCHERS
            if(msg.contains("Rank")) { // RANK VOUCHERS
                String rank="huh";
                if(msg.contains("Donator")) {
                    rank="Donator";
                } else if(msg.contains("Lord")) {
                    rank="Lord";
                } else if(msg.contains("Titan")) {
                    rank="Titan";
                } else if(msg.contains("Legacy")) {
                    rank="Legacy";
                } else if(msg.contains("God")) {
                    rank="God";
                } else if(msg.contains("Overlord")) {
                    rank="Overlord";
                } else if(msg.contains("Akuma+")) {
                    rank="Akuma+";
                } else if(msg.contains("Akuma")) {
                    rank="Akuma";
                }  else if(msg.contains("Infinity")) {
                    rank="Infinity";
                }
                if(msg.contains("redeemed a")) {
                    queueHandler.addQueue(new QueueTitle("§aRedeemed Rank "+rank));
                } else {
                    queueHandler.addQueue(new QueueTitle("§aWon Rank "+rank));
                }
            } else if(msg.contains("CUSTOM TAG")&&msg.contains("You have received an")) {
                queueHandler.addQueue(new QueueTitle("§aWon Custom Tag"));
            } else if(msg.contains("CUSTOM TAG")&&msg.contains("You have redeemed a")) {
                queueHandler.addQueue(new QueueTitle("§aRedeemed Custom Tag"));
            }
        }else if(msg.contains("Thank you for voting for us! You've received some rewards")) {
            queueHandler.addQueue(new QueueTitle("§aVoted"));
        }

    }

    private boolean sendMessage() {
        timeOut++;
        if(timeOut%3==0) {
            timeOut=0;
            return true;
        }
        return false;
    }

}
