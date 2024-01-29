package sejong.team.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sejong.team.aws.S3Service;
import sejong.team.client.UserServiceClient;
import sejong.team.domain.Team;
import sejong.team.dto.TeamDto;
import sejong.team.repository.TeamRepository;
import sejong.team.service.req.SearchTeamInfoRequestDto;
import sejong.team.service.res.CreateTeamResponseVO;
import sejong.team.service.res.SearchTeamInfoResponseDto;
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
        Integer memberSize = getMemberSize(findTeam);
        return TeamBaseInfoResponseDto.builder()
                .teamName(findTeam.getName())
                .teamEmblem(findTeam.getEmblem())
                .unique_num(findTeam.getUniqueNum())
                .sizeOfUsers(memberSize)
                .createdAt(findTeam.getCreated_at())
                .build();
    }
    /*
    public List<SearchTeamInfoResponseDto> searchTeamInfoByNameOrCode(SearchTeamInfoRequestDto requestDto) {
        List<Team> allTeamByCond = teamRepository.findAllByCondition(requestDto.getSearchCond());
        allTeamByCond.stream()
        allTeamByCond.stream().map(team -> SearchTeamInfoResponseDto.of(
            team.getId(),team.getName(),team.getEmblem(),,team.getUnique_num()
        ))
    }
}*/

    public List<SearchTeamInfoResponseDto> searchTeamInfoByNameOrCode(SearchTeamInfoRequestDto requestDto) {
        List<Team> allTeamByCond = teamRepository.findAllByCondition(requestDto.getSearch());
        List<SearchTeamInfoResponseDto> teamInfoResponseDtos = allTeamByCond.stream()
                .map(team ->
                        SearchTeamInfoResponseDto.of(team.getId(),
                                team.getName(),
                                team.getEmblem(),
                                getMemberSize(team),
                                team.getUniqueNum()))
                .collect(Collectors.toList());
        return teamInfoResponseDtos;
    }

    private Integer getMemberSize(Team team) {
        return userServiceClient.countUsersInTeam(team.getId()).getBody().getSize();
    }
}
