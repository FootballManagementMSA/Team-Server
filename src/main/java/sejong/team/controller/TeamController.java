package sejong.team.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sejong.team.dto.TeamDto;
import sejong.team.global.BaseResponse;
import sejong.team.global.DataResponse;
import sejong.team.service.TeamService;
import sejong.team.service.res.CreateTeamResponseVO;
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
    public DataResponse<List<SearchTeamResponseDto>> findAllTeams(){
        return new DataResponse<>(teamService.findAllTeams());
    }

}
