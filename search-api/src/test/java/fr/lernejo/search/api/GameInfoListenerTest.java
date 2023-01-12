package fr.lernejo.search.api;

import org.elasticsearch.ElasticsearchException;
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
public class GameInfoListenerTest {

    @Mock
    private RestHighLevelClient client = new ElasticSearchConfiguration().restHighLevelClient("localhost", 9200, "elastic", "admin");

    @Mock
    private Message message;

    private String test_me_body = "{\"id\":4,\"title\":\"CRSED:F.O.A.D.\",\"thumbnail\":\"https:\\/\\/www.freetogame.com\\/g\\/4\\/thumbnail.jpg\",\"short_description\":\"TakethebattleroyalegenreandaddmysticalpowersandyouhaveCRSED:F.O.A.D.(AkaCuisineRoyale:SecondEdition)\",\"game_url\":\"https:\\/\\/www.freetogame.com\\/open\\/crsed\",\"genre\":\"Shooter\",\"platform\":\"PC(Windows)\",\"publisher\":\"GaijinDistributionKFT\",\"developer\":\"DarkflowSoftware\",\"release_date\":\"2019-12-12\",\"freetogame_profile_url\":\"https:\\/\\/www.freetogame.com\\/crsed\"}";
   private GameInfoListener listener = new GameInfoListener(client);

    @Test
    void wasCreated() throws Exception {
        if(!(listener instanceof GameInfoListener)) {
            throw new Exception("Class not created correctly");
        }
    }

    @Test
    void correctlyIndex() throws Exception {
        try {
            String id = "123";
            MessageProperties props = new MessageProperties();
            props.setContentType(MessageProperties.CONTENT_TYPE_JSON);
            props.setHeader("game_id", id);
            this.message = new Message(test_me_body.getBytes(), props);
            listener.onMessage(this.message);
        } catch (ElasticsearchException e) {
            System.out.println("Bug in Elastic Search");
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
