package sejong.team.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sejong.team.common.entity.BaseTimeEntity;

import java.util.Random;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity(name = "team_tb")
public class Team extends BaseTimeEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @Column(nullable = true)
    private String unique_num;
    @Column(nullable = true)
    private String emblem;
}
