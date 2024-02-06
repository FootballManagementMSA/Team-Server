package sejong.team.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sejong.team.dto.TeamDto;
import sejong.team.global.DataResponse;
import sejong.team.service.TeamService;
import sejong.team.service.req.SearchTeamInfoRequestDto;
import sejong.team.service.res.CreateTeamResponseVO;
import sejong.team.service.res.SearchTeamInfoResponseDto;
import sejong.team.service.res.SearchTeamResponseDto;
import sejong.team.service.res.TeamBaseInfoResponseDto;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/team-service")
public class TeamController {
    private final TeamService teamService;

    @GetMapping("/teams/{teamId}")
    public DataResponse<TeamBaseInfoResponseDto> findTeamBaseInfo(@PathVariable Long teamId) {
        return new DataResponse<>(teamService.findTeamInfo(teamId));
    }

    @PostMapping("/teams")
    public DataResponse<CreateTeamResponseVO> createTeam(@ModelAttribute TeamDto teamDto) throws IOException {
        return new DataResponse<>(teamService.createTeam(teamDto));
    }

    @GetMapping("/teams")
    public DataResponse<List<SearchTeamResponseDto>> findAllTeams() {
        return new DataResponse<>(teamService.findAllTeams());
    }

    @GetMapping("/team/search")
    public DataResponse<List<SearchTeamInfoResponseDto>> searchTeamInfo(@RequestParam(name = "search") String search) {
        List<SearchTeamInfoResponseDto> responseDtos = teamService.searchTeamInfo(SearchTeamInfoRequestDto.builder()
                .search(search)
                .build());
        return new DataResponse<>(responseDtos);
    }

}
