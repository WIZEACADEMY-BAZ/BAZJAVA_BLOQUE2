package com.wizeline.baz.LearningSpring.patron.comportamiento;

import java.util.logging.Logger;

public class ReadMessage implements Observer{

    private static final Logger LOGGER = Logger.getLogger(ReadMessage.class.getName());

    @Override
    public void update(Chat chat) {
        if(chat.message.equals("LEIDO")){
            LOGGER.info("Observer 2 : ha leído el mensaje");
        }else {
            LOGGER.info("Observer 2 : message esta disponible para leer ");
        }
    }
}
