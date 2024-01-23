package sejong.team.service;

import com.amazonaws.services.s3.model.ObjectMetadata;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sejong.team.aws.S3Service;
import sejong.team.common.client.UserServiceClient;
import sejong.team.common.client.dto.SizeUserTeamResponse;
import sejong.team.domain.Team;
import sejong.team.dto.TeamDto;
import sejong.team.global.MethodUtils;
import sejong.team.repository.TeamRepository;
import sejong.team.service.res.TeamBaseInfoResponseDto;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class TeamService {
    private final TeamRepository teamRepository;
    private final S3Service s3Service;
    private final UserServiceClient userServiceClient;
    @Transactional
    public void createTeam(TeamDto teamDto) throws IOException {
        InputStream inputStream = MethodUtils.getInputStreamFromMultipartFile(teamDto.getEmblem());
        String fileName = UUID.randomUUID().toString() + ".jpg";

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(teamDto.getEmblem().getSize());
        metadata.setContentType(teamDto.getEmblem().getContentType());
        s3Service.uploadFile(fileName, inputStream, metadata);

        // 중복 로직 반영 x
        String uniqueNum = UUID.randomUUID().toString().substring(0, 4);

        Team team = Team.builder()
                .name(teamDto.getName())
                .emblem(s3Service.getFileUrl(fileName))
                .uniqueNum(uniqueNum)
                .build();
        teamRepository.save(team);
    }

    public TeamBaseInfoResponseDto findTeamInfo(Long teamId) {
        Team findTeam = teamRepository.findById(teamId)
                .orElseThrow(NullPointerException::new);
        ResponseEntity<SizeUserTeamResponse> sizeUserTeamResponseResponseEntity = userServiceClient.countUsersInTeam(teamId);
        return TeamBaseInfoResponseDto.builder()
                .teamName(findTeam.getName())
                .teamEmblem(findTeam.getEmblem())
                .unique_num(findTeam.getUniqueNum())
                .sizeOfUsers(sizeUserTeamResponseResponseEntity.getBody().getSize())
                .createdAt(findTeam.getCreated_at())
                .build();
    }
}
