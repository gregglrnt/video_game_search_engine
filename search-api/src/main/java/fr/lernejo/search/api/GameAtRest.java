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

import java.util.ArrayList;
import java.util.List;


@RestController
public class GameAtRest {
    private RestHighLevelClient client;

    public GameAtRest(RestHighLevelClient client) {
        this.client = client;
    }

    @GetMapping("api/games")
    public List<Object> getGameWithLucene(@RequestParam(value = "query", required = false) String query) {
        List<Object> results = new ArrayList<>();
        if (query == null || query == "") {
            results.add("No results. Please follow Lucene syntax");
            return results;
        }
            SearchRequest req = new SearchRequest();
            SearchSourceBuilder source = new SearchSourceBuilder();
            source.query(new QueryStringQueryBuilder(query));
            req.source(source);
            this.client.searchAsync(
                req, RequestOptions.DEFAULT, new ActionListener<>() {
                    @Override
                    public void onResponse(SearchResponse res) {
                        for(SearchHit hit : res.getHits()) {
                            results.add(hit.getSourceAsMap());
                        }
                    }

                    @Override
                    public void onFailure(Exception e) {
                        results.add("No results. Please try again");
                    }
                }
            );
            return results;
        }

    }

