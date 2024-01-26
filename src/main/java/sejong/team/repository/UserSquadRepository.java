package sejong.team.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sejong.team.domain.UserSquad;

public interface UserSquadRepository extends JpaRepository<UserSquad,Long> {
}
