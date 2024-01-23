package sejong.team.repository;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import sejong.team.domain.Team;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TeamRepositoryTest {

    @Autowired
    TeamRepository teamRepository;

    @DisplayName("팀 레포지토리 저장 테스트")
    @Test
    void testSaveTeam(){
        //given
        Team team = createTeam();
        Team saveTeam = teamRepository.save(team);
        //when
        //then
        assertAll(()->assertEquals(team.getUniqueNum(),saveTeam.getUniqueNum()),
                ()->assertEquals(team.getName(),saveTeam.getName()),
                ()->assertEquals(team.getEmblem(),saveTeam.getEmblem()));
    }

    private static Team createTeam() {
        return Team.builder()
                .uniqueNum("test_unique_num")
                .emblem("test_emblem")
                .name("test_name")
                .build();
    }

}