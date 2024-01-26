package sejong.team.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sejong.team.domain.Squad;

public interface SquadRepository extends JpaRepository<Squad,Long> {
}
