package com.wizeline.baz.LearningSpring.patron.comportamiento;

import java.util.logging.Logger;

public class SendMessage implements Observer{

    private static final Logger LOGGER = Logger.getLogger(SendMessage.class.getName());

    @Override
    public void update(Chat chat) {
        if(chat.message.equals("LEIDO")){
            LOGGER.info("Observer 1 :  Se ha leido tu mensaje");
        }else {
            LOGGER.info("Observer 1 : ha enviado un mensaje");
        }
    }
}
