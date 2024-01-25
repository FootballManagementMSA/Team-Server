package sejong.team.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import sejong.team.service.ScheduleService;
import sejong.team.service.req.CreateScheduleRequestDto;
import sejong.team.service.res.ViewScheduleResponseDto;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class ScheduleControllerTest {
    @Mock
    private ScheduleService scheduleService;

    @InjectMocks
    private ScheduleController scheduleController;

    private MockMvc mockMvc;
    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(scheduleController).build();
    }

    @Test
    @DisplayName("일정 생성 API 테스트")
    public void testCreateSchedule() throws Exception {
        Long teamId = 1L;
        CreateScheduleRequestDto createScheduleRequestDto = CreateScheduleRequestDto.builder()
                .title("팀 매치")
                .memo("3시에 시작합니다")
                .startTime(LocalDateTime.parse("2023-09-01T15:00:00"))
                .endTime(LocalDateTime.parse("2023-09-01T17:00:00"))
                .place("지역 축구장")
                .longitude(127.123456)
                .latitude(37.123456)
                .awayTeamId(2L)
                .fcmToken("fcm_token_example")
                .build();

        // JSON 문자열로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String jsonContent = objectMapper.writeValueAsString(createScheduleRequestDto);

        mockMvc.perform(post("/api/team-service/" + teamId + "/schedules")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk());

        verify(scheduleService).createSchedule(eq(teamId), any(CreateScheduleRequestDto.class));
    }

    @Test
    @DisplayName("일정 상세 뷰 API 테스트")
    public void testViewSchedule() throws Exception {
        Long scheduleId = 1L;
        when(scheduleService.viewSchedule(scheduleId)).thenReturn(ViewScheduleResponseDto.builder().build());

        mockMvc.perform(get("/api/team-service/schedules/" + scheduleId))
                .andExpect(status().isOk());

        verify(scheduleService).viewSchedule(scheduleId);
    }

    @Test
    @DisplayName("푸시알림 수락 API 테스트")
    public void testAcceptSchedule() throws Exception {
        Long scheduleId = 1L;

        mockMvc.perform(put("/api/team-service/schedules/" + scheduleId))
                .andExpect(status().isOk());

        verify(scheduleService).acceptSchedule(scheduleId);
    }

    @Test
    @DisplayName("푸시알림 거절 API 테스트")
    public void testRejectSchedule() throws Exception {
        Long scheduleId = 1L;

        mockMvc.perform(delete("/api/team-service/schedules/" + scheduleId))
                .andExpect(status().isOk());

        verify(scheduleService).deleteSchedule(scheduleId);
    }
}
