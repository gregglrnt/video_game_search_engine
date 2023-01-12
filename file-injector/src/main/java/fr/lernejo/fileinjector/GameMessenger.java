package fr.lernejo.fileinjector;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;


public class GameMessenger {

    private final RabbitTemplate rabbit;
    private final ObjectMapper mapper = new ObjectMapper();

    public GameMessenger(RabbitTemplate originalTemplate) {
        this.rabbit = originalTemplate;
    }

    public void addGames(GameEntry[] games) {
        for (GameEntry g : games) {
            this.sendGame(g);
        }
    }

    private void sendGame(GameEntry gameObj) {
        rabbit.setMessageConverter(new Jackson2JsonMessageConverter());
        MessageProperties props = new MessageProperties();
        props.setHeader("game_id", gameObj.id());
        try {
            String body = mapper.writeValueAsString(gameObj);
            Message message = new Message(body.getBytes(), props);
            rabbit.send(message);
        } catch (Exception e) {
            System.out.println("Failure to parse games :" + e.getMessage());
        }
    }

}
