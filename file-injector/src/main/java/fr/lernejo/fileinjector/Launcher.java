package fr.lernejo.fileinjector;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import java.io.File;
import java.io.InputStream;

@SpringBootApplication
public class Launcher {


    public static void main(String[] args) {
        try (AbstractApplicationContext springContext = new AnnotationConfigApplicationContext(Launcher.class)) {
            if(args.length > 0) {
                InputStream is = Launcher.class.getResourceAsStream(args[0]);
                ObjectMapper mapper = new ObjectMapper();
                GameEntry[] games = mapper.readValue(is.readAllBytes(), GameEntry[].class);
                RabbitTemplate template = springContext.getBean(RabbitTemplate.class);
                new GameMessenger(template).addGames(games);
            }
            System.out.println("Hello after starting Spring");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
