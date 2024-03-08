package sejong.team.service;

import com.google.api.gax.rpc.NotFoundException;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sejong.team.common.client.dto.ScheduleInfoDto;
import sejong.team.domain.Schedule;
import sejong.team.domain.Team;
import sejong.team.fcm.FCMService;
import sejong.team.fcm.ScheduleNotificationDto;
import sejong.team.global.MessageUtils;
import sejong.team.repository.ScheduleRepository;
import sejong.team.repository.TeamRepository;
import sejong.team.service.req.CreateScheduleRequestDto;
import sejong.team.service.res.ViewScheduleResponseDto;

import java.util.ArrayList;
import java.util.List;

import static sejong.team.domain.QSchedule.schedule;
import static sejong.team.domain.QSquad.squad;
import static sejong.team.domain.QUserSquad.userSquad;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final TeamRepository teamRepository;
    private final FCMService fcmService;
    private final JPAQueryFactory queryFactory;

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

    public List<ScheduleInfoDto> getScheduleInfo(Long userId) {
        List<Schedule> results = queryFactory
                .select(schedule)
                .from(userSquad)
                .join(squad)
                .on(userSquad.squadId.eq(squad.id))
                .join(schedule)
                .on(squad.scheduleId.eq(schedule.id))
                .where(userSquad.userId.eq(userId))
                .fetch();

        List<ScheduleInfoDto> scheduleInfoDto = new ArrayList<>();
        for(Schedule result : results) {
            Team homeTeam = teamRepository.findById(result.getHomeTeamId())
                    .orElseThrow(() -> new EntityNotFoundException(MessageUtils.TEAM_NOT_FOUND));
            Team awayTeam = teamRepository.findById(result.getAwayTeamId())
                    .orElseThrow(() -> new EntityNotFoundException(MessageUtils.TEAM_NOT_FOUND));

            ScheduleInfoDto scheduleInfoBuild = ScheduleInfoDto.builder()
                    .place(result.getPlace())
                    .startTime(result.getStartTime())
                    .homeTeam(
                            ScheduleInfoDto.HomeTeam.builder()
                                    .name(homeTeam.getName())
                                    .emblem(homeTeam.getEmblem())
                                    .build()
                    )
                    .awayTeam(
                            ScheduleInfoDto.AwayTeam.builder()
                                    .name(awayTeam.getName())
                                    .emblem(awayTeam.getEmblem())
                                    .build()
                    )
                    .build();

            scheduleInfoDto.add(scheduleInfoBuild);
        }

        return scheduleInfoDto;
    }
}
