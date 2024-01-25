package sejong.team.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "schedule_tb")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Schedule {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "home_team_id")
    private Long homeTeamId;
    @Column(name = "away_team_id")
    private Long awayTeamId;
    private String place;
    private Double longitude;
    private Double latitude;
    @Column(name = "start_time")
    private LocalDateTime startTime;
    @Column(name = "end_time")
    private LocalDateTime endTime;
    private String memo;
    private String title;
    private Boolean accept;

}
