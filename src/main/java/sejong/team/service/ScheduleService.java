package sejong.team.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sejong.team.domain.Schedule;
import sejong.team.domain.Team;
import sejong.team.fcm.FCMService;
import sejong.team.fcm.ScheduleNotificationDto;
import sejong.team.global.MessageUtils;
import sejong.team.repository.ScheduleRepository;
import sejong.team.repository.TeamRepository;
import sejong.team.service.req.CreateScheduleRequestDto;
import sejong.team.service.res.ViewScheduleResponseDto;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final TeamRepository teamRepository;
    private final FCMService fcmService;
    @Transactional
    public void createSchedule(Long homeTeamId,
                               CreateScheduleRequestDto createScheduleRequestDto){

        Schedule schedule = Schedule.builder()
                .homeTeamId(homeTeamId)
                .title(createScheduleRequestDto.getTitle())
                .memo(createScheduleRequestDto.getMemo())
                .startTime(createScheduleRequestDto.getStartTime())
                .endTime(createScheduleRequestDto.getEndTime())
                .place(createScheduleRequestDto.getPlace())
                .longitude(createScheduleRequestDto.getLongitude())
                .latitude(createScheduleRequestDto.getLatitude())
                .accept(false)
                .build();

        Team homeTeam = teamRepository.findById(homeTeamId)
                .orElseThrow(() -> new EntityNotFoundException(MessageUtils.TEAM_NOT_FOUND));

        Team awayTeam = teamRepository.findById(createScheduleRequestDto.getAwayTeamId())
                .orElseThrow(() -> new EntityNotFoundException(MessageUtils.TEAM_NOT_FOUND));

        ScheduleNotificationDto scheduleNotificationDto = ScheduleNotificationDto.builder()
                .title(createScheduleRequestDto.getTitle())
                .memo(createScheduleRequestDto.getMemo())
                .homeTeamName(homeTeam.getName())
                .homeTeamUniqueNumber(homeTeam.getUniqueNum())
                .homeTeamEmblem(homeTeam.getEmblem())
                .awayTeamName(awayTeam.getName())
                .awayTeamUniqueNumber(awayTeam.getUniqueNum())
                .awayTeamEmblem(awayTeam.getEmblem())
                .startTime(createScheduleRequestDto.getStartTime())
                .endTime(createScheduleRequestDto.getEndTime())
                .place(createScheduleRequestDto.getPlace())
                .longitude(createScheduleRequestDto.getLongitude())
                .latitude(createScheduleRequestDto.getLatitude())
                .build();

        fcmService.sendNotificationToToken(createScheduleRequestDto.getFcmToken(), scheduleNotificationDto);
        scheduleRepository.save(schedule);
    }
    public ViewScheduleResponseDto viewSchedule(Long scheduleId){
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new EntityNotFoundException(MessageUtils.SCHEDULE_NOT_FOUND));

        Team homeTeam = teamRepository.findById(schedule.getHomeTeamId())
                .orElseThrow(() -> new EntityNotFoundException(MessageUtils.TEAM_NOT_FOUND));

        Team awayTeam = teamRepository.findById(schedule.getAwayTeamId())
                .orElseThrow(() -> new EntityNotFoundException(MessageUtils.TEAM_NOT_FOUND));

        ViewScheduleResponseDto viewScheduleResponseDto = ViewScheduleResponseDto.builder()
                .title(schedule.getTitle())
                .memo(schedule.getMemo())
                .homeTeamName(homeTeam.getName())
                .homeTeamUniqueNumber(homeTeam.getUniqueNum())
                .homeTeamEmblem(homeTeam.getEmblem())
                .awayTeamName(awayTeam.getName())
                .awayTeamUniqueNumber(awayTeam.getUniqueNum())
                .awayTeamEmblem(awayTeam.getEmblem())
                .startTime(schedule.getStartTime())
                .endTime(schedule.getEndTime())
                .place(schedule.getPlace())
                .longitude(schedule.getLongitude())
                .latitude(schedule.getLatitude())
                .build();

        return viewScheduleResponseDto;
    }
    @Transactional
    public void acceptSchedule(Long scheduleId){
        scheduleRepository.updateAccept(scheduleId);
    }

    @Transactional
    public void deleteSchedule(Long scheduleId){
        scheduleRepository.deleteById(scheduleId);
    }
}
