package me.bubbles.bubblemod.commands.AutoChat;

import me.bubbles.bubblemod.Client;
import me.bubbles.bubblemod.commands.Command;
import net.minecraft.text.Text;

public class AutoChat extends Command {

    private String usage = "§8[§b§lBubbleMod§8] §fUsage: §c.autochat [seconds] [text] §for §c.autochat toggle §fto toggle §for §c.auto reset";
    private String message;
    private int delay=-1;
    private ChatTimer timer;
    private boolean enabled=false;

    public AutoChat() {
        super("autochat","Set a chat to type at a set interval",true);
        addAliases("ac");
    }

    @Override
    public void run(String message) {

        String[] args = message.split(" ");

        if(!(args.length >=2)) {
            syntaxError();
            return;
        }

        if(args[1].equals("toggle")) {
            if(delay==-1) {
                client.getMinecraftClient().inGameHud.getChatHud().addMessage(Text.of("§8[§b§lBubbleMod§8] §cMust set a chat first!"));
                return;
            }
            enabled=!enabled;
            onToggle();
            client.getMinecraftClient().inGameHud.getChatHud().addMessage(Text.of("§8[§b§lBubbleMod§8] §fAuto Chat Toggle: §c"+enabled));
            return;
        }else if (args[1].equals("reset")){
            if(delay==-1) {
                client.getMinecraftClient().inGameHud.getChatHud().addMessage(Text.of("§8[§b§lBubbleMod§8] §cMust set a chat first!"));
                return;
            }
            enabled=!enabled;
            onToggle();
            client.getMinecraftClient().inGameHud.getChatHud().addMessage(Text.of("§8[§b§lBubbleMod§8] §fAutoChat Timer Reset"));
            return;
        }

        if(!(args.length >=3)) {
            syntaxError();
            return;
        }

        int seconds;
        try {
            seconds=Integer.parseInt(args[1]);
        }catch(NullPointerException e) {
            syntaxError();
            return;
        }
        delay=seconds*20;
        StringBuilder text=new StringBuilder();
        for(int i=2;i<args.length;i++) {
            if(i!=2)
                text.append(" ");
            text.append(args[i]);
        }

        this.message=text.toString();

        if(timer!=null) {
            Client.getInstance().getTimerManager().removeTimer(timer);
        }

        timer=new ChatTimer(this.message);
        timer.setStartTime(delay);

        enabled=true;
        onToggle();

        client.getMinecraftClient().inGameHud.getChatHud().addMessage(Text.of("§8[§b§lBubbleMod§8] §fAutoChat set to §c\""+this.message+"\" §fevery §c"+delay/20+"§f second(s)."));

    }

    private void onToggle() {
        if(enabled) {
            timer.setStartTime(delay);
            timer.timeOver();
            Client.getInstance().getTimerManager().addTimer(timer);
        } else {
            timer.setTime(delay);
            Client.getInstance().getTimerManager().removeTimer(timer);
        }
    }

    private void syntaxError() {
        client.getMinecraftClient().inGameHud.getChatHud().addMessage(Text.of(usage));
    }

}
