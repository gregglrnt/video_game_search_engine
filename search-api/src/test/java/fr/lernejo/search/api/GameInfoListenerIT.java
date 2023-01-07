package fr.lernejo.search.api;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class GameInfoListenerIT {

    @Mock
    private RestHighLevelClient client = new ElasticSearchConfiguration().restHighLevelClient("localhost", 9200, "elastic", "admin");

    @Mock
    private Message message;

    @Captor
    private ArgumentCaptor<IndexRequest> indexCaptor = ArgumentCaptor.forClass(IndexRequest.class);

   private GameInfoListener listener = new GameInfoListener(client);

    @Test
    void wasCreated() throws Exception {
        if(!(listener instanceof GameInfoListener)) {
            throw new Exception("Class not created correctly");
        }
    }

    @Test
    void correctlyIndex() throws Exception {
        String id = "123";
        MessageProperties props = new MessageProperties();
        props.setHeader("content_type", "application/json");
        props.setHeader("game_id", id);
        this.message = new Message("body".getBytes(), props);
        try {
            listener.onMessage(this.message);
        } catch (Exception e) {
            throw new Exception("Error in indexation :" + e.getMessage());
        }
    }

    @Test
    void wrongIndex() throws Exception {
        String id = "456";
        MessageProperties props = new MessageProperties();
        props.setHeader("game_not_id", id);
        this.message = new Message("error".getBytes(), props);
        try {
            listener.onMessage(this.message);
            throw new Exception("No error in indexation !");
        } catch (Exception e) {
            System.out.println("Wrong header threw error " + e.getMessage());
        }
    }


}
