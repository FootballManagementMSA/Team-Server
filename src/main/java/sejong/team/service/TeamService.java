package sejong.team.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sejong.team.common.client.UserServiceClient;
import sejong.team.common.client.dto.SizeOfUsersInTeamResponse;
import sejong.team.domain.Team;
import sejong.team.repository.TeamRepository;
import sejong.team.service.res.TeamBaseInfoResponseDto;
import sejong.team.service.req.SearchTeamInfoRequestDto;
import sejong.team.service.res.SearchTeamInfoResponseDto;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TeamService {
    private final TeamRepository teamRepository;
    private final UserServiceClient userServiceClient;
    public TeamBaseInfoResponseDto findTeamInfo(Long teamId) {
        Team findTeam = teamRepository.findById(teamId)
                .orElseThrow(NullPointerException::new);
        SizeOfUsersInTeamResponse sizeOfUsersInTeam = userServiceClient.getSizeOfUsersInTeam(teamId);
        return TeamBaseInfoResponseDto.builder()
                .teamName(findTeam.getName())
                .teamEmblem(findTeam.getEmblem())
                .unique_num(findTeam.getUnique_num())
                .sizeOfUsers(sizeOfUsersInTeam.getSize())
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
    }*/
}