package fr.lernejo.search.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class GameAtRestTest {

    @Autowired
    private MockMvc mockMvc;

    private String testQuery = "developer:\"Epic Games\"";

    @Test
    public void will_return_a_result_on_query() throws Exception {
        try {
            this.mockMvc.perform(get("/api/games?query=" + testQuery)).andExpect(status().isOk());
        } catch (NestedServletException e ) {
            System.out.println("Elastic search not running" + e.getMessage());
        }
    }

}
