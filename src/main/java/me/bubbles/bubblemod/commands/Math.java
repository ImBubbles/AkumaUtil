package me.bubbles.bubblemod.commands;

import me.bubbles.bubblemod.Client;
import net.minecraft.text.Text;

public class Math extends Command {

    public Math() {
        super("math","Use whole numbers to add, subtract, multiply, divide",true);
        addAliases("m");
    }

    @Override
    public void run(String message) {

        String usage = "§8[§b§lBubbleMod§8] §fUsage: §c.math (add/subtract/multiply/divide) [num1] [num2]";

        String[] args = message.replace(".","").split(" ");

        if(args.length!=4) {
            client.getMinecraftClient().inGameHud.getChatHud().addMessage(Text.of(usage));
            return;
        }

        int num1 = Integer.parseInt(args[2]);
        int num2 = Integer.parseInt(args[3]);

        int result;

        switch(args[1]) {
            case "add":
                result=num1+num2;
                break;
            case "subtract":
                result=num1-num2;
                break;
            case "multiply":
                result=num1*num2;
                break;
            case "divide":
                result=num1/num2;
                break;
            default:
                client.getMinecraftClient().inGameHud.getChatHud().addMessage(Text.of(usage));
                return;
        }

        client.getMinecraftClient().inGameHud.getChatHud().addMessage(Text.of("§8[§b§lBubbleMod§8] §f"+result));

    }

}
