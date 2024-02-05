package sejong.team.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sejong.team.domain.Squad;

import java.util.Optional;

public interface SquadRepository extends JpaRepository<Squad,Long> {
    @Query("SELECT s FROM squad_tb s WHERE s.scheduleId = :scheduleId AND s.teamId = :teamId")
    Optional<Squad> findSquadByScheduleIdAndTeamId(@Param("scheduleId") Long scheduleId,
                                                   @Param("teamId") Long teamId);
}
