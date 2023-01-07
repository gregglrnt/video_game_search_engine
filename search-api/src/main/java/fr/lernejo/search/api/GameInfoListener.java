package fr.lernejo.search.api;

import com.rabbitmq.client.AMQP;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.xcontent.XContentType;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static fr.lernejo.search.api.AmqpConfiguration.GAME_INFO_QUEUE;

@Component
public class GameInfoListener {
    private final RestHighLevelClient restHighLevelClient;

    public GameInfoListener(RestHighLevelClient rhlc) {
        this.restHighLevelClient = rhlc;
    }

    @RabbitListener(queues = GAME_INFO_QUEUE)
    public void onMessage(Message message) {
        if(message.getMessageProperties() == null) return;
        System.out.println(message);
        String id = message.getMessageProperties().getHeaders().get("game_id").toString();
        IndexRequest index = new IndexRequest("games");
        index.id(id);
        index.source("{\"game_id\":\"" + id + "\"}", XContentType.JSON);
        index.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
        this.restHighLevelClient.indexAsync(index, RequestOptions.DEFAULT, new ActionListener<>() {
            @Override
            public void onResponse(IndexResponse response) {
                System.out.println("Successfully indexed game with id: " + id);
            }

            @Override
            public void onFailure(Exception e) {
                System.out.println("Error occurred while indexing game with id: " + id);
                e.printStackTrace();
            }
        });
    }
}
