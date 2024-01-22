package sejong.team.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sejong.team.domain.Team;

public interface TeamRepository extends JpaRepository<Team,Long> {
}
