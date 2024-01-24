package sejong.team.repository;

import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sejong.team.domain.Team;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team,Long> {
    Optional<Team> findById(Long teamId);

    @Query("select t from team_tb as t where t.uniqueNum=:conditions or t.name=:conditions")
    List<Team> findAllByCondition(@Param(value = "conditions") String conditions);
}
