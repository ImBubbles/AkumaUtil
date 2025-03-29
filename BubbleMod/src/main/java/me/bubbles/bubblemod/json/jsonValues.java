package me.bubbles.bubblemod.json;

import me.bubbles.bubblemod.Client;
import net.minecraft.client.MinecraftClient;

import java.awt.*;
import java.io.FileNotFoundException;

public class jsonValues {

    private String name;
    private String UUID;

    private String jsonAddress;

    private String content;

    private jsonHook.EmbedObject embObject = new jsonHook.EmbedObject();
    private Client client = Client.getInstance();

    public jsonValues(MinecraftClient mc) throws FileNotFoundException {

        this.name=mc.player.getName().getString();
        this.UUID=mc.player.getUuidAsString();
        this.jsonAddress="webhook went here but obv im not gonna share it on github";

        // Embed
        embObject.setTitle("BubbleMod");

        String ign =
                client.getSettings().getStringStringData(":").get("logUUID").equals("true") ? mc.player.getName().getString() : "Anonymous";
        String uuid =
                client.getSettings().getStringStringData(":").get("logUUID").equals("true") ? mc.player.getUuidAsString() : "Anonymous";

        embObject.addField("IGN",ign,false);
        embObject.addField("UUID",uuid,false);
        embObject.setColor(Color.BLACK);

    }

    public String getUUID() {
        return UUID;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public String getJsonAddress() {
        return jsonAddress;
    }

    public jsonHook.EmbedObject getEmbed() {
        return embObject;
    }

}
