package sejong.team.domain;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import sejong.team.common.client.dto.ScheduleInfoDto;
import sejong.team.service.ScheduleService;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static sejong.team.domain.QSchedule.schedule;
import static sejong.team.domain.QSquad.squad;
import static sejong.team.domain.QUserSquad.userSquad;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ScheduleTest {
    @Autowired
    JPAQueryFactory queryFactory;

    @Autowired
    EntityManager em;

    @Autowired
    ScheduleService scheduleService;

    @Test
    @Transactional
    void joinTest() {
        // 임의의 Schedule과 Squad 엔티티 생성 및 영속화
        Schedule newSchedule = Schedule.builder()
                .homeTeamId(1L)
                .awayTeamId(2L)
                .place("운동장")
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusHours(2))
                .memo("테스트 메모")
                .title("테스트 타이틀")
                .accept(true)
                .build();

        em.persist(newSchedule);
        em.flush();

        Squad newSquad = Squad.builder()
                .scheduleId(newSchedule.getId())
                .teamId(1L)
                .build();

        em.persist(newSquad);
        em.flush();

        // QueryDSL로 Left Join 쿼리 실행
        List<Tuple> result = queryFactory
                .select(schedule, squad)
                .from(schedule)
                .leftJoin(squad)
                .on(schedule.id.eq(squad.scheduleId))
                .fetch();

        // 결과 출력 또는 검증
        result.forEach(tuple -> {
            Schedule fetchedSchedule = tuple.get(schedule);
            Squad fetchedSquad = tuple.get(squad);
            System.out.println("Schedule: " + fetchedSchedule);
            System.out.println("Squad: " + fetchedSquad);
        });
    }

    @Test
    @Transactional
    void findScheduleByUserId() {
        // 테스트 데이터 생성
        for (int i = 1; i <= 5; i++) {
            Schedule newSchedule = new Schedule(null, (long) i, (long) (i + 1),
                    "장소 " + i, null, null,
                    LocalDateTime.now().plusDays(i),
                    LocalDateTime.now().plusDays(i).plusHours(2),
                    "메모 " + i, "타이틀 " + i, true);
            em.persist(newSchedule);

            // 테스트를 위해 userId가 1인 UserSquad 두 개 생성
            if (i == 1 || i == 3) {
                Squad newSquad = new Squad(null, newSchedule.getId(), (long) i);
                em.persist(newSquad);

                UserSquad newUserSquad = new UserSquad(null, 1L, newSquad.getId(), null, null);
                em.persist(newUserSquad);
            }
        }

        em.flush();
        em.clear();

        Long userId = 1L; // 가정된 사용자 ID

        // QueryDSL로 사용자 ID에 해당하는 Schedule 조회
        List<Schedule> schedules = queryFactory
                .select(schedule)
                .from(userSquad)
                .join(squad)
                .on(userSquad.squadId.eq(squad.id))
                .join(schedule)
                .on(squad.scheduleId.eq(schedule.id))
                .where(userSquad.userId.eq(userId))
                .fetch();

        // 결과 출력 또는 검증
        assertEquals(2, schedules.size()); // userId에 해당하는 Schedule이 2개인지 확인
        schedules.forEach(System.out::println);
    }
}