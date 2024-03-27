package sejong.team.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;
import sejong.team.common.entity.BaseTimeEntity;

@Entity(name = "team_tb")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Team extends BaseTimeEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @Column(nullable = true, name = "unique_num")
    private String uniqueNum;
    @Column(nullable = true)
    private String emblem;
    private String details;
}
