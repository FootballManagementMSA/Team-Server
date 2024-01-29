package sejong.team.service;

<<<<<<< Updated upstream
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import sejong.team.aws.S3Service;
=======
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import sejong.team.client.UserServiceClient;
>>>>>>> Stashed changes
import sejong.team.domain.Team;
import sejong.team.dto.TeamDto;
import sejong.team.repository.TeamRepository;
<<<<<<< Updated upstream

import java.io.IOException;
import java.io.InputStream;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TeamServiceTest {
    @Mock
    private TeamRepository teamRepository;

    @Mock
    private S3Service s3Service;

    @InjectMocks
    private TeamService teamService;

    @Test
    public void 팀생성테스트() throws IOException {
        // given
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "emblem",
                "filename.jpg",
                "image/jpeg",
                "some-image-content".getBytes());

        TeamDto teamDto = TeamDto.builder()
                .name("최강축구단")
                .emblem(mockMultipartFile)
                .build();

        // when
        teamService.createTeam(teamDto);

        // then
        verify(s3Service, times(1)).uploadFile(anyString(),
                any(InputStream.class), any(ObjectMetadata.class));
        verify(teamRepository, times(1)).save(any(Team.class));
    }
=======
import sejong.team.service.req.SearchTeamInfoRequestDto;
import sejong.team.service.res.SearchTeamInfoResponseDto;
import sejong.team.service.res.TeamBaseInfoResponseDto;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;

@AutoConfigureWireMock(port = 0)
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TeamServiceTest {
    @Autowired
    WireMockServer wireMockServer;
    @Mock
    private TeamRepository teamRepository;
    @Mock
    private UserServiceClient userServiceClient;
    private TeamService teamService = new TeamService(teamRepository, userServiceClient);

    @Test
    void 특정_팀의_대표정보_조회() {
        //given
        Team saveTeam = createTeam(1L,"1", "test_emblem", "test_name");
        wireMockServer.stubFor(
                WireMock
                        .get(WireMock.urlMatching("/api/user-service/users/teams/([0-9]+)"))
                        .willReturn(WireMock.aResponse()
                                .withStatus(HttpStatus.OK.value())
                                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                .withBodyFile("client-payload/get-sizeOfUsersInTeam.json")
                        )
        );
        //when
        Mockito.when(teamService.findTeamInfo(1L)).thenReturn(saveTeam);
        TeamBaseInfoResponseDto teamInfo = teamService.findTeamInfo(saveTeam.getId());
        //then
        assertThat(teamInfo.getSizeOfUsers()).isEqualTo(2);
        assertThat(teamInfo.getTeamName()).isEqualTo(saveTeam.getName());
        assertThat(teamInfo.getTeamEmblem()).isEqualTo(saveTeam.getEmblem());
        assertThat(teamInfo.getUnique_num()).isEqualTo(saveTeam.getUnique_num());
    }

    @Test
    void 팀검색_기능테스트_유니크키() {
        //given
        teamRepository.save(createTeam(1L, "test_emblem", "test_name", 1L));
        teamRepository.save(createTeam(2L, "test_emblem2", "test_name2", 1L));
        //when
        List<SearchTeamInfoResponseDto> responseDtos = teamService
                .searchTeamInfoByNameOrCode(SearchTeamInfoRequestDto.of("1"));
        //then
        assertThat(responseDtos).hasSize(1);
    }
/*
>>>>>>> Stashed changes
    @Test
    void 팀검색_기능테스트_이름() {
        //given
        Team saveTeam = teamRepository.save(createTeam());
        //when
        //then
<<<<<<< Updated upstream
    }
    private static Team createTeam() {
        return Team.builder()
                .uniqueNum("test_unique_num")
                .emblem("test_emblem")
                .name("test_name")
=======
    }*/

    private static Team createTeam(Long id,String uniqueNum, String testEmblem, String testName) {
        return Team.builder()
                .id(id)
                .unique_num(uniqueNum)
                .emblem(testEmblem)
                .name(testName)
>>>>>>> Stashed changes
                .build();
    }
}
