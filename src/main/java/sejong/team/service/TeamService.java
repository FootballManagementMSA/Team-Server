package sejong.team.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sejong.team.common.client.UserServiceClient;
import sejong.team.common.client.dto.SizeUserTeamResponse;
import sejong.team.domain.Team;
import sejong.team.repository.TeamRepository;
import sejong.team.service.res.TeamBaseInfoResponseDto;

@RequiredArgsConstructor
@Service
public class TeamService {
    private final TeamRepository teamRepository;
    private final UserServiceClient userServiceClient;
    public TeamBaseInfoResponseDto findTeamInfo(Long teamId) {
        Team findTeam = teamRepository.findById(teamId)
                .orElseThrow(NullPointerException::new);
        ResponseEntity<SizeUserTeamResponse> sizeUserTeamResponseResponseEntity = userServiceClient.countUsersInTeam(teamId);
        return TeamBaseInfoResponseDto.builder()
                .teamName(findTeam.getName())
                .teamEmblem(findTeam.getEmblem())
                .unique_num(findTeam.getUnique_num())
                .sizeOfUsers(sizeUserTeamResponseResponseEntity.getBody().getSize())
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