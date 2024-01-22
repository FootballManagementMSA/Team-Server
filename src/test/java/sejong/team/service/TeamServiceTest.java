package sejong.team.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sejong.team.domain.Team;
import sejong.team.repository.TeamRepository;
import sejong.team.service.req.SearchTeamInfoRequestDto;
import sejong.team.service.res.SearchTeamInfoResponseDto;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TeamServiceTest {

    @Autowired
    TeamService teamService;
    @Autowired
    TeamRepository teamRepository;

    @Test
    void 특정_팀의_대표정보_조회(){
        //given
        Team saveTeam = teamRepository.save(createTeam());

        //when

        //then
    }

/*    @Test
    void 팀검색_기능테스트_유니크키() {
        //given
        Team saveTeam = teamRepository.save(createTeam());

        //when
        //then
        assertAll(() -> teamService
                .searchTeamInfoByNameOrCode(SearchTeamInfoRequestDto.of("test_unique_num")).);
    }

    @Test
    void 팀검색_기능테스트_이름() {
        //given
        Team saveTeam = teamRepository.save(createTeam());

        //when

        //then
    }*/

    private static Team createTeam() {
        return Team.builder()
                .unique_num("test_unique_num")
                .emblem("test_emblem")
                .name("test_name")
                .build();
    }
}