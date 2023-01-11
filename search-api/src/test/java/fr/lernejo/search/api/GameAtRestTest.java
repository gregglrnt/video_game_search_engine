package fr.lernejo.search.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;


import java.io.ByteArrayOutputStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class GameAtRestTest {

    @Autowired
    private MockMvc mockMvc;

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();


    private String testQuery = "developer:\"Epic Games\"";

    @Test
    public void will_return_a_result_on_query() throws Exception {
        this.mockMvc.perform(get("/api/games?query=" + testQuery)).andExpect(status().isOk());
    }

    @Test
    public void will_return_doc_if_no_query() throws Exception {
        this.mockMvc.perform(get("/api/games?query=")).andExpect(status().isOk()).andExpect(jsonPath("$.length()").isNumber());
    }
}
