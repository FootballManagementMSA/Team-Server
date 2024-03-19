package sejong.team.service;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sejong.team.domain.Schedule;
import sejong.team.domain.Team;
import sejong.team.fcm.FCMService;
import sejong.team.fcm.ScheduleNotificationDto;
import sejong.team.repository.ScheduleRepository;
import sejong.team.repository.TeamRepository;
import sejong.team.service.req.CreateScheduleRequestDto;
import sejong.team.service.res.ViewScheduleResponseDto;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ScheduleServiceTest {

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private FCMService fcmService;

    @InjectMocks
    private ScheduleService scheduleService;

    @Test
    @DisplayName("일정 생성 테스트")
    public void testCreateSchedule() {
        Long homeTeamId = 1L;
        CreateScheduleRequestDto createScheduleRequestDto = CreateScheduleRequestDto.builder()
                .title("축구 경기")
                .memo("15:00에 시작하는 친선 경기")
                .startTime(LocalDateTime.of(2023, 9, 10, 15, 0))
                .endTime(LocalDateTime.of(2023, 9, 10, 17, 0))
                .place("서울시 광진구 군자동 28-2")
                .awayTeamId(2L)
                .longitude(127.1234)
                .latitude(37.5678)
                .build();

        Team mockHomeTeam = new Team();
        Team mockAwayTeam = new Team();

        when(teamRepository.findById(homeTeamId)).thenReturn(Optional.of(mockHomeTeam));
        when(teamRepository.findById(createScheduleRequestDto.getAwayTeamId())).thenReturn(Optional.of(mockAwayTeam));

        scheduleService.createSchedule(homeTeamId, createScheduleRequestDto);

        verify(scheduleRepository).save(any(Schedule.class));
    }

    @Test
    @DisplayName("일정 상세 뷰 테스트")
    public void testViewSchedule() {
        Long scheduleId = 1L;
        Schedule mockSchedule = new Schedule();
        Team mockHomeTeam = new Team();
        Team mockAwayTeam = new Team();

        when(scheduleRepository.findById(scheduleId)).thenReturn(Optional.of(mockSchedule));
        when(teamRepository.findById(mockSchedule.getHomeTeamId())).thenReturn(Optional.of(mockHomeTeam));
        when(teamRepository.findById(mockSchedule.getAwayTeamId())).thenReturn(Optional.of(mockAwayTeam));

        ViewScheduleResponseDto response = scheduleService.viewSchedule(scheduleId);

        assertNotNull(response);
    }

    @Test
    @DisplayName("푸시알림 수락 테스트")
    public void testAcceptSchedule() {
        Long scheduleId = 1L;

        scheduleService.acceptSchedule(scheduleId);

        verify(scheduleRepository).updateAccept(scheduleId);
    }

    @Test
    @DisplayName("푸시알림 거절 테스트")
    public void testDeleteSchedule() {
        Long scheduleId = 1L;

        scheduleService.deleteSchedule(scheduleId);

        verify(scheduleRepository).deleteById(scheduleId);
    }
}