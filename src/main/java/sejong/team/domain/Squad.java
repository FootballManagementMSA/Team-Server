package sejong.team.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "squad_tb")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Squad {
    @Id
    @GeneratedValue
    Long id;
    @Column(name = "schedule_id")
    private Long scheduleId;
    @Column(name = "team_id")
    private Long teamId;
    @Column(name = "user_squad_id")
    private Long userSquadId;
}
