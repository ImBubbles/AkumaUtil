package me.bubbles.bubblemod.module.categories.chat;

import me.bubbles.bubblemod.module.Mod;
import me.bubbles.bubblemod.module.settings.ModeSetting;
import me.bubbles.bubblemod.module.settings.NumberSetting;
import me.bubbles.bubblemod.timers.queue.QueueHandler;
import me.bubbles.bubblemod.timers.queue.QueueTitle;
import net.minecraft.client.gui.hud.MessageIndicator;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class ReactionTimer extends Mod {

    private final int seconds = 20;
    private int time = 200*seconds;
    public static int timer=0;
    private int reacTimer=0;
    private int[] lastTwo = {0,0};
    private boolean first;
    private int timeOut=0;
    private ModeSetting mode = new ModeSetting("Mode","Adapt","Set","Adapt","Avg");
    private NumberSetting timeSetting = new NumberSetting("Time",185,215,200,5);
    private QueueHandler queueHandler;

    public ReactionTimer() {

        super("ReacTimer","Reaction Timer", Category.CHAT);

        addSettings(mode,timeSetting);

    }

    @Override
    public void onEnable() {
        this.queueHandler=new QueueHandler(20);
        clientInstance.getTimerManager().addTimer(queueHandler);
        timer=time;
        first=true;
    }

    @Override
    public void onDisable() {
        timer=time;
        clientInstance.getTimerManager().removeTimer(queueHandler);
    }

    @Override
    public void onTick() {

        if(mode.isMode("Set"))
            time = seconds*timeSetting.getValueInt();

        timer=clamp(timer-1,0,time);

        int[] list = {60*seconds,30*seconds,15*seconds,10*seconds,5*seconds,4*seconds,3*seconds,2*seconds,seconds};
        int[] titleList = {10*seconds,5*seconds,4*seconds,3*seconds,2*seconds,seconds};

        for(int i : list) {
            if(timer==i) {
                if(mode.isMode("Set")||
                        (mode.isMode("Avg")&&isTwoReady())||
                        (mode.isMode("Adapt")&&isOneReady())) {

                    mc.inGameHud.getChatHud().addMessage(Text.of("§8[§b§lBubbleMod§8] §fChat Reaction In Approx: §c"+i/seconds+"s"));

                }
            }
        }

        for(int b : titleList) {
            if(timer==b) {
                if(mode.isMode("Set")||
                        (mode.isMode("Avg")&&isTwoReady())||
                        (mode.isMode("Adapt")&&isOneReady())) {

                    queueHandler.addQueue(new QueueTitle("§fChat Reaction: "+b/20+"s"));

                }
            }
        }

        reacTimer++;
        timeOut = clamp(timeOut-1,0,100);

    }

    @Override
    public void onChat(Text message, MessageSignatureData signature, MessageIndicator indicator, CallbackInfo ci) {
        /*if(message.getString().contains("REACTION")) {
            mc.inGameHud.getChatHud().addMessage(Text.of("Message: "));
            String str = message.getString().replace("REACTION","RC");
            mc.inGameHud.getChatHud().addMessage(Text.of(str));
        }*/
        if(!(message.getString().contains("REACTION ")&&message.getString().contains("»"))) {
            return;
        }
        if(message.getString().contains("Type ")||message.getString().contains("Unscramble")||message.getString().contains("Solve ")||message.getString().contains("No scramble")||message.getString().contains("The word")||message.getString().contains("First person to")||message.getString().contains("The question is")||message.getString().contains("is scrambled. First to unscramble")||message.getString().contains("Whoever types")||message.getString().contains("Whoever unscrambles")||message.getString().contains("First to type")||message.getString().contains("What is")||message.getString().contains("Calculate it!")) {
            if(timeOut==0) {
                boolean isEq = message.getString().contains("Solve ")||message.getString().contains("The question is")||message.getString().contains("Calculate it!");
                if(!first) {
                    onChatReaction();
                    timer=time;
                }else{
                    mc.inGameHud.getChatHud().addMessage(Text.of("§8[§b§lBubbleMod§8] §fTimer will start working after the next chat reaction."));
                    first=false;
                }
                /*if(isEq) { // Is equation
                    String eq = message.getString().split(":")[1].replaceAll("\n","").replaceAll(" ","").replaceAll("-","");
                    int num1;
                    int num2;
                    int result;
                    if(eq.contains("*")) {
                        num1=Integer.parseInt(eq.split("\\*")[0]);
                        num2=Integer.parseInt(eq.split("\\*")[1]);
                        result=num1*num2;
                    }else if(eq.contains("+")) {
                        num1=Integer.parseInt(eq.split("\\+")[0]);
                        num2=Integer.parseInt(eq.split("\\+")[1]);
                        result=num1+num2;
                    }else{
                        result=0;
                    }
                    mc.inGameHud.getChatHud().addMessage(Text.of("§8[§b§lBubbleMod§8] §fAnswer is: "+result));
                }*/
                reacTimer=0;
                timeOut=5;
            }
        }
    }

    private int clamp(int now, int min, int max) {
        if(now<min) {
            return min;
        }else if(now>max) {
            return max;
        }else{
            return now;
        }
    }

    private void onChatReaction() {

        overOne(reacTimer);

        switch (mode.getMode()) {
            case "Adapt" -> adapt();
            case "Avg" -> average();
        }

    }

    private void adapt() {
        if(isOneReady()) {
            time=lastTwo[0];
            mc.inGameHud.getChatHud().addMessage(Text.of("§8[§b§lBubbleMod§8] §fChat reaction timer reset. Time: §c"+time/20+"s"));
        }else{
            mc.inGameHud.getChatHud().addMessage(Text.of("§8[§b§lBubbleMod§8] §fAdaptive chat reaction is now ready. Last: §c"+time/20+"s"));
        }
    }

    private void average() {
        if(isTwoReady()) {
            time=(lastTwo[0]+lastTwo[1])/2;
            mc.inGameHud.getChatHud().addMessage(Text.of("§8[§b§lBubbleMod§8] §fChat reaction timer reset. Time: §c"+time/20+"s"));
        } else if (isOneReady() ){
            mc.inGameHud.getChatHud().addMessage(Text.of("§8[§b§lBubbleMod§8] §fAverage chat reaction is now ready. Average: §c+"+time/20+"s"));
        } else{
            mc.inGameHud.getChatHud().addMessage(Text.of("§8[§b§lBubbleMod§8] §fAverage chat reaction will be ready in one more chat reaction."));
        }
    }

    private void overOne(int put) {
        lastTwo[1]=lastTwo[0];
        lastTwo[0]=put;
    }

    private boolean isOneReady() {
        return !(lastTwo[0]==0);
    }
    private boolean isTwoReady() {
        return !(lastTwo[0]==0&&lastTwo[1]==0);
    }

}
