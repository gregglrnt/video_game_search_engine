package fr.lernejo.search.api;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@RestController
public class GameAtRest {
    private final RestHighLevelClient client;

    public GameAtRest(RestHighLevelClient client) {
        this.client = client;
    }

    @GetMapping("api/games")
    public List<Object> getGameWithLucene(@RequestParam(name = "query") String query) throws IOException {
        List<Object> results = new ArrayList<>();
        SearchRequest req = new SearchRequest();
        SearchSourceBuilder source = new SearchSourceBuilder();
        source.query(new QueryStringQueryBuilder(query));
        req.source(source);
        for (SearchHit hit :  this.client.search(req, RequestOptions.DEFAULT).getHits()) {
            results.add(hit.getSourceAsMap());
        }
        return results;
        }
    }

