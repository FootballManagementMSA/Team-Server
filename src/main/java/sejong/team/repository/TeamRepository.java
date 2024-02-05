package sejong.team.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sejong.team.domain.Team;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team,Long> {
    Optional<Team> findById(Long teamId);

    @Query("select t from team_tb as t where t.uniqueNum=:search or t.name=:search")
    List<Team> findAllByCondition(@Param(value = "search") String search);
}
