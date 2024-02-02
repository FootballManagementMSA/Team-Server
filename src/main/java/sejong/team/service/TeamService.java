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
import sejong.team.repository.TeamRepository;
import sejong.team.service.res.CreateTeamResponseVO;
import sejong.team.service.res.SearchTeamResponseDto;
import sejong.team.service.res.TeamBaseInfoResponseDto;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TeamService {
    private final TeamRepository teamRepository;
    private final S3Service s3Service;
    private final UserServiceClient userServiceClient;
    @Transactional
    public CreateTeamResponseVO createTeam(TeamDto teamDto) throws IOException {
        String fileUrl = s3Service.uploadMultipartFile(teamDto.getEmblem());

        // 중복 로직 반영 x
        String uniqueNum = UUID.randomUUID().toString().substring(0, 4);

        Team team = Team.builder()
                .name(teamDto.getName())
                .emblem(fileUrl)
                .uniqueNum(uniqueNum)
                .build();
        teamRepository.save(team);

        return CreateTeamResponseVO.builder()
                .uniqueNumber(uniqueNum)
                .build();
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

    public List<SearchTeamResponseDto> findAllTeams(){
        List<Team> teams = teamRepository.findAll();

        List<SearchTeamResponseDto> responseDtos = teams.stream()
                .map(team -> SearchTeamResponseDto.builder()
                        .name(team.getName())
                        .uniqueNum(team.getUniqueNum())
                        .emblem(team.getEmblem())
                        .build())
                .collect(Collectors.toList());
        return responseDtos;

    }
    /*
    public List<SearchTeamInfoResponseDto> searchTeamInfoByNameOrCode(SearchTeamInfoRequestDto requestDto) {
        List<Team> allTeamByCond = teamRepository.findAllByCondition(requestDto.getSearchCond());
        allTeamByCond.stream()
        allTeamByCond.stream().map(team -> SearchTeamInfoResponseDto.of(
            team.getId(),team.getName(),team.getEmblem(),,team.getUnique_num()
        ))
    }*/
}
