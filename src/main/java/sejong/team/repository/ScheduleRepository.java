package sejong.team.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sejong.team.domain.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule,Long> {
    @Modifying
    @Query("UPDATE schedule_tb s SET s.accept = TRUE WHERE s.id = :scheduleId")
    void updateAccept(@Param("scheduleId") Long scheduleId);

    @Modifying
    @Query("DELETE FROM schedule_tb s WHERE s.id = :scheduleId")
    void deleteById(@Param("scheduleId") Long scheduleId);
}
