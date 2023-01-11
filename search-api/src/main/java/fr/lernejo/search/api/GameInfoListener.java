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


@Component
public class GameInfoListener {
    private final RestHighLevelClient restHighLevelClient;

    public GameInfoListener(RestHighLevelClient rhlc) {
        this.restHighLevelClient = rhlc;
    }

    @RabbitListener(queues = "game_info")
    public void onMessage(Message message) throws IOException {
        if(message.getMessageProperties() == null) return;
        String id = message.getMessageProperties().getHeaders().get("game_id").toString();
        IndexRequest index = new IndexRequest("games");
        index.id(id);
        index.source("{\"game_id\":\"" + id + "\"}", XContentType.JSON);
        index.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
        this.restHighLevelClient.index(index, RequestOptions.DEFAULT);
    }
}
