package sejong.team.scheduler;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import sejong.team.domain.Schedule;
import sejong.team.domain.Squad;
import sejong.team.repository.ScheduleRepository;
import sejong.team.repository.SquadRepository;
import sejong.team.repository.UserSquadRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScheduleCleanupTask {
    private final ScheduleRepository scheduleRepository;
    private final UserSquadRepository userSquadRepository;
    private final SquadRepository squadRepository;

    // 매일 자정에 실행되도록 설정 (cron = "0 0 0 * * ?")
    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void cleanUpExpiredSchedules() {
        LocalDateTime now = LocalDateTime.now();
        List<Schedule> expiredSchedules = scheduleRepository.findAllByEndTimeBefore(now);

        expiredSchedules.forEach(schedule -> {
            // 해당 Schedule과 연결된 모든 Squad 찾기
            List<Squad> relatedSquads = squadRepository.findByScheduleId(schedule.getId());
            // Squad ID 리스트 추출 (홈팀, 어웨이팀 두 개의 Squad ID 획득.)
            List<Long> squadIds = relatedSquads.stream().map(Squad::getId).collect(Collectors.toList());
            // UserSquad 레코드 삭제 -> 벌크연산 사용
            if (!squadIds.isEmpty()) {
                userSquadRepository.deleteBySquadIdIn(squadIds);
            }
            // Squad 테이블 내 ScheduleId가 일치하는 행 삭제(두 개 삭제 -> 홈팀, 어웨이팀)
            squadRepository.deleteByScheduleId(schedule.getId());
            // Schedule 레코드 삭제
            scheduleRepository.delete(schedule);
            // 로깅
            log.info("만료된 Schedule ID {} 와 관련된 squad, user-squad 데이터 삭제 완료", schedule.getId());
        });
    }
}
