package sejong.team.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TeamController.class)
public class TeamControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateTeam() throws Exception {
        MockMultipartFile file = new MockMultipartFile("emblem", "emblem.jpg",
                "image/jpeg", "image content".getBytes());
        mockMvc.perform(multipart("/api/team-service/teams")
                        .file(file)
                        .param("name", "Test Team"))
                .andExpect(status().isOk());
    }
}
