package sejong.team.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sejong.team.domain.Team;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
public interface TeamRepository extends JpaRepository<Team,Long> {
    Optional<Team> findById(Long teamId);

    @Query("select t from Team as t where t.unique_num=:conditions or t.name=:conditions")
    List<Team> findAllByCondition(@Param(value = "conditions") String conditions);
}
