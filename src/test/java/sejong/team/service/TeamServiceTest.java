package sejong.team.service;

import com.amazonaws.services.s3.model.ObjectMetadata;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import sejong.team.aws.S3Service;
import sejong.team.domain.Team;
import sejong.team.dto.TeamDto;
import sejong.team.repository.TeamRepository;

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
    @Test
    void 팀검색_기능테스트_이름() {
        //given
        Team saveTeam = teamRepository.save(createTeam());
        //when
        //then
    }
    private static Team createTeam() {
        return Team.builder()
                .uniqueNum("test_unique_num")
                .emblem("test_emblem")
                .name("test_name")
                .build();
    }
}
