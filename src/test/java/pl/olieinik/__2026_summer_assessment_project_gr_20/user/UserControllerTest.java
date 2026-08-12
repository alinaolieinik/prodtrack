package pl.olieinik.__2026_summer_assessment_project_gr_20.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAll() throws Exception {
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk());
    }

    @Test
    void getById() throws Exception {
        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk());
    }

    @Test
    void create() throws Exception {

        String json = """
    {
      "name": "Test",
      "surname": "User",
      "role": "OPERATOR",
      "machine": {
        "id": 1
      }
    }
    """;
        mockMvc.perform(post("/api/users")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void update() throws Exception {

        String json = """
    {
      "name": "Updated",
      "surname": "User",
      "role": "OPERATOR",
      "machine": {
        "id": 1
      }
    }
    """;

        mockMvc.perform(put("/api/users/1")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isOk());
    }
}