package me.bubbles.bubblemod.client;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Client implements ModInitializer {

    public Logger logger = LoggerFactory.getLogger(Client.class);

    @Override
    public void onInitialize() {
        logger.info("Hello world");
    }

}
